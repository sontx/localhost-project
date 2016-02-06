package in.sontx.web.local.dao.user;

import in.sontx.web.shared.dao.ISQLConnection;

public abstract class DbModule {
	protected final ISQLConnection connection;

	public DbModule(ISQLConnection cnn) {
		connection = cnn;
	}
}
