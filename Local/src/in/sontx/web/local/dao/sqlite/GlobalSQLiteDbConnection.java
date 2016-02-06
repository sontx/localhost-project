package in.sontx.web.local.dao.sqlite;

import in.sontx.web.local.dao.TableInfo;

public class GlobalSQLiteDbConnection extends SQLiteDbConnection {

	public GlobalSQLiteDbConnection(String fileName) {
		super(fileName);
	}

	@Override
	protected String[] requestTableStructs() {
		return new String[] { TableInfo.GLOBAL_ACCOUNT_TABLE_STRUCT, TableInfo.GLOBAL_ACCINFO_TABLE_STRUCT };
	}

}
