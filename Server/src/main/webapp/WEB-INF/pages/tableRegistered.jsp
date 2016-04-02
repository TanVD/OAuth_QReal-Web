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
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" integrity="sha384-aUGj/X2zp5rLCbBxumKTCw2Z50WgIr1vs/PFN4praOTvYXWlVyh2UtNUU0KAUhAX" crossorigin="anonymous">

    <!--JQuery-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.4.min.js"></script>

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
</head>

<body>

<jsp:include page="include/navbar.jsp"/>

<div class="container">
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
                        <c:if test="${o.isAdmin() && o.username != 'Admin'}">
                            <td>

                            </td>
                        </c:if>
                    </tr>
                    <c:if test="${o.username != 'Admin'}">
                </form>
                </c:if>
                </c:forEach>
        </table>
    </c:if>
</div>
</body>