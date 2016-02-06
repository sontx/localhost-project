package in.sontx.web.local.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ResourceInitializationListener implements ServletContextListener {
    
    public void contextInitialized(ServletContextEvent arg0)  { 
         ResourceManager.createInstance();
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
         ResourceManager.destroyInstance();
    }
	
}
