package in.sontx.web.local.bo;

import java.sql.SQLException;

import in.sontx.web.local.dao.user.UserDb;
import in.sontx.web.shared.dao.ISQLConnection;

public final class UserMgr {
	public final NoteMgr noteMgr;
	private final UserDb userDb;

	public UserMgr(ISQLConnection cnn) {
		userDb = new UserDb(cnn);
		noteMgr = new NoteMgr(userDb);
	}

	public void open() throws SQLException {
		userDb.open();
	}

	public void close() {
		userDb.close();
	}
}
