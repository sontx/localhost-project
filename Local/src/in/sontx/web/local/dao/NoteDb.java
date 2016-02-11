package in.sontx.web.local.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.istack.internal.NotNull;

import in.sontx.web.local.bean.Note;
import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.dao.ResultSetAdapter;
import in.sontx.web.shared.utils.Log;
import in.sontx.web.shared.utils.SQLHepler;

public final class NoteDb {
	private final ISQLConnection connection;
	
	public NoteDb(ISQLConnection connection) {
		this.connection = connection;
	}
	
	public boolean addNote(@NotNull String userId, @NotNull Note note) {
		final String sql = String.format(
				"INSERT INTO %s(id, title, content, created, modified, user_id) VALUES(%s, %s, %s, %d, %d, %s)",
				TableInfo.NOTE_TABLE_NAME,
				SQLHepler.prepareString(note.getId()),
				SQLHepler.prepareString(note.getTitle()),
				SQLHepler.prepareString(note.getContent()), 
				note.getCreated(),
				note.getModified(),
				SQLHepler.prepareString(userId));
		try {
			return connection.executeNonQuery(sql) > 0;
		} catch (SQLException e) {
			Log.e(e);
		}
		return false;
	}

	public Note getNote(@NotNull String userId, @NotNull String id) {
		final String sql = String.format(
				"SELECT title, content, created, modified FROM %s WHERE user_id=%s AND id=%s ORDER BY modified DESC", 
				TableInfo.NOTE_TABLE_NAME,
				SQLHepler.prepareString(userId),
				SQLHepler.prepareString(id));
		ResultSetAdapter set = connection.executeQuery(sql);
		if (set == null)
			return null;
		try {
			if (set.next()) {
				Note note = new Note();
				note.setId(id);
				note.setTitle(set.getString("title"));
				note.setContent(set.getString("content"));
				note.setCreated(set.getInt("created"));
				note.setModified(set.getInt("modified"));
				return note;
			}
		} catch (SQLException e) {
			Log.e(e);
		} finally {
			try {
				set.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	private List<Note> getNotes(@NotNull String sql) {
		ResultSetAdapter set = connection.executeQuery(sql);
		if (set == null)
			return null;
		try {
			List<Note> notes = new ArrayList<>();
			while (set.next()) {
				Note note = new Note();
				note.setId(set.getString("id"));
				note.setTitle(set.getString("title"));
				note.setContent(set.getString("content"));
				note.setCreated(set.getInt("created"));
				note.setModified(set.getInt("modified"));
				notes.add(note);
			}
			return notes;
		} catch (SQLException e) {
			Log.e(e);
		} finally {
			try {
				set.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	public List<Note> searchNotes(@NotNull String userId, @NotNull String what) {
		what = SQLHepler.prepareSearchString(what);
		final String sql = String.format(
				"SELECT id, title, content, created, modified FROM %s WHERE (user_id=%s) AND ((title LIKE %s) OR (content LIKE %s)) ORDER BY modified DESC",
				TableInfo.NOTE_TABLE_NAME, 
				SQLHepler.prepareString(userId),
				what, 
				what);
		return getNotes(sql);
	}

	public List<Note> getAllNotes(@NotNull String userId) {
		final String sql = String.format(
				"SELECT id, title, content, created, modified FROM %s WHERE user_id=%s ORDER BY modified DESC", 
				TableInfo.NOTE_TABLE_NAME,
				SQLHepler.prepareString(userId));
		return getNotes(sql);
	}

	/**
	 * Update note information, this method just update title, content and modified time.
	 * @param userId determine user id who will be updated
	 * @param note contain new note information
	 * @return true when success!
	 */
	public boolean updateNote(@NotNull String userId, @NotNull Note note) {
		final String sql = String.format(
				"UPDATE %s SET title=%s, content=%s, modified=%d WHERE user_id=%s AND id=%s",
				TableInfo.NOTE_TABLE_NAME, 
				SQLHepler.prepareString(note.getTitle()),
				SQLHepler.prepareString(note.getContent()),
				note.getModified(),
				SQLHepler.prepareString(userId),
				SQLHepler.prepareString(note.getId()));
		try {
			return connection.executeNonQuery(sql) > 0;
		} catch (SQLException e) {
			Log.e(e);
		}
		return false;
	}

	public boolean deleteNote(@NotNull String userId, @NotNull String id) {
		final String sql = String.format(
				"DELETE FROM %s WHERE user_id=%s AND id=%s", 
				TableInfo.NOTE_TABLE_NAME,
				SQLHepler.prepareString(userId),
				SQLHepler.prepareString(id));
		try {
			return connection.executeNonQuery(sql) > 0;
		} catch (SQLException e) {
			Log.e(e);
		}
		return false;
	}
}
