package in.sontx.web.shared.utils;

public final class SQLHepler {
	public static String prepareString(String s) {
		s = s.replace("'", "''");
		return String.format("'%s'", s);
	}
	
	public static String prepareSearchString(String s) {
		s = s.replace("'", "''");
		return String.format("'%%%s%%'", s);
	}

	SQLHepler() {
	}
}
