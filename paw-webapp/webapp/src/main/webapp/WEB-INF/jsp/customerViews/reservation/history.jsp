<%--
  Created by IntelliJ IDEA.
  User: gonza
  Date: 01/05/2022
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <title>Senta3</title>
</head>
<body>
<%@ include file="../../components/navbar.jsp" %>
<div class="restaurant-header" style="background-color: rgb(255, 242, 229);border-radius: 0px;">
    <div class="restaurant-info" style="margin-left: 2%;">
        <h1 class="presentation-text header-title"><spring:message code="History.title"/></h1>
    </div>
</div>
<div class="contentContainer">
    <div class="points">
        <div class="reservations-header">
            <h3 class="presentation-text"><spring:message code="History.points" arguments="${customer.points}"/></h3>
        </div>
        <div class="progress-bar" style="--width:${progressBarNumber}"></div>
        <c:if test="${progressBarNumber > 100}">
            <span class="presentation-text">100%</span>
        </c:if>
        <c:if test="${progressBarNumber <= 100}">
            <span class="presentation-text">${progressBarNumber}%</span>
        </c:if>
    </div>
    <div class="reservations">
        <div class="reservations-header">
            <h3 class="presentation-text"><spring:message code="History.reservations"/></h3>
        </div>
        <div class="reservationList">
            <c:forEach var="reservation" items="${reservations}">
                <div class="card horizontal">
                    <div class="card-stacked">
                        <div class="card-content">
                            <span class="presentation-text">${customer.customerName}</span>
                            <p class="text description"><spring:message code="Customer.activereservations.people" arguments="${reservation.qPeople}"/></p>
                            <p  class="text description"><spring:message code="Createreservation.register.date" arguments="${reservation.reservationHour}"/> ${reservation.getReservationOnlyDate()}</p>
                            <p class="text description"><spring:message code="Customer.activereservations.where" arguments="${reservation.restaurant.restaurantName}"/></p>
                            <p class="text description"><spring:message code="Customer.activereservations.hour" arguments="${reservation.reservationHour}"/></p>
                            <p  class="text description"><spring:message code="Reservations.status"/>: ${reservation.reservationStatus}</p>
                            <a href="<c:url value="/history/reservation?reservationSecurityCode=${reservation.securityCode}"/>" class="waves-effect waves-light btn confirm-btn green text description"><spring:message code="Kitchen.title"/></a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>

<style>
    .contentContainer{
        display: flex;
        flex-direction: column;
        padding: 0 2% 0 2%;
    }
    .points{
        width: 100%;
        height: 30%;
    }
    .reservations{
        width: 100%;
        height: 70%;
    }
    .reservationList{
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        justify-content: flex-start;
    }
    .card.horizontal{
        margin: 1%;
        min-width: 20%;
        max-width: 30%;
        border-radius: .8rem;
    }
    .reservations-header{
        justify-content: center;
    }
    .progress-bar{
        position: relative;
        width: 40%;
        height: 3em;
        background-color: white;
        border-radius: 1.5em;
        color: white;
    }
    .progress-bar::before{
        content: attr(data-label);
        display: flex;
        align-items: center;
        position: absolute;
        left: .5em;
        top: .5em;
        bottom: .5em;
        width: calc(var(--width,0) * 1%);
        min-width: 2rem;
        max-width: calc(100% - 1em);
        background-color: green;
        border-radius: 1em;
        padding: 1em;
    }
</style>
<script type="text/javascript">


</script>
