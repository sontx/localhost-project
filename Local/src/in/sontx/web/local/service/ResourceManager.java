package in.sontx.web.local.service;

import java.sql.SQLException;
import java.util.HashMap;

import in.sontx.web.local.Config;
import in.sontx.web.local.bean.Account;
import in.sontx.web.local.bean.AccountInfo;
import in.sontx.web.local.bo.GlobalMgr;
import in.sontx.web.local.bo.UserMgr;
import in.sontx.web.local.dao.sqlite.GlobalSQLiteDbConnection;
import in.sontx.web.local.dao.sqlite.UserSQLiteDbConnection;
import in.sontx.web.shared.utils.Log;
import in.sontx.web.shared.utils.Path;

public final class ResourceManager {
	private static ResourceManager instance;
	public final GlobalMgr globalMgr;
	private final HashMap<String, UserMgr> userMap = new HashMap<>();

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

	public boolean generateUserSession(Account account, String sessionId) {
		AccountInfo accountInfo = globalMgr.getAccountInfo(account.getUserId());
		if (accountInfo != null) {
			String userDir = accountInfo.getUserDir();
			String dbFilePath = Path.combine(Config.WORKING_DIR, userDir, Config.USER_DATABASE_FILE);
			UserMgr userMgr = new UserMgr(new UserSQLiteDbConnection(dbFilePath));
			try {
				userMgr.open();
				userMap.put(sessionId, userMgr);
				return true;
			} catch (SQLException e) {
				userMgr.close();
				Log.e(e);
			}
		}
		return false;
	}

	public void destroyUserSession(String sessionId) {
		if (sessionId != null) {
			UserMgr userMgr = userMap.get(sessionId);
			if (userMgr != null) {
				userMgr.close();
				userMap.remove(sessionId);
			}
		}
	}

	public UserMgr getUserSession(String sessionId) {
		return userMap.get(sessionId);
	}

	private void release() {
		globalMgr.close();
		for (String key : userMap.keySet()) {
			UserMgr userMgr = userMap.get(key);
			userMgr.close();
		}
		userMap.clear();
	}

	ResourceManager() {
		globalMgr = new GlobalMgr(new GlobalSQLiteDbConnection(Config.GLOBAL_DATABASE_PATH));
		globalMgr.open();
	}
}
