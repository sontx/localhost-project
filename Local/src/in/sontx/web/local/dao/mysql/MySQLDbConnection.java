package in.sontx.web.local.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import in.sontx.web.local.Config;
import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.dao.ResultSetAdapter;
import in.sontx.web.shared.utils.Log;

public class MySQLDbConnection implements ISQLConnection {
	private Connection connection;
	private Statement statement;
	
	public MySQLDbConnection() throws ClassNotFoundException {
		Class.forName(Config.DB_DRIVER_STRING);
	}
	
	@Override
	public void open() throws SQLException {
		connection = DriverManager.getConnection(Config.DB_CONNECTION_STRING);
		statement = connection.createStatement();
	}

	@Override
	public void close() {
		try {
			connection.close();
			statement.close();
		} catch (SQLException e) {
			Log.e(e);
		}
	}

	@Override
	public int executeNonQuery(String sql) throws SQLException {
		return statement.executeUpdate(sql);
	}

	@Override
	public ResultSetAdapter executeQuery(String sql) {
		try {
			return new MySQLResultSetAdapter(statement.executeQuery(sql));
		} catch (SQLException e) {
			Log.e(e);
		}
		return null;
	}

}
