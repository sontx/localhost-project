package in.sontx.web.shared.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Security {
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

	Security() {
	}
}
