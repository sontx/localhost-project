package in.sontx.web.shared.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.regex.Pattern;

public final class Security {
	private static final String UUID_PATTERN = "^[a-f0-9]{32}$";
	private static final Pattern uuidPattern = Pattern.compile(UUID_PATTERN);
	
	public static String convertToSHA1(byte[] st) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			return Convert.bytesToHexString(md.digest(st));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String convertToMD5(byte[] st) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			return Convert.bytesToHexString(md.digest(st));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getStringHash(String st) {
		if (st == null)
			return null;
		return convertToSHA1(st.getBytes(Charset.forName("UTF-8")));
	}

	public static String getPasswordHash(String password) {
		return getStringHash(password);
	}
	
	public static String getRandomUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static boolean isValidUUID(String uuid) {
		return uuid == null || (uuid = uuid.trim()).length() != 32 ? false :
			uuidPattern.matcher(uuid).matches();
	}

	Security() {
	}
}
