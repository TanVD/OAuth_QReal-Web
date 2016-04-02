
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Server Configuration</title>
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

<!-- panel  -->
<form role="form" class="col-md-6 col-md-offset-3" action="serverConfigured" method="post">

  <div  class="panel panel-default ">
    <div align="center" class="panel-body form-horizontal payment-form">

      <div class="form-group">
        <label for="clientId" class="col-sm-3 control-label">Client ID</label>
        <div class="col-sm-9">
          <input type="text" class="form-control" id="clientId" name="clientId" value="${clientId}" required aria-disabled="true">
        </div>
      </div>

      <div class="form-group">
        <label for="scopes" class="col-sm-3 control-label">Scopes</label>
        <div class="col-sm-9">
          <input type="text" class="form-control" id="scopes" name="scopes" value="${scopes}" required>
        </div>
      </div>

      <div class="form-group">
        <label for="secret" class="col-sm-3 control-label">Secret</label>
        <div class="col-sm-9">
          <input type="text" class="form-control" id="secret" name="secret" value="${secret}" required>
        </div>
      </div>

      <div class="form-group">
        <div class="col-sm-12 text-right">
          <button type="submit" class="btn btn-default preview-add-button">
            <span class="glyphicon glyphicon-plus"></span> Reconfigure
          </button>
        </div>
      </div>
    </div>
  </div>
</form>
<!-- / panel  -->

</body>
</html>
