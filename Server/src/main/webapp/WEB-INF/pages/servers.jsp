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
<!--.navbar-->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">OAuth Server</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/servers"><span class="glyphicon glyphicon-cloud"></span> Table servers</a></li>
                <li><a href="/tableRegistered"><span class="glyphicon glyphicon-user"></span> Table users</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${name} <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/logout">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<!--/.navbar -->
<c:if test="${haveErrors}">
    <div class="container">
        <div class="row">
            <div class="alert-group">
                <div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <strong>Warning!</strong> Some servers do not respond.
                </div>
            </div>
        </div>
    </div>
</c:if>
<div class="container">
    <table class="table">
        <c:if test="${not empty objects}">
            <tr>
                <th style="text-align:center">Name</th>
                <th style="text-align:center">IP</th>
                <th style="text-align:center">Status</th>
                <th></th>
            </tr>
            <c:forEach var="o" items="${objects}">
                <tr>
                    <td align="center">${o.name}</td>
                    <td align="center">${o.ip}</td>
                        <td align="center">
                        <c:if test="${o.connectionStateForJsp == 1}">
                            <kbd>StandBy</kbd>
                        </c:if>
                        <c:if test="${o.connectionStateForJsp == 2}">
                            <kbd>Waiting for answer</kbd>
                        </c:if>
                        <c:if test="${o.connectionStateForJsp == 3}">
                            <kbd>Error</kbd>
                        </c:if>
                        <c:if test="${o.connectionStateForJsp == 4}">
                            <kbd>Success</kbd>
                        </c:if>
                        </td>
                    <td align="center">
                        <div class="btn-group" role="group" aria-label="Group">
                        <a href="servers/refreshServer/${o.ip}">
                            <button class="btn btn-default btn-sm" title="Refresh">
                            Refresh
                            </button>
                        </a>
                        <a href="servers/configureServer/${o.ip}">
                            <button class="btn btn-default btn-sm" title="Configure">
                                Configure
                            </button>
                        </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>

        </c:if>
    </table>
    <a href="servers/addNewServer">
        <button class="btn btn-sm btn-success pull-right" type="submit">
            Add new server
        </button>
    </a>
</div>
</body>