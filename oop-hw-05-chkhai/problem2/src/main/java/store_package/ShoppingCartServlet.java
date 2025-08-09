package store_package;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/ShoppingCart")
public class ShoppingCartServlet extends HttpServlet {
    private RequestDispatcher rd;
    private ShoppingCart sc;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ShoppingCart sc = (ShoppingCart) req.getSession().getAttribute("ShoppingCart");
        if (sc == null) sc = new ShoppingCart();
        String productID = req.getParameter("productID");
        if (productID == null) {
            Map<String, String[]> mp = req.getParameterMap();
            for (Map.Entry<String, String[]> entry : mp.entrySet()) {
                String curr_id = entry.getKey();
                String[] values = entry.getValue();
                if (values == null || values.length == 0) continue;
                int quantity = Integer.parseInt(values[0]);
                sc.changeQuantity(curr_id, quantity);
            }
            req.getSession().setAttribute("ShoppingCart", sc);
            req.getRequestDispatcher("ShoppingCartDisplay.jsp").forward(req, resp);
            return;
        }
        sc.addProduct(productID);
        req.getSession().setAttribute("ShoppingCart", sc);
        req.getRequestDispatcher("ShoppingCartDisplay.jsp").forward(req, resp);
    }

}
