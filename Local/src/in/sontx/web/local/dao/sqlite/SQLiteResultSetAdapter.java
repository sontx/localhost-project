package in.sontx.web.local.dao.sqlite;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

import in.sontx.web.shared.dao.ResultSetAdapter;

public class SQLiteResultSetAdapter implements ResultSetAdapter {
	private SQLiteStatement statement;
	private HashMap<String, Integer> columnMap = new HashMap<>();

	public SQLiteResultSetAdapter(SQLiteStatement statement) throws SQLiteException {
		this.statement = statement;
		int count = statement.columnCount();
		for (int i = 0; i < count; i++) {
			columnMap.put(statement.getColumnName(i), i);
		}
	}

	@Override
	public boolean next() throws SQLException {
		try {
			return statement.step();
		} catch (SQLiteException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public void close() throws IOException {
		statement.dispose();
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		try {
			return statement.columnString(columnIndex);
		} catch (SQLiteException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		try {
			return statement.columnInt(columnIndex);
		} catch (SQLiteException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		try {
			return statement.columnLong(columnIndex);
		} catch (SQLiteException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		try {
			return statement.columnDouble(columnIndex);
		} catch (SQLiteException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		try {
			return statement.columnString(columnMap.get(columnLabel));
		} catch (SQLiteException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		try {
			return statement.columnInt(columnMap.get(columnLabel));
		} catch (SQLiteException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		try {
			return statement.columnLong(columnMap.get(columnLabel));
		} catch (SQLiteException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		try {
			return statement.columnDouble(columnMap.get(columnLabel));
		} catch (SQLiteException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		try {
			return statement.columnValue(columnIndex);
		} catch (SQLiteException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		try {
			return statement.columnValue(columnMap.get(columnLabel));
		} catch (SQLiteException e) {
			throw new SQLException(e);
		}
	}

}
