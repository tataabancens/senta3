<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
</head>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<body>
<h1><spring:message code="Error.login"/></h1>
<a href="/logout"><spring:message code="Error.login.logout"/></a>
</body>
</html>