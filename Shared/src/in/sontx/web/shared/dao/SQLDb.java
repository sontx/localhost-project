package in.sontx.web.shared.dao;

import java.sql.SQLException;

import com.sun.istack.internal.NotNull;

public abstract class SQLDb {
	protected final ISQLConnection connection;

	public SQLDb(@NotNull ISQLConnection cnn) {
		this.connection = cnn;
	}

	public void open() throws SQLException {
		connection.open();
	}

	public void close() {
		connection.close();
	}

}
