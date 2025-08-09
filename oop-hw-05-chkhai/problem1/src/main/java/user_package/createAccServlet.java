package user_package;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/newAcc")
public class createAccServlet extends HttpServlet {

    private RequestDispatcher rd;

    public createAccServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        rd = request.getRequestDispatcher("/createAcc.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext sc = request.getServletContext();
        accountManager am = (accountManager) sc.getAttribute("accountManager");
        if (am == null) {
            am = new accountManager();
            sc.setAttribute("accountManager", am);
        }
        String usr = request.getParameter("usr");
        boolean b = am.accountExists(usr);
        String filename = !b ? "welcomePage.jsp" : "nameTakenPage.jsp";
        am.createAccount(usr, request.getParameter("pwd"));
        rd = request.getRequestDispatcher(filename);
        rd.forward(request, response);

    }
}
