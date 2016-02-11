package in.sontx.web.local.dao.mysql;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.sontx.web.shared.dao.ResultSetAdapter;

public class MySQLResultSetAdapter implements ResultSetAdapter {
	private final ResultSet set;

	public MySQLResultSetAdapter(ResultSet set) {
		this.set = set;
	}

	@Override
	public void close() throws IOException {
		try {
			set.close();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	@Override
	public boolean next() throws SQLException {
		return set.next();
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		return set.getString(columnIndex);
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		return set.getInt(columnIndex);
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		return set.getLong(columnIndex);
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		return set.getDouble(columnIndex);
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		return set.getString(columnLabel);
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		return set.getInt(columnLabel);
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		return set.getLong(columnLabel);
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		return set.getDouble(columnLabel);
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		return set.getObject(columnIndex);
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		return set.getObject(columnLabel);
	}

}
