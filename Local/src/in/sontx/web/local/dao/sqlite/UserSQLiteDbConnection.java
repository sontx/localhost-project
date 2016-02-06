package in.sontx.web.local.dao.sqlite;

import in.sontx.web.local.dao.TableInfo;

public class UserSQLiteDbConnection extends SQLiteDbConnection {

	public UserSQLiteDbConnection(String fileName) {
		super(fileName);
	}

	@Override
	protected String[] requestTableStructs() {
		return new String[] { TableInfo.USER_NOTE_TABLE_STRUCT };
	}

}
