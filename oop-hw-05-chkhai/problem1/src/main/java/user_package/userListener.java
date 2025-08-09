package user_package;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class userListener implements ServletContextListener {

    private accountManager am;

    public userListener() {
        am = new accountManager();
    }

    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        sc.setAttribute("accountManager", am);
    }
}
