package in.sontx.web.local.bean;

public class AccountInfo {
	private int userId;
	private int lastLogin;
	private String userDir;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(int lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getUserDir() {
		return userDir;
	}

	public void setUserDir(String userDir) {
		this.userDir = userDir;
	}
}
