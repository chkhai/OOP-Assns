<%@ page import="store_package.ProductCatalog" %>
<%@ page import="store_package.Product" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 6/9/2025
  Time: 11:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <%
        ProductCatalog pc = (ProductCatalog) application.getAttribute("Product Catalog");
        String p_id = request.getParameter("productID");
        Product p = pc.getProduct(p_id);
        String s = "store-images/";
    %>
    <title><%= p.getName() %></title>
    <h2><%= p.getName()%></h2>
    <img src = "<%=s + p.getImg()%>" alt = "Product Image"><br><br>
    <form action="ShoppingCart" method="POST">
        $<%= p.getPrice() %>
        <input name="productID" type="hidden" value="<%= p.getId() %>"/>
        <button type="submit">Add to Cart</button>
    </form>
</body>
</html>
