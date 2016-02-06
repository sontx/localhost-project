package in.sontx.web.local.dao.sqlite;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteJob;

class NonQueryJob extends SQLiteJob<Integer> {
	private final String sql;

	public NonQueryJob(String sql) {
		this.sql = sql;
	}

	@Override
	protected Integer job(SQLiteConnection connection) throws Throwable {
		if (sql != null) {
			try {
				connection.exec(sql);
				return connection.getChanges();
			} catch (SQLiteException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

}
