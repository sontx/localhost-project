package in.sontx.web.local.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ResourceInitializationListener implements ServletContextListener {
    
    public void contextInitialized(ServletContextEvent event)  { 
         ResourceManager.createInstance();
         event.getServletContext().addListener(new SessionListener());
    }

    public void contextDestroyed(ServletContextEvent event)  { 
         ResourceManager.destroyInstance();
    }
	
    private static class SessionListener  implements HttpSessionListener {

		@Override
		public void sessionCreated(HttpSessionEvent event) {
//			HttpSession session = event.getSession();
//			Account account = (Account) session.getAttribute("account");
//			ResourceManager.getInstance().generateUserSession(account, session.getId());
		}

		@Override
		public void sessionDestroyed(HttpSessionEvent event) {
			String sessionId = event.getSession().getId();
			ResourceManager.getInstance().destroyUserSession(sessionId);
		}
    	
    }
}
