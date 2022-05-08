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
    <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
    <div class="row">
        <%@ include file="../../components/navbar.jsp" %>
    </div>
    <div class="restaurant-header">
        <div class="restaurant-info">
            <div>
                <i class="large material-icons">restaurant</i>
            </div>
            <div>
                <div class="presentation-text title restaurant-title">
                    <h3 class="presentation-text header-title"><c:out value="${restaurant.restaurantName}"/></h3>
                </div>
                <div class="presentation-text restaurant-description">
                    <span><spring:message code="Restaurant.phone"/> </span>
                    <span><c:out value="${restaurant.phone}"/></span>
                </div>
            </div>
        </div>
    </div>
    <div class="page-container">
        <div class="confirm-card">
            <div class="card">
                <div class="card-content white-text center">
                    <span class="text description"><spring:message code="Notifycustomer.subtitle"/></span>
                    <div class="with-margin">
                        <span class="text description"><c:out value="${reservation.reservationId}"/></span>
                    </div>
                    <p class="text description"><spring:message code="Notifycustomer.mail"/></p>
                    <p class="text description"><spring:message code="Notifycustomer.register"/></p>
                    <sec:authorize access="!isAuthenticated()">
                        <div class="center">
                            <a class="waves-effect waves-light btn confirm-btn" href="<c:url value="/registerShort/${reservation.customerId}/${reservation.reservationId}"/>"><spring:message code="Button.register"/></a>
                            <a class="waves-effect waves-light btn confirm-btn" href="<c:url value="/menu?reservationId=${reservation.reservationId}"/>"><spring:message code="Button.no.register"/></a>
                        </div>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <a class="waves-effect waves-light btn confirm-btn" href="<c:url value="/menu?reservationId=${reservation.reservationId}"/>"><spring:message code="Button.continue"/></a>
                    </sec:authorize>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

<style>
    i{
        color: #171616;
        margin-right: 25px;
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
        width: 100%;
        flex-wrap: wrap;
        justify-content: center;
        padding-top: 2%;
    }


    .confirm-card{
        display:flex;
        justify-content: center;
        min-width: 40%;
        max-width: 100%;
        margin-left: 5%;
        margin-right: 5%;
    }





</style>