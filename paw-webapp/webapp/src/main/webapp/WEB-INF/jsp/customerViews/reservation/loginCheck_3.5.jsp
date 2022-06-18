<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
</head>
<body>
<%@ include file="../../components/navbar.jsp" %>
<div style="display: flex;justify-content: center;margin-top: 5%;">
    <div class="card">
        <span class="presentation-text">¿Tenes cuenta?</span>
        <div style="display: flex;flex-direction: column;margin-top: 7%;">
            <a href="" class="btn confirm-btn" style="margin-bottom: 7%;">Login</a>
            <a href="" class="btn confirm-btn">Continuar sin logearse</a>
        </div>
    </div>
</div>
</body>
</html>

<style>
    body{
        background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }
    .card{
        border-radius: .8rem;
        display: flex;
        flex-wrap: wrap;
        margin: 10px;
        align-content: center;
        flex-direction: column;
        min-height: 250px;
        min-width: 450px;
        width: 30%;
        padding: 20px;
    }
</style>
