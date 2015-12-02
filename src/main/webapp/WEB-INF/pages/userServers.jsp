<%--
  Created by IntelliJ IDEA.
  User: tanvd
  Date: 01.12.15
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
      <title>Servers Available</title>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
      <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<h2>You are registered on this servers</h2>
<c:if test="${not empty objects}">
  <table class="table">
    <tr>
      <th>IP</th>
    </tr>
    <c:forEach var="o" items="${objects}">
      <tr>
        <td>${o}</td>
      </tr>
    </c:forEach>
  </table>
</c:if>
</body>
</html>
