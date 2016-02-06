package in.sontx.web.shared.net.www;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import in.sontx.web.shared.utils.Log;

public class HttpServletRequestWrapper {
	public final HttpServletRequest request;

	private boolean isNullOrEmpty(String st) {
		return st == null || st.length() == 0;
	}

	public int getInt(String key, int def) {
		String value = getParameter(key, null);
		try {
			return value == null ? def : Integer.parseInt(value);
		} catch (NumberFormatException e) {
			Log.e(e);
		}
		return def;
	}

	public String getParameter(String key) {
		return getParameter(key, null);
	}

	public String getParameter(String key, String def) {
		String value = request.getParameter(key);
		return isNullOrEmpty(value) ? def : value;
	}

	public void setAttribute(String key, Object obj) {
		request.setAttribute(key, obj);
	}

	public Object getAttribute(String key) {
		return request.getAttribute(key);
	}

	public Object getAttribute(String key, Object def) {
		Object obj = getAttribute(key);
		return obj != null ? obj : def;
	}
	
	public Object popAttribute(String key, Object def) {
		Object ret = getAttribute(key, def);
		request.removeAttribute(key);
		return ret;
	}

	public boolean hasSession() {
		return getSession() != null;
	}

	public HttpSession getSession() {
		return request.getSession(false);
	}

	public HttpSession createSession() {
		return request.getSession(true);
	}

	public void deleteSession() {
		if (hasSession())
			getSession().invalidate();
	}

	public void setSessionAttribute(String key, Object obj) {
		if (hasSession())
			getSession().setAttribute(key, obj);
	}

	public Object getSessionAttribute(String key) {
		return hasSession() ? getSession().getAttribute(key) : null;
	}

	public boolean hasSessionAttribute(String key, Class<?> cls) {
		if (!hasSession())
			return false;
		Object obj = getSession().getAttribute(key);
		return instanceOf(obj, cls);
	}

	public boolean hasAttribute(String key, Class<?> cls) {
		if (!hasSession())
			return false;
		Object obj = request.getAttribute(key);
		return instanceOf(obj, cls);
	}

	private boolean instanceOf(Object obj, Class<?> cls) {
		return obj != null && cls.isAssignableFrom(obj.getClass());
	}

	public void removeAttribute(String key) {
		request.removeAttribute(key);
	}

	public HttpServletRequestWrapper(HttpServletRequest request) {
		this.request = request;
	}
}
