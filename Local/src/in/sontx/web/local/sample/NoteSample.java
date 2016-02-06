package in.sontx.web.local.sample;

import java.util.ArrayList;
import java.util.List;

import in.sontx.web.local.bean.Note;
import in.sontx.web.shared.utils.DateTime;

public class NoteSample {
	public static List<Note> getNotes() {
		List<Note> notes = new ArrayList<>();
		Note note = new Note();
		note.setCreated(DateTime.now().getTime());
		note.setTitle("this is note 1");
		note.setContent("this is content of note 1");
		note.setId(1);
		notes.add(note);
		
		note = new Note();
		note.setCreated(DateTime.now().getTime());
		note.setTitle("this is note 2");
		note.setContent("this is content of note 2");
		note.setId(2);
		notes.add(note);
		
		note = new Note();
		note.setCreated(DateTime.now().getTime());
		note.setTitle("this is note 3");
		note.setContent("this is content of note 3");
		note.setId(3);
		notes.add(note);
		
		return notes;
	}
}
