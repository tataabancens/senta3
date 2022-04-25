<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
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
<%@ include file="components/navbar.jsp" %>

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
                <div class="center">
                    <a class="waves-effect waves-light btn reservation-btn green " href="${pageContext.request.contextPath}">Volver</a>
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
        border-radius: 16px;
        display: grid;
    }


    .dish-card{
        width: 100%;
    }


    .reservation-btn{
        display: flex;
        border-radius: 16px;
        background-color: #37A6E6;
        margin-top: 5%;
        opacity: 57%;
        width: 35%;
        min-width: 10%;
        font-size: 1vw;
        text-align: center;
    }

    .reservation-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
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


    .restaurant-card{
        display:flex;
        margin-left: 5%;
        width: 20%;
        max-width: 40%;
        margin-right: 5%;
        height: 20%;
    }


</style>