package user_package;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class loginServlet extends HttpServlet {

    private RequestDispatcher rd;

    public loginServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = request.getServletContext();
        accountManager am = (accountManager) sc.getAttribute("accountManager");
        if (am == null) {
            am = new accountManager();
            sc.setAttribute("accountManager", am);
        }
        String usr = request.getParameter("usr");
        String pwd = request.getParameter("pwd");
        boolean b = am.passwordCheck(usr, pwd);
        String filename = b ? "welcomePage.jsp" : "dataIncorrectPage.jsp";
        rd = request.getRequestDispatcher(filename);
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }

}
