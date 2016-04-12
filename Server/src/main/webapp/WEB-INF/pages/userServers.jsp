<%--
  Created by IntelliJ IDEA.
  User: tanvd
  Date: 01.12.15
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
      <title>Servers Available</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <jsp:include page="include/head.jsp"/>

</head>
<body>

<jsp:include page="include/navbar.jsp"/>

<%--<c:if test="${not empty objects}">--%>
  <table class="table">
    <tr>
      <th>Name</th>
      <th>IP</th>
      <th>Status</th>
    </tr>
    <%--<c:forEach var="o" items="${objects}">--%>
      <%--<tr>--%>
          <%--<td align="center">${o.name}</td>--%>
          <%--<td align="center">${o.ip}</td>--%>
          <%--<td align="center">--%>
              <%--<c:if test="${o.connectionStateForJsp == 1}">--%>
                  <%--<kbd>StandBy</kbd>--%>
              <%--</c:if>--%>
              <%--<c:if test="${o.connectionStateForJsp == 2}">--%>
                  <%--<kbd>Waiting for answer</kbd>--%>
              <%--</c:if>--%>
              <%--<c:if test="${o.connectionStateForJsp == 3}">--%>
                  <%--<kbd>Error</kbd>--%>
              <%--</c:if>--%>
              <%--<c:if test="${o.connectionStateForJsp == 4}">--%>
                  <%--<kbd>Success</kbd>--%>
              <%--</c:if>--%>
          <%--</td>--%>
      <%--</tr>--%>
    <%--</c:forEach>--%>
  </table>
<%--</c:if>--%>
</body>
</html>
