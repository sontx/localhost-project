package in.sontx.web.shared.net.www;

public abstract class Rule {
	public abstract boolean executable(HttpServletRequestWrapper request);
}
