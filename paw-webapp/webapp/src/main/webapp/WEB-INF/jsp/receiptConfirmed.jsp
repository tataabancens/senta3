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

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="components/navbar.jsp" %>

<div class="row">
    <div class="col s3">
        <div class="card restaurant-card">
            <div class="card-content white-text">
                <span class="card-title text"><c:out value="${restaurant.restaurantName}"/></span>
                <span class="text"><c:out value="${restaurant.phone}"/></span>
            </div>
        </div>
    </div>

    <div class="page-container">
        <div class="card confirm-card">
            <div class="card-content white-text">
                <span class="card-title text price center">Pediste la cuenta!</span>
                <span class="card-title text price center">Muchas gracias!</span>
                    <div class="row margin-0">
                        <div class="col s12 center">
                            <a class="waves-effect waves-light btn reservation-btn green " href="${pageContext.request.contextPath}">Volver al inicio</a>
                        </div>
                    </div>

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
    .text{
        color:  #707070
    }

    .summary{
        margin-top: 20px;
    }


    .card{
        border-radius: 16px;
        display: grid;
    }

    .restaurant-card{
    }

    .dish-card{
        width: 100%;
    }

    .items-title{
        color:  #707070;
        font-size: 18px;
    }

    .title2{
        justify-content: center;
        color:  #707070;
        font-size: 20px;

    }

    .price{
        font-size: 25px;
        font-weight: bold;
        color: black;
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

    .page-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
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