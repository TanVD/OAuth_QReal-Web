<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>OAuth server</title>

  <jsp:include page="../include/head.jsp"/>

</head>

<body>

<div class="container">

  <h1>OAuth Server OAuth2 Error</h1>

  <p>
    <c:out value="${message}" />
    (
    <c:out value="${error.summary}" />
    )
  </p>
  <p>Please go back to your client application and try again, or
    contact the owner and ask for support</p>

</div>

</body>
</html>