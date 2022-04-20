<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="components/navbar.jsp" %>
<div class="row">
        <div class=" col s4 offset-s4 box card dish-card">
            <div class="card-content white-text">
                <span class="card-title title text price center">Ingresa tu codigo de reserva</span>
                <c:url value="/findReservation" var="postUrl"/>
                <form:form modelAttribute="findReservationForm" action="${postUrl}" method="post">
                    <div class="with-margin">
                        <div>
                            <form:errors path="reservationId" element="p" cssClass="error"/>
                            <form:label path="reservationId"></form:label>
                            <form:input type="number" path="reservationId" min="1"/>
                        </div>
                    </div>
                    <div class="center">
                        <input type="submit" value="Buscar" class="continue-btn">
                    </div>
                </form:form>
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


    .card{
        border-radius: 16px;
        display: grid;
    }

    .restaurant-card{
    }

    .dish-card{
        width: 100%;
    }

    .description{
        color:  #707070;
        font-size: 17px;
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

    .continue-btn{
        padding-inline: 7%;
        padding-block: 1%;
        border-radius: 16px;
        background-color: #37A6E6;
        margin-top: 5%;
        opacity: 57%;
    }

    .continue-btn:hover{
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

    .smaller{
        width: 100%;
        margin-bottom: 0;
        margin-top: 0;
    }

    .already-reserved-btn{
    }

</style>