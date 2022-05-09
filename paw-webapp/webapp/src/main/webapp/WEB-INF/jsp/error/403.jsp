<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
</head>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<body>
<div class="pageContainer">
    <div class="card restaurant-card">
        <div>
            <p class="presentation-text center"><spring:message code="Error.login"/></p>
        </div>
        <div class="center">
            <a class="waves-effect waves-light btn confirm-btn" href="<c:url value="/logout"/>"><spring:message code="Error.login.logout"/></a>
        </div>
    </div>
</div>
</body>
</html>
<style>
    body{
        background: url("${pageContext.request.contextPath}/resources/images/restaurant-background.jpg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }
    .card{
        border-radius: .8rem;
    }
    .pageContainer{
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .center{
        justify-content: center;
    }
    .card.restaurant-card{
        justify-content: center;
        align-items: center;
        flex-direction: column;
    }
    .btn.confirm-btn{
        margin-bottom: 2em;
    }
</style>