package in.sontx.web.local.dao.user;

import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.dao.SQLDb;

public class UserDb extends SQLDb {
	public final NoteDbModule noteDb;

	public UserDb(ISQLConnection cnn) {
		super(cnn);
		noteDb = new NoteDbModule(cnn);
	}

}
