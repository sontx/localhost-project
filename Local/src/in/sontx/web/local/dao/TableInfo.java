package in.sontx.web.local.dao;

public final class TableInfo {
	public static final String ACCOUNT_TABLE_NAME = "account";
	public static final String ACCOUNT_TABLE_STRUCT = "CREATE TABLE account ("
													  + "id char(32) NOT NULL,"
													  + "username varchar(15) NOT NULL," 
													  + "password_hash varchar(1000) NOT NULL," 
													  + "PRIMARY KEY (id),"
													  + "UNIQUE KEY id_UNIQUE (id)," 
													  + "UNIQUE KEY username_UNIQUE (username))";

	public static final String ACCOUNT_INFO_TABLE_NAME = "account_info";
	public static final String ACCOUNT_INFO_TABLE_STRUCT = "CREATE TABLE account_info ("
														   + "user_id char(32) NOT NULL,"
														   + "last_login int(10) unsigned NOT NULL,"
														   + "user_dir char(32) NOT NULL,"
														   + "PRIMARY KEY (user_id),"
														   + "CONSTRAINT fk_acc_info FOREIGN KEY (user_id) REFERENCES account (id))";

	public static final String NOTE_TABLE_NAME = "note";
	public static final String NOTE_TABLE_STRUCT = "CREATE TABLE note ("
												   + "id char(32) NOT NULL,"
												   + "title varchar(255) NOT NULL,"
												   + "content varchar(4000) NOT NULL,"
												   + "created int(11) NOT NULL,"
												   + "modified int(11) NOT NULL,"
												   + "user_id char(32) NOT NULL,"
												   + "PRIMARY KEY (id),"
												   + "UNIQUE KEY id_UNIQUE (id),"
												   + "KEY fk_note (user_id),"
												   + "CONSTRAINT fk_note FOREIGN KEY (user_id) REFERENCES account (id))";
	
	public static final String FILE_TABLE_NAME = "file";
	public static final String FILE_TABLE_STRUCT = "CREATE TABLE file ("
												   + "id char(32) NOT NULL,"
												   + "origin_name varchar(500) NOT NULL,"
												   + "raw_name varchar(255) NOT NULL,"
												   + "size int(10) unsigned NOT NULL,"
												   + "created int(10) unsigned NOT NULL,"
												   + "type varchar(100) NOT NULL,"
												   + "state tinyint(3) unsigned NOT NULL DEFAULT '0',"
												   + "user_id char(32) NOT NULL,"
												   + "PRIMARY KEY (id),"
												   + "UNIQUE KEY id_UNIQUE (id),"
												   + "KEY fk_user_id___idx (user_id),"
												   + "CONSTRAINT fk_file FOREIGN KEY (user_id) REFERENCES account (id))";

	TableInfo() {
	}
}
