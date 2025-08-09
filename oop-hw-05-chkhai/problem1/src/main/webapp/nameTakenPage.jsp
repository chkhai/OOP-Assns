<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 6/7/2025
  Time: 10:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <title>Create New Account</title>
    <h2>The Name <%= request.getParameter("usr")%> Is Already In Use</h2>
    <h3> Please enter another name and password.</h3>
    <form action = "newAcc" method = "POST">
        Username: <input type = "text" name = "usr"><br><br>
        Password: <input type = "text" name = "pwd">
        <button type = "submit" > Login </button>
    </form>
</body>
</html>
