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
    <title>Users Control Panel</title>
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
            <c:if test="${!o.isAdmin()}">
            <form class="form-signin" action="/tableRegistered/grantUserAdminRights/${o.username}" method="post">
                </c:if>
                <c:if test="${o.isAdmin() && o.username != 'Admin'}">
                <form class="form-signin" action="/tableRegistered/withdrawUserAdminRights/${o.username}" method="post">
                    </c:if>
                    <tr>
                        <td>${o.username}</td>
                        <td>${o.password}</td>
                        <c:if test="${!o.isAdmin()}">
                            <td>
                                <button class="btn btn-sm btn-success  btn-block" type="submit">
                                    Grant Admin Rights
                                </button>
                            </td>
                        </c:if>
                        <c:if test="${o.isAdmin() && o.username != 'Admin'}">
                            <td>
                                <button class="btn btn-sm btn-danger  btn-block" type="submit">
                                    Withdraw Admin Rights
                                </button>
                            </td>
                        </c:if>
                    </tr>
                    <c:if test="${o.username != 'Admin'}">
                </form>
                </c:if>
                </c:forEach>
        </table>
    </c:if>
    <a href="/servers" class="btn btn-default" role="button">Servers Control Panel</a>
</div>
</body>