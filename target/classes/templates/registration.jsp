<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h2>Registration Form</h2>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name"><br>
    <label for="login">Login:</label><br>
    <input type="text" id="login" name="login"><br>
    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password"><br>
    <input type="submit" value="Register">
</form>
</body>
</html>

