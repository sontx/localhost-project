package in.sontx.web.shared.utils;

import org.apache.commons.lang3.StringEscapeUtils;

public final class Convert {
	public static String bytesToHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
	
	public static String toHtml(String plainText) {
		return StringEscapeUtils.escapeHtml4(plainText);
	}

	Convert() {
	}
}
