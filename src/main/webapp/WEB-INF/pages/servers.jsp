<%--
  Created by IntelliJ IDEA.
  User: tanvd
  Date: 30.11.15
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
  <title>Servers Control Panel</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">
  <table class="table">
    <h2>Registered servers table</h2>
    <c:if test="${not empty objects}">

    <tr>
      <th>IP</th>
      <th>Format</th>
    </tr>
    <c:forEach var="o" items="${objects}">
    <tr>
      <td>${o.ip}</td>
      <td>${o.format}</td>
    </tr>
    </c:forEach>

    </c:if>
      <form class="form-signin" action="/servers/newServerAdded" method="post">
        <tr>
        <td><input type="text" name="ip" class="form-control" placeholder="IP" required autofocus></td>
        <td><input type="text" name="format" class="form-control" placeholder="Format" required></td>
        <td>
          <button class="btn btn-sm btn-success  btn-block" type="submit">
          Add
        </button>
        </td>
        </tr>
      </form>
    </table>
    <a href="/tableRegistered" class="btn btn-default" role="button">Users Control Panel</a>
</div>
</body>