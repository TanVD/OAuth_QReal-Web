<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
  <title>Login</title>
  <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">

  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" integrity="sha384-aUGj/X2zp5rLCbBxumKTCw2Z50WgIr1vs/PFN4praOTvYXWlVyh2UtNUU0KAUhAX" crossorigin="anonymous">

  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
</head>

<body>
<div class="container">
  <div class="row">
    <div class="col-sm-6 col-md-4 col-md-offset-4">
      <h1 class="text-center login-title">Sign in to continue to Auth</h1>
      <div class="account-wall">
        <img class="profile-img" src="https://cdn4.iconfinder.com/data/icons/mechanical-cogs-and-gear-wheel/500/cogwheel_configuration_configure_control_gear_gears_gearwheel_mechanics_pinion_rackwheel_screw-wheel_settings_tool_steel_machinery-512.png"
             alt="">
        <%--<form class="form-signin" action="/login" method="post">--%>
        <form class="form-signin" action="/login" method="post">
          <input type="text" name="username" class="form-control" placeholder="Email:" required autofocus>
          <input type="password" name="password" class="form-control" placeholder="Password:" required>
          <button class="btn btn-lg btn-primary btn-block" type="submit">
            Sign in</button>
          <input type="hidden" name="${_csrf.parameterName}"
                 value="${_csrf.token}" />
        </form>
      </div>

      <c:if test="${error}">
      <h2 class="text-center login-title">Password or login wrong</h2>
      </c:if>
      <a href="/register" class="text-center new-account">Create an account </a>
    </div>
  </div>
</div>
</body>

</html>
