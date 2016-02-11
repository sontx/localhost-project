package in.sontx.web.local.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.istack.internal.NotNull;

import in.sontx.web.local.bean.Account;
import in.sontx.web.local.bean.AccountInfo;
import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.dao.ResultSetAdapter;
import in.sontx.web.shared.utils.Log;
import in.sontx.web.shared.utils.SQLHepler;

public final class AccountDb {
	private final ISQLConnection connection;

	public AccountDb(ISQLConnection connection) {
		this.connection = connection;
	}
	
	public boolean createAccount(@NotNull Account account, @NotNull AccountInfo accountInfo) {
		String sql = String.format("INSERT INTO %s(id, username, password_hash) VALUES(%s, %s, %s)",
				TableInfo.ACCOUNT_TABLE_NAME, 
				SQLHepler.prepareString(account.getId()),
				SQLHepler.prepareString(account.getUserName()),
				SQLHepler.prepareString(account.getPasswordHash()));
		try {
			if (connection.executeNonQuery(sql) <= 0)
				return false;
			sql = String.format("INSERT INTO %s(user_id, last_login, user_dir) VALUES(%s, %d, %s)",
					TableInfo.ACCOUNT_INFO_TABLE_NAME, 
					SQLHepler.prepareString(account.getId()), 
					accountInfo.getLastLogin(),
					SQLHepler.prepareString(accountInfo.getUserDir()));
			return connection.executeNonQuery(sql) > 0;
		} catch (SQLException e) {
			Log.e(e);
		}
		return false;
	}

	public Account getAccount(@NotNull String userName, @NotNull String passwordHash) {
		final String sql = String.format("SELECT id FROM %s WHERE username=%s AND password_hash=%s",
				TableInfo.ACCOUNT_TABLE_NAME, 
				SQLHepler.prepareString(userName),
				SQLHepler.prepareString(passwordHash));
		ResultSetAdapter set = connection.executeQuery(sql);
		if (set == null)
			return null;
		try {
			if (set.next()) {
				Account account = new Account();
				account.setId(set.getString(1));
				account.setUserName(userName);
				account.setPasswordHash(passwordHash);
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
		final String sql = String.format("SELECT id, username FROM %s", TableInfo.ACCOUNT_TABLE_NAME);
		ResultSetAdapter set = connection.executeQuery(sql);
		if (set == null)
			return null;
		try {
			List<Account> accounts = new ArrayList<>();
			while (set.next()) {
				Account account = new Account();
				account.setId(set.getString("id"));
				account.setUserName(set.getString("username"));
				account.setPasswordHash("");
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

	public AccountInfo getAccountInfo(@NotNull String userId) {
		final String sql = String.format("SELECT last_login, user_dir FROM %s WHERE user_id=%s", 
				TableInfo.ACCOUNT_INFO_TABLE_NAME,
				SQLHepler.prepareString(userId));
		ResultSetAdapter set = connection.executeQuery(sql);
		if (set == null)
			return null;
		try {
			if (set.next()) {
				AccountInfo accountInfo = new AccountInfo();
				accountInfo.setLastLogin(set.getInt("last_login"));
				accountInfo.setUserDir(set.getString("user_dir"));
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

	public boolean updateAccountInfo(@NotNull String userId, @NotNull AccountInfo accountInfo) {
		final String sql = String.format("UPDATE %s SET last_login=%d, user_dir=%s WHERE user_id=%s",
				TableInfo.ACCOUNT_INFO_TABLE_NAME,
				accountInfo.getLastLogin(), 
				SQLHepler.prepareString(accountInfo.getUserDir()),
				SQLHepler.prepareString(userId));
		return executeNonQuery(sql);
	}

	public boolean changeAccountPasswordHash(@NotNull String userId, @NotNull String passwordHash) {
		final String sql = String.format("UPDATE %s SET password_hash=%s WHERE id=%s",
				TableInfo.ACCOUNT_TABLE_NAME, 
				SQLHepler.prepareString(passwordHash), 
				SQLHepler.prepareString(userId));
		return executeNonQuery(sql);
	}

	public boolean deleteAccount(@NotNull String userId) {
		String sql = String.format("DELETE FROM %s WHERE user_id=%s", 
				TableInfo.ACCOUNT_INFO_TABLE_NAME, 
				SQLHepler.prepareString(userId));
		boolean resultDelAccountInfo = executeNonQuery(sql);
		
		sql = String.format("DELETE FROM %s WHERE id=%s", 
				TableInfo.ACCOUNT_TABLE_NAME, 
				SQLHepler.prepareString(userId));
		boolean resultDelAccount = executeNonQuery(sql);
		
		return resultDelAccount && resultDelAccountInfo;
	}
}
