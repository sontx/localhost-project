package in.sontx.web.shared.net.www;

import javax.servlet.http.HttpServletResponse;

public class HttpServletResponseWrapper {
	public final HttpServletResponse response;

	public HttpServletResponseWrapper(HttpServletResponse response) {
		this.response = response;
	}
}
