package in.sontx.web.local.dao;

public final class TableInfo {
	public static final String GLOBAL_ACCOUNT_TABLE_NAME = "tbAccount";
	public static final String GLOBAL_ACCOUNT_TABLE_STRUCT = "CREATE TABLE \"tbAccount\"" +
															 "(\"userId\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
															 "\"userName\" VARCHAR(50) NOT NULL  UNIQUE ," + 
															 "\"passwordHash\" VARCHAR(2000) NOT NULL )";
	public static final String GLOBAL_ACCINFO_TABLE_NAME = "tbAccountInfo";
	public static final String GLOBAL_ACCINFO_TABLE_STRUCT = "CREATE TABLE \"tbAccountInfo\" " + 
															 "(\"userId\" INTEGER PRIMARY KEY  NOT NULL  UNIQUE ," +
															 "\"lastLogin\" INTEGER NOT NULL ," +
															 "\"userDir\" VARCHAR(255) NOT NULL )";
	public static final String USER_NOTE_TABLE_NAME = "tbNote";
	public static final String USER_NOTE_TABLE_STRUCT = "CREATE TABLE \"tbNote\"" +
														"(\"id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ," +
														"\"title\" VARCHAR(255) NOT NULL ," +
														"\"content\" VARCHAR(4000) NOT NULL ," + 
														"\"created\" INT4 NOT NULL )";

	TableInfo() {
	}
}
