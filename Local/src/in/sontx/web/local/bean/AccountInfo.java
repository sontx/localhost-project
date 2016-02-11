package in.sontx.web.local.bean;

import com.sun.istack.internal.NotNull;

public class AccountInfo {
	private int lastLogin;
	private String userDir;

	public int getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(int lastLogin) {
		this.lastLogin = lastLogin;
	}

	@NotNull
	public String getUserDir() {
		return userDir;
	}

	public void setUserDir(@NotNull String userDir) {
		this.userDir = userDir;
	}
}
