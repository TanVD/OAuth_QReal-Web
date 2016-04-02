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

<div class="container">
    <table class="table">
        <c:if test="${not empty objects}">
            <tr>
                <th style="text-align:center">Client ID</th>
                <th style="text-align:center">Secret</th>
                <th style="text-align:center"> Scope</th>
            </tr>
            <c:forEach var="o" items="${objects}">
                <tr>
                    <td align="center">${o.clientId}</td>
                    <td align="center">${o.clientSecret}</td>
                    <td align="center">${o.scope}</td>
                    <td>
                        <a href="servers/configureServer/${o.clientId}">
                            <button class="btn btn-default btn-sm" title="Configure">
                                Configure
                            </button>
                        </a>
                    </td>
                </tr>
            </c:forEach>

        </c:if>
    </table>
    <a href="servers/addNewServer">
        <button class="btn btn-sm btn-success pull-right" type="submit">
            Add new client
        </button>
    </a>
</div>
</body>