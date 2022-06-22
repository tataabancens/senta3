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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="../../components/navbar.jsp" %>

<div class="page-container">
    <div class="card confirm-card">
        <div class="card-content white-text center">
            <span class="presentation-text" style="color: #171616; font-size: 2rem;"><spring:message code="Receipt.finish"/></span>
            <div style="margin:5%;">
                <span class="presentation-text" style="color: #171616;"><spring:message code="Receipt.points" arguments="${points}"/></span>
            </div>
            <div class="center">
                <a class="waves-effect waves-light btn confirm-btn green " href="<c:url value="/"/>"><spring:message code="Button.back"/></a>
            </div>
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
        border-radius: 16px;
        display: grid;
    }

    

    .center{
        justify-content: center;
    }



    .page-container {
        display: flex;
        flex-wrap: wrap;
        align-items: center;
        justify-content: center;
    }


    .confirm-card{
        display:flex;
        justify-content: center;
        width: 40%;
        height: 50%;
        margin-top: 5%;
        max-width: 60%;
        margin-left: 5%;
        margin-right: 5%;
    }


</style>