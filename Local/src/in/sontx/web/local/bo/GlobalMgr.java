package in.sontx.web.local.bo;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import in.sontx.web.local.Config;
import in.sontx.web.local.bean.Account;
import in.sontx.web.local.bean.AccountInfo;
import in.sontx.web.local.dao.GlobalDb;
import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.utils.Security;

public final class GlobalMgr {
	private final GlobalDb globalDb;
	private final Pattern userNamePattern = Pattern.compile(Config.ACCOUNT_USERNAME_PATTERN);
	private final Pattern passwordPattern = Pattern.compile(Config.ACCOUNT_PASSWORD_PATTERN);

	public GlobalMgr(ISQLConnection cnn) {
		globalDb = new GlobalDb(cnn);
	}

	public void open() {
		try {
			globalDb.open();
		} catch (SQLException e) {
		}
	}

	public void close() {
		globalDb.close();
	}

	private boolean checkUserName(String userName) {
		return userName == null || (userName = userName.trim()).length() < 3 ? false
				: userNamePattern.matcher(userName).matches();
	}

	private boolean checkPassword(String password) {
		return password == null || (password = password.trim()).length() < 6 ? false
				: passwordPattern.matcher(password).matches();
	}

	public Account createAccount(String userName, String password) {
		if (checkUserName(userName) && checkPassword(password)) {
			String passwordHash = Security.getPasswordHash(password);
			String dir = String.format("{%s} - %s", UUID.randomUUID().toString(), userName);
			return globalDb.createAccount(userName, passwordHash, dir);
		}
		return null;
	}

	public Account getAccount(String userName, String password) {
		return checkUserName(userName) && checkPassword(password)
				? globalDb.getAccount(userName, Security.getPasswordHash(password)) : null;
	}

	public List<Account> getAccounts() {
		return globalDb.getAccounts();
	}

	public AccountInfo getAccountInfo(int userId) {
		return userId < 0 ? null : globalDb.getAccountInfo(userId);
	}

	public boolean changeAccountPassword(int userId, String newPassword) {
		return userId < 0 || !checkPassword(newPassword) ? false
				: globalDb.changeAccountPasswordHash(userId, Security.getPasswordHash(newPassword));
	}

	public boolean deleteAccount(int userId) {
		return userId < 0 ? false : globalDb.deleteAccount(userId);
	}
}
