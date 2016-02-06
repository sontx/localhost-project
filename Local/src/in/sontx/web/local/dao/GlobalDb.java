package in.sontx.web.local.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.sontx.web.local.bean.Account;
import in.sontx.web.local.bean.AccountInfo;
import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.dao.ResultSetAdapter;
import in.sontx.web.shared.dao.SQLDb;
import in.sontx.web.shared.utils.DateTime;
import in.sontx.web.shared.utils.Log;
import in.sontx.web.shared.utils.SQLHepler;

public class GlobalDb extends SQLDb {

	public GlobalDb(ISQLConnection cnn) {
		super(cnn);
	}

	public Account createAccount(String userName, String passwordHash, String dir) {
		String sql = String.format("INSERT INTO %s(userName, passwordHash) VALUES(%s, %s)",
				TableInfo.GLOBAL_ACCOUNT_TABLE_NAME, SQLHepler.prepareString(userName),
				SQLHepler.prepareString(passwordHash));
		try {
			if (connection.executeNonQuery(sql) <= 0)
				return null;
			Account account = getAccount(userName, passwordHash);
			if (account == null)
				return null;
			sql = String.format("INSERT INTO %s(userId, lastLogin, userDir) VALUES(%d, %d, %s)",
					TableInfo.GLOBAL_ACCINFO_TABLE_NAME, account.getUserId(), DateTime.now().getTime(),
					SQLHepler.prepareString(dir));
			return connection.executeNonQuery(sql) > 0 ? account : null;
		} catch (SQLException e) {
			Log.e(e);
		}
		return null;
	}

	public Account getAccount(String userName, String passwordHash) {
		final String sql = String.format("SELECT * FROM %s WHERE userName=%s AND passwordHash=%s",
				TableInfo.GLOBAL_ACCOUNT_TABLE_NAME, SQLHepler.prepareString(userName),
				SQLHepler.prepareString(passwordHash));
		ResultSetAdapter set = connection.executeQuery(sql);
		if (set == null)
			return null;
		try {
			if (set.next()) {
				Account account = new Account();
				account.setUserId(set.getInt("userId"));
				account.setUserName(userName);
				account.setPasswordHash(set.getString("passwordHash"));
				return account;
			}
		} catch (SQLException e) {
			Log.e(e);
		} finally {
			try {
				set.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	public List<Account> getAccounts() {
		final String sql = String.format("SELECT userId, userName FROM %s", TableInfo.GLOBAL_ACCOUNT_TABLE_NAME);
		ResultSetAdapter set = connection.executeQuery(sql);
		if (set == null)
			return null;
		try {
			List<Account> accounts = new ArrayList<>();
			while (set.next()) {
				Account account = new Account();
				account.setUserId(set.getInt("userId"));
				account.setUserName(set.getString("userName"));
				accounts.add(account);
			}
			return accounts;
		} catch (SQLException e) {
			Log.e(e);
		} finally {
			try {
				set.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	public AccountInfo getAccountInfo(int userId) {
		final String sql = String.format("SELECT * FROM %s WHERE userId=%d", TableInfo.GLOBAL_ACCINFO_TABLE_NAME,
				userId);
		ResultSetAdapter set = connection.executeQuery(sql);
		if (set == null)
			return null;
		try {
			if (set.next()) {
				AccountInfo accountInfo = new AccountInfo();
				accountInfo.setUserId(userId);
				accountInfo.setLastLogin(set.getInt("lastLogin"));
				accountInfo.setUserDir(set.getString("userDir"));
				return accountInfo;
			}
		} catch (SQLException e) {
			Log.e(e);
		} finally {
			try {
				set.close();
			} catch (IOException e) {
			}
		}
		return null;
	}

	private boolean executeNonQuery(String sql) {
		try {
			return connection.executeNonQuery(sql) > 0;
		} catch (SQLException e) {
			Log.e(e);
		}
		return false;
	}

	public boolean updateAccountInfo(AccountInfo accountInfo) {
		final String sql = String.format("UPDATE %s SET lastLogin=%d, userDir=%s WHERE userId=%d",
				accountInfo.getLastLogin(), SQLHepler.prepareString(accountInfo.getUserDir()), accountInfo.getUserId());
		return executeNonQuery(sql);
	}

	public boolean changeAccountPasswordHash(int userId, String passwordHash) {
		final String sql = String.format("UPDATE %s SET passwordHash=%s WHERE userId=%d",
				TableInfo.GLOBAL_ACCOUNT_TABLE_NAME, SQLHepler.prepareString(passwordHash), userId);
		return executeNonQuery(sql);
	}

	public boolean deleteAccount(int userId) {
		String sql = String.format("DELETE FROM %s WHERE userId=%d", TableInfo.GLOBAL_ACCOUNT_TABLE_NAME, userId);
		boolean resultDelAccount = executeNonQuery(sql);
		sql = String.format("DELETE FROM %s WHERE userId=%d", TableInfo.GLOBAL_ACCINFO_TABLE_NAME, userId);
		boolean resultDelAccountInfo = executeNonQuery(sql);
		return resultDelAccount && resultDelAccountInfo;
	}
}
