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
<%@ include file="../components/navbar.jsp" %>

<div class="page-container">
    <div class="restaurant-card card">
        <div class="card-content white-text">
            <span class="main-title text"><c:out value="${restaurant.restaurantName}"/></span>
            <span class="title2"><c:out value="${restaurant.phone}"/></span>
        </div>

    </div>
    <div class="card confirm-card">
        <div class="card-content white-text center">
            <span class="main-title text">Pediste la cuenta!</span>
            <div>
                <span class="main-title text">Felicitaciones ganaste <c:out value="${points}"/> puntos!!</span>
            </div>
            <div class="center">
                <a class="waves-effect waves-light btn confirm-btn green " href="<c:url value="/"/>">Volver</a>
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
        border-radius: 16px;
        display: grid;
    }

    

    .center{
        justify-content: center;
    }

    .with-margin{
        margin-top: 10%;
        margin-bottom: 10%;
    }


    .page-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
    }


    .confirm-card{
        display:flex;
        justify-content: center;
        width: 40%;
        max-width: 60%;
        margin-left: 5%;
        margin-right: 5%;
    }


</style>