package in.sontx.web.local.bo;

import java.util.List;

import in.sontx.web.local.bean.Note;
import in.sontx.web.local.dao.user.NoteDbModule;
import in.sontx.web.local.dao.user.UserDb;
import in.sontx.web.shared.utils.DateTime;

public final class NoteMgr {
	private NoteDbModule dbModule;

	public NoteMgr(UserDb db) {
		dbModule = db.noteDb;
	}

	private boolean validNote(Note note) {
		note.setTitle(note.getTitle().trim());
		note.setContent(note.getContent().trim());
		return !(note.getTitle().length() == 0 || note.getContent().length() == 0);
	}

	public boolean addNote(Note note) {
		if (!validNote(note))
			return false;
		note.setCreated(DateTime.now().getTime());
		return dbModule.addNote(note);
	}

	public Note getNote(int id) {
		return id < 0 ? null : dbModule.getNote(id);
	}

	public List<Note> searchNotes(String pattern) {
		return pattern == null || (pattern = pattern.trim()).length() == 0 ? dbModule.getAllNotes()
				: dbModule.searchNotes(pattern);
	}

	public boolean updateNote(Note note) {
		if (!validNote(note))
			return false;
		return dbModule.updateNote(note);
	}

	public boolean deleteNote(int id) {
		return id > -1 ? dbModule.deleteNote(id) : false;
	}
}
