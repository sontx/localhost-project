package in.sontx.web.shared.utils;

public final class Path {
	public static String validPart(String part) {
		part = part.replace('\\', '/');
		if (part.endsWith("/"))
			part = part.substring(0, part.length() - 1);
		return part;
	}
	
	public static String combineByArray(String[] args) throws IllegalArgumentException {
		if (args.length == 0)
			throw new IllegalArgumentException("Arguments are nothing!");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < args.length - 1; i++) {
			builder.append(validPart(args[i])).append('/');
		}
		builder.append(validPart(args[args.length - 1]));
		return builder.toString();
	}

	public static String combine(String... args) throws IllegalArgumentException {
		return combineByArray(args);
	}

	public static boolean isName(String arg) {
		return (arg == null || (arg = arg.trim()).length() == 0) ? false : (arg.contains("/") || arg.contains("\\"));
	}
	
	public static String getDirectoryName(String filePath) {
		filePath = validPart(filePath);
		return filePath.substring(0, filePath.lastIndexOf('/'));
	}
	
	Path() {
	}
}
