package store_package;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;

@WebListener
public class Listener implements HttpSessionListener, ServletContextListener {
    private ProductCatalog pc;

    public void contextInitialized(ServletContextEvent event) {
        try {
            pc = new ProductCatalog();
            ServletContext sc = event.getServletContext();
            sc.setAttribute("Product Catalog", pc);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
//        System.out.println("ProductCatalog initialized successfully!");
    }

    public void contextDestroyed(ServletContextEvent event) {

    }

    public void sessionCreated(HttpSessionEvent event) {
        ShoppingCart cart = new ShoppingCart();
        HttpSession session = event.getSession();
        session.setAttribute("ShoppingCart", cart);
    }

    public void sessionDestroyed(HttpSessionEvent event) {

    }
}
