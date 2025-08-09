<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 6/7/2025
  Time: 10:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <body>
    <title>Welcome <%= request.getParameter("usr") %></title>
    <h2> Welcome <%= request.getParameter("usr") %>  </h2>
  </body>
</html>
