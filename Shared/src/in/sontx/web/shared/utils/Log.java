package in.sontx.web.shared.utils;

public final class Log {
	private static Logable logable;

	public static void setFoctory(Logable _logable) {
		if (_logable != null)
			logable = _logable;
	}

	public static void e(Object obj) {
		String msg = obj != null ? obj.toString() : "NULL";
		if (obj instanceof Throwable)
			msg = obj.toString() + System.lineSeparator() + msg;
		logable.err(msg);
	}

	public static void d(Object obj) {
		logable.out(obj != null ? obj.toString() : "NULL");
	}

	Log() {
	}

	public interface Logable {
		void out(String s);

		void err(String s);
	}

	static {
		logable = new Logable() {

			@Override
			public void out(String s) {
				System.out.println(s);
			}

			@Override
			public void err(String s) {
				System.err.println(s);
			}
		};
	}
}
