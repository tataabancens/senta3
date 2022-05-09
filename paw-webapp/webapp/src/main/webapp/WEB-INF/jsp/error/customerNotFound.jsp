<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>
    <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="../components/navbar.jsp" %>

<div class="row">

    <div class="col s4 offset-s4 center margin-top">
        <div class="card restaurant-card">
            <div class="card-content white-text">
                <div>
                    <p class="card-title title text price center"><spring:message code="Error.title"/></p>
                </div>
                <div>
                    <p class="card-title text"><spring:message code="Error.user.notfound"/></p>
                </div>
                <div class="center">
                    <c:url value="/"/>
                    <a class="waves-effect waves-light btn reservation-btn" href="<c:url value="/"/>"><spring:message code="Button.back"/></a>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>

<style>

    body{
        background-color: #F0F0F0;
    }

    .card{
        border-radius: 10px;
    }

    .reservation-btn{
        border-radius: 16px;
        background-color: #37A6E6;
        margin-top: 5%;
        opacity: 57%;
    }

    .reservation-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }

    .center{
        justify-content: center;
    }

    .margin-top{
        margin-top: 5%;
    }

</style>