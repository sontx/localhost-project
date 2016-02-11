package in.sontx.web.local;

public final class Config {
	public static final String WEB_NAME = "(localhost)";
	public static final String WEB_POWER_NAME = "NoEm";
	public static final String WEB_POWER_LINK = "http://www.sontx.in";
	public static final String WEB_AUTHOR_NAME = "sontx.in";
	public static final String WEB_AUTHOR_LINK = "http://www.sontx.in";
	public static final String ACCOUNT_USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
	public static final String ACCOUNT_PASSWORD_PATTERN = "^[a-z0-9_-]{6,40}$";
	public static final String WORKING_DIR = "e:/data/";
	public static final String ADMIN_USERNAME = "admin";
	public static final String ADMIN_PASSWORD = "admin";
	public static final String ADMIN_EMAIL = "xuanson33bk@gmail.com";
	public static final String DB_DRIVER_STRING = "com.mysql.jdbc.Driver";
	public static final String DB_CONNECTION_STRING = "jdbc:mysql://localhost:3306/lhdb?user=root&password=123abc@";
	
	Config() {
	}
}
