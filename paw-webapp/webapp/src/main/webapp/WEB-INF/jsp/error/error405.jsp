<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->

    <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="../components/navbar.jsp" %>

<div class="pageContainer">
    <div class="card restaurant-card">
        <div>
            <p class="presentation-text center"><spring:message code="Error.title"/></p>
        </div>
        <div>
            <p class="text description"><spring:message code="Error.405"/></p>
        </div>
        <div>
            <p class="text description"><spring:message code="Error.dontworry"/></p>
        </div>
        <div class="center">
            <a class="waves-effect waves-light btn confirm-btn" href="<c:url value="/"/>"><spring:message code="Button.back"/></a>
        </div>
    </div>
</div>
</body>
</html>

<style>

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
    .text.description{
        font-size: clamp(1rem,1vw,3rem);
    }
    .card.restaurant-card{
        padding: 1rem;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        min-width: 9em;
    }
    .btn.confirm-btn{
        margin-bottom: 2em;
    }

    body{
        background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }

</style>