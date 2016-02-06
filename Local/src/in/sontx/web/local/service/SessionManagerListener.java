package in.sontx.web.local.service;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import in.sontx.web.local.bean.Account;

@WebListener
public class SessionManagerListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent sessionEvent) {
		HttpSession session = sessionEvent.getSession();
		Account account = (Account) session.getAttribute("account");
		if (account != null) {
			if (!ResourceManager.getInstance().generateUserSession(account, session.getId()))
				session.invalidate();
		}
	}

	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		ResourceManager.getInstance().destroyUserSession(sessionEvent.getSession().getId());
	}

}
