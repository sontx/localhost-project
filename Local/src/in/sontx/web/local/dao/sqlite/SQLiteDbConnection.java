package in.sontx.web.local.dao.sqlite;

import java.io.File;
import java.sql.SQLException;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteJob;
import com.almworks.sqlite4java.SQLiteQueue;
import com.sun.istack.internal.NotNull;

import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.dao.ResultSetAdapter;
import in.sontx.web.shared.utils.Log;
import in.sontx.web.shared.utils.Path;

public abstract class SQLiteDbConnection implements ISQLConnection {
	private final SQLiteQueue queue;
	private boolean dbCreated;

	public SQLiteDbConnection(String fileName) {
		File dbFile = new File(fileName);
		queue = new SQLiteQueue(dbFile);
		dbCreated = dbFile.exists();
	}

	protected abstract String[] requestTableStructs();

	private void create() {
		final String[] tables = requestTableStructs();
		if (tables == null || tables.length == 0)
			return;
		for (String table : tables) {
			queue.execute(new NonQueryJob(table)).complete();
		}
	}

	@Override
	public void open() throws SQLException {
		queue.start();
		if (!dbCreated) {
			File dir = new File(Path.getDirectoryName(queue.getDatabaseFile().getPath()));
			if (!dir.isDirectory() && !dir.exists())
				dir.mkdirs();
			Log.d("Creating database...");
			create();
			Log.d("Created database!");
			dbCreated = true;
		}
	}

	@Override
	public void close() {
		queue.stop(true);
		try {
			queue.join();
		} catch (InterruptedException e) {
		}
		if (!dbCreated) {
			File dbFile = queue.getDatabaseFile();
			if (dbFile != null)
				dbFile.delete();
		}
	}

	@Override
	public int executeNonQuery(@NotNull String sql) throws SQLException {
		return queue.execute(new NonQueryJob(sql)).complete();
	}

	@Override
	public ResultSetAdapter executeQuery(@NotNull final  String sql) {
		return queue.execute(new SQLiteJob<ResultSetAdapter>() {
			@Override
			protected ResultSetAdapter job(SQLiteConnection connection) throws Throwable {
				return new SQLiteResultSetAdapter(connection.prepare(sql));
			}
		}).complete();
	}

}
