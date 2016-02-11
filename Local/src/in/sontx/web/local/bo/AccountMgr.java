package in.sontx.web.local.bo;

import java.util.List;
import java.util.regex.Pattern;

import com.sun.istack.internal.NotNull;

import in.sontx.web.local.Config;
import in.sontx.web.local.bean.Account;
import in.sontx.web.local.bean.AccountInfo;
import in.sontx.web.local.dao.AccountDb;
import in.sontx.web.local.service.ResourceManager;
import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.utils.DateTime;
import in.sontx.web.shared.utils.Security;

/**
 * Manage user accounts in database.
 * @author NoEm
 *
 */
public final class AccountMgr {
	public static AccountMgr createInstance(ISQLConnection connection) {
		return new AccountMgr(connection);
	}
	
	private final AccountDb accountDb;
	private final Pattern userNamePattern = Pattern.compile(Config.ACCOUNT_USERNAME_PATTERN);
	private final Pattern passwordPattern = Pattern.compile(Config.ACCOUNT_PASSWORD_PATTERN);
	
	private AccountMgr(ISQLConnection connection) {
		accountDb = new AccountDb(connection);
	}

	private boolean isValidUserName(String userName) {
		return userName == null || (userName = userName.trim()).length() < 3 ? false
				: userNamePattern.matcher(userName).matches();
	}

	private boolean isValidPassword(String password) {
		return password == null || (password = password.trim()).length() < 6 ? false
				: passwordPattern.matcher(password).matches();
	}
	
	public boolean createAccount(@NotNull String username, @NotNull String password) {
		if (!isValidUserName(username) || !isValidPassword(password))
			return false;
		
		String id = Security.getRandomUUID();
		String passwordHash = Security.getPasswordHash(password);
		
		String dir = Security.getRandomUUID();
		int lastLogin = DateTime.now().getTime();
		
		Account account = new Account();
		account.setId(id);
		account.setUserName(username);
		account.setPasswordHash(passwordHash);
		
		AccountInfo info = new AccountInfo();
		info.setUserDir(dir);
		info.setLastLogin(lastLogin);
		
		boolean result = accountDb.createAccount(account, info);
		if (result)
			ResourceManager.getInstance().getFileManager().createDirectory(dir);
		return result;
	}

	public Account getAccount(@NotNull String userName, @NotNull String password) {
		if (!isValidUserName(userName) || !isValidPassword(password))
			return null;
		String passwordHash = Security.getPasswordHash(password);
		Account account = accountDb.getAccount(userName, passwordHash);
		// update last time login info
		if (account != null) {
			String id = account.getId();
			AccountInfo info = accountDb.getAccountInfo(id);
			if (info != null) {
				info.setLastLogin(DateTime.now().getTime());
				accountDb.updateAccountInfo(id, info);
			}
		}
		return account;
	}

	public List<Account> getAllAccounts() {
		return accountDb.getAccounts();
	}

	public AccountInfo getAccountInfo(@NotNull String userId) {
		return Security.isValidUUID(userId) ? accountDb.getAccountInfo(userId) : null;
	}

	public boolean changePassword(@NotNull String userId, @NotNull String password) {
		return Security.isValidUUID(userId) && isValidPassword(password) ? 
			accountDb.changeAccountPasswordHash(userId, Security.getPasswordHash(password)) : false;
	}

	public boolean deleteAccount(@NotNull String userId) {
		return Security.isValidUUID(userId) ? accountDb.deleteAccount(userId) : false;
	}
}
