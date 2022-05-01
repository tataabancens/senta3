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
    <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="../components/navbar.jsp" %>
<div class="form-container">
            <div class="card card-content">
                <span class="main-title">Ingresa tu codigo de reserva</span>
                <c:url value="/findReservation" var="postUrl"/>
                <form:form modelAttribute="findReservationForm" action="${postUrl}" method="post">
                    <div class="reservationId">
                        <div>
                            <form:errors path="reservationId" element="p" cssClass="error"/>
                            <form:label path="reservationId">Codigo:</form:label>
                            <form:input type="number" path="reservationId" min="1"/>
                        </div>
                    </div>
                    <div class="submit center">
                        <input type="submit" value="Buscar" class="continue-btn">
                    </div>
                </form:form>
            </div>
</div>
</body>
</html>

<style>

    .form-container{
        display: flex;
        padding-top: 30px;
        justify-content: center;
    }

    .card{
        border-radius: 16px;
        padding: 20px;
        display: flex;
    }
    .card.card-content{
        justify-content: center;
        flex-direction: column;
        font-family: "Segoe UI", Lato, sans-serif;
        min-height: 150px;
        height: 300px;
        max-height: 800px;
        min-width: 400px;
        width: 500px;
        max-width: 600px;
    }


    .continue-btn{
        font-family: "Goldplay", sans-serif;
        border-radius: 10px;
        background-color: #37A6E6;
        margin-top: 5%;
        opacity: 57%;
        padding: 2%;
        color: white;
    }

    .continue-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }


</style>