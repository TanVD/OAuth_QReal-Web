<%--
  Created by IntelliJ IDEA.
  User: tanvd
  Date: 08.11.15
  Time: 0:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">
    <h2>Registered users table</h2>
    <c:if test="${not empty objects}">
        <table class="table">
            <tr>
                <th>Login</th>
                <th>Password</th>
            </tr>
            <c:forEach var="o" items="${objects}">
                <tr>
                    <td>${o.username}</td>
                    <td>${o.password}</td>
                </tr>
            </c:forEach>
        </table>
</c:if>
</div>
</body>