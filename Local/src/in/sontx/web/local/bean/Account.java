package in.sontx.web.local.bean;

import com.sun.istack.internal.NotNull;

public class Account {
	private String id;
	private String userName;
	private String passwordHash;

	@NotNull
	public String getUserName() {
		return userName;
	}

	public void setUserName(@NotNull String userName) {
		this.userName = userName;
	}

	@NotNull
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(@NotNull String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@NotNull
	public String getId() {
		return id;
	}

	public void setId(@NotNull String id) {
		this.id = id;
	}
}
