<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 6/7/2025
  Time: 10:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <title>Information Incorrect</title>
    <h2> Please Try Again</h2>
    <h3> Either your username of password is incorrect. Please try again.</h3>
    <form action="login" method="POST">
        Username: <input type="text" name="usr"/><br><br>
        Password: <input type="password" name="pwd"/>
        <button type="submit">Login</button>
    </form>
    <p><a href = "createAcc.jsp">Create New Account</a></p>
</body>
</html>
