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

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
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
