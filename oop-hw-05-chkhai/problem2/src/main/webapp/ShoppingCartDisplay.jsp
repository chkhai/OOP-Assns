<%@ page import="store_package.ShoppingCart" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.mysql.cj.Session" %>
<%@ page import="store_package.Product" %>
<%@ page import="store_package.ProductCatalog" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 6/9/2025
  Time: 11:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<title>Shopping Cart</title>
<h2>Shopping Cart</h2>
<form action = "ShoppingCart" method = "POST">
    <ul>
    <%
        double total_cost = 0;
        HttpSession s = request.getSession();
        ShoppingCart sc = (ShoppingCart) s.getAttribute("ShoppingCart");
        HashMap<String, Integer> mp = sc.getShoppingCart();
        ProductCatalog pc = new ProductCatalog();
        for(Map.Entry<String, Integer> entry : mp.entrySet()){
            String id = entry.getKey();
            Product p = pc.getProduct(id);
            int quantity = mp.get(id);
            total_cost += quantity * p.getPrice();
    %>
        <li>
            <input type="number" value="<%=quantity%>" name="<%=id%>" min="0">
            <%=p.getName()%>, <%=p.getPrice()%>
        </li>
        <%
            }
        %>
    </ul>
    <p>Total: $<%=total_cost%></p>
    <button type="submit">Update Cart</button>
    <br><br>
    <a href="index.jsp">Continue Shopping</a>
</form>
</body>
</html>
