<%@ page import="store_package.ProductCatalog" %>
<%@ page import="java.util.Vector" %>
<%@ page import="store_package.Product" %>
<html>
<body>
<title>Student Store</title>

<h2>Student Store</h2>
<h3>Items Available:</h3>

<ul>
    <%
        ProductCatalog pc = (ProductCatalog) application.getAttribute("Product Catalog");
        Vector<Product> v = pc.executeAllProductsQuery();

        for(int i = 0; i < v.size(); i++) {
    %>
    <li><a href="ProductDisplay.jsp?productID=<%= v.get(i).getId() %>"><%= v.get(i).getName() %></a></li>
    <%
        }
    %>
</ul>

</body>
</html>
