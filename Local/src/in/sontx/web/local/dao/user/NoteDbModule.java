package in.sontx.web.local.dao.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.istack.internal.NotNull;

import in.sontx.web.local.bean.Note;
import in.sontx.web.local.dao.TableInfo;
import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.dao.ResultSetAdapter;
import in.sontx.web.shared.utils.Log;
import in.sontx.web.shared.utils.SQLHepler;

public class NoteDbModule extends DbModule {
	public NoteDbModule(ISQLConnection cnn) {
		super(cnn);
	}

	public boolean addNote(Note note) {
		final String sql = String.format("INSERT INTO %s(title, content, created) VALUES(%s, %s, %d)",
				TableInfo.USER_NOTE_TABLE_NAME, SQLHepler.prepareString(note.getTitle()),
				SQLHepler.prepareString(note.getContent()), note.getCreated());
		try {
			return connection.executeNonQuery(sql) > 0;
		} catch (SQLException e) {
			Log.e(e);
		}
		return false;
	}

	public Note getNote(int id) {
		final String sql = String.format("SELECT * FROM %s WHERE id=%d", TableInfo.USER_NOTE_TABLE_NAME, id);
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
				note.setId(set.getInt("id"));
				note.setTitle(set.getString("title"));
				note.setContent(set.getString("content"));
				note.setCreated(set.getInt("created"));
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

	public List<Note> searchNotes(@NotNull String pattern) {
		pattern = SQLHepler.prepareSearchString(pattern);
		final String sql = String.format("SELECT * FROM %s WHERE (title LIKE %s) OR (content LIKE %s)",
				TableInfo.USER_NOTE_TABLE_NAME, pattern, pattern);
		return getNotes(sql);
	}

	public List<Note> getAllNotes() {
		final String sql = String.format("SELECT * FROM %s", TableInfo.USER_NOTE_TABLE_NAME);
		return getNotes(sql);
	}

	public boolean updateNote(Note note) {
		final String sql = String.format("UPDATE %s SET title=%s, content=%s WHERE id=%d",
				TableInfo.USER_NOTE_TABLE_NAME, SQLHepler.prepareString(note.getTitle()),
				SQLHepler.prepareString(note.getContent()), note.getId());
		try {
			return connection.executeNonQuery(sql) > 0;
		} catch (SQLException e) {
			Log.e(e);
		}
		return false;
	}

	public boolean deleteNote(int id) {
		final String sql = String.format("DELETE FROM %s WHERE id=%d", TableInfo.USER_NOTE_TABLE_NAME, id);
		try {
			return connection.executeNonQuery(sql) > 0;
		} catch (SQLException e) {
			Log.e(e);
		}
		return false;
	}
}
