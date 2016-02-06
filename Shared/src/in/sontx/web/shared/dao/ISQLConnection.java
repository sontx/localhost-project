package in.sontx.web.shared.dao;

import java.sql.SQLException;

import com.sun.istack.internal.NotNull;

public interface ISQLConnection {
	void open() throws SQLException;

	void close();

	int executeNonQuery(@NotNull String sql) throws SQLException;

	ResultSetAdapter executeQuery(@NotNull String sql);
}
