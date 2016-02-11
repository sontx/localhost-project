package in.sontx.web.local.bo;

import java.util.List;

import com.sun.istack.internal.NotNull;

import in.sontx.web.local.bean.Note;
import in.sontx.web.local.dao.NoteDb;
import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.utils.DateTime;
import in.sontx.web.shared.utils.Security;

public final class NoteMgr {
	private final String userId;
	private final NoteDb noteDb;

	public NoteMgr(@NotNull ISQLConnection connection, @NotNull String userId) {
		this.noteDb = new NoteDb(connection);
		this.userId = userId;
	}

	private boolean isValidNote(String title, String content) {
		return (title != null && content != null) && (title.trim().length() > 0 && content.trim().length() > 0);
	}
	
	public boolean addNote(String title, String content) {
		if (!isValidNote(title, content))
			return false;
		String id = Security.getRandomUUID();
		int created = DateTime.now().getTime();
		int modified = DateTime.now().getTime();
		Note note = new Note();
		note.setId(id);
		note.setTitle(title.trim());
		note.setContent(content.trim());
		note.setCreated(created);
		note.setModified(modified);
		return noteDb.addNote(userId, note);
	}

	public Note getNote(@NotNull String id) {
		return Security.isValidUUID(id) ? noteDb.getNote(userId, id) : null;
	}

	public List<Note> searchNotes(String what) {
		return (what == null || (what = what.trim()).length() == 0) ? noteDb.getAllNotes(userId) : noteDb.searchNotes(userId, what);
	}

	public List<Note> getAllNotes() {
		return noteDb.getAllNotes(userId);
	}

	public boolean updateNote(@NotNull String id, @NotNull String title, @NotNull String content) {
		if (!Security.isValidUUID(id) || !isValidNote(title, content))
			return false;
		int modified = DateTime.now().getTime();
		Note note = new Note();
		note.setId(id);
		note.setTitle(title);
		note.setContent(content);
		note.setModified(modified);
		return noteDb.updateNote(userId, note);
	}

	public boolean deleteNote(@NotNull String id) {
		return Security.isValidUUID(id) ? noteDb.deleteNote(userId, id) : false;
	}
}
