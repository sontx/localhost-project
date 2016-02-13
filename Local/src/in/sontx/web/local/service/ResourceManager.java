package in.sontx.web.local.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import in.sontx.web.local.Config;
import in.sontx.web.local.bean.Account;
import in.sontx.web.local.bo.AccountMgr;
import in.sontx.web.local.bo.FileMgr;
import in.sontx.web.local.bo.GuestFileMgr;
import in.sontx.web.local.bo.NoteMgr;
import in.sontx.web.local.dao.mysql.MySQLDbConnection;
import in.sontx.web.shared.dao.ISQLConnection;
import in.sontx.web.shared.utils.FileManager;
import in.sontx.web.shared.utils.Log;

public final class ResourceManager {
	private static ResourceManager instance;
	private ISQLConnection connection;
	private final AccountMgr accountMgr;
	private final GuestFileMgr guestFileMgr;
	private final HashMap<String, UserModuleHolder> userMap = new HashMap<>();
	private FileManager fileManager;

	public static void createInstance() {
		instance = new ResourceManager();
	}

	public static ResourceManager getInstance() {
		return instance;
	}

	public static void destroyInstance() {
		if (instance != null) {
			instance.release();
			instance = null;
		}
	}

	public AccountMgr getAccountMgr() {
		return accountMgr;
	}

	public FileManager getFileManager() {
		return fileManager;
	}

	public void generateUserSession(Account account, String sessionId) {
		if (account != null && sessionId != null) {
			NoteMgr noteMgr = new NoteMgr(connection, account.getId());
			FileMgr fileMgr = new FileMgr(connection, account.getId());
			UserModuleHolder holder = new UserModuleHolder(noteMgr, fileMgr);
			userMap.put(sessionId, holder);
		}
	}

	public void destroyUserSession(String sessionId) {
		if (sessionId != null)
			userMap.remove(sessionId);
	}

	public NoteMgr getNoteMgr(String sessionId) {
		return userMap.containsKey(sessionId) ? userMap.get(sessionId).noteMgr : null;
	}
	
	public FileMgr getFileMgr(String sessionId) {
		return userMap.containsKey(sessionId) ? userMap.get(sessionId).fileMgr : null;
	}
	
	public GuestFileMgr getGuestFileMgr() {
		return guestFileMgr;
	}

	private void release() {
		connection.close();
		userMap.clear();
	}

	ResourceManager() {
		try {
			fileManager = new FileManager(Config.WORKING_DIR);
		} catch (IOException e) {
			Log.e(e);
		}
		try {
			connection = new MySQLDbConnection();
			connection.open();
		} catch (ClassNotFoundException | SQLException e) {
			Log.e(e);
		}
		accountMgr = AccountMgr.createInstance(connection);
		guestFileMgr = new GuestFileMgr(connection);
	}

	private static class UserModuleHolder {
		public final NoteMgr noteMgr;
		public final FileMgr fileMgr;

		public UserModuleHolder(NoteMgr noteMgr, FileMgr fileMgr) {
			this.noteMgr = noteMgr;
			this.fileMgr = fileMgr;
		}
	}
}
