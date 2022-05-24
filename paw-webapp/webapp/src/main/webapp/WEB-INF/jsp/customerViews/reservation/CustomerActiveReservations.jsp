<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Senta3</title>
</head>
<body>
<%@ include file="../../components/navbar.jsp" %>
<div class="restaurant-header" style="background-color: rgb(255, 242, 229);border-radius: 0px;">
    <div class="restaurant-info" style="margin-left: 2%;">
        <h1 class="presentation-text header-title"><spring:message code="Reservation.active"/></h1>
    </div>
</div>
<div class="contentContainer">
    <div class="reservation-list">
        <c:forEach var="reservation" items="${reservations}">
            <div class="card horizontal">
                <a href="<c:url value="menu/?reservationId=${reservation.id}"/>">
                    <div class="card-stacked">
                        <div class="card-content">
                            <span class="presentation-text">${customer.customerName}</span>
                            <p class="text description"><spring:message code="Customer.activereservations.people" arguments="${reservation.qPeople}"/></p>
                            <p class="text description"><spring:message code="Customer.activereservations.where" arguments="${reservation.restaurant.restaurantName}"/></p>
                            <p  class="text description">${reservation.startedAtTime.toLocalDateTime().dayOfMonth}/${reservation.startedAtTime.toLocalDateTime().monthValue}/${reservation.startedAtTime.toLocalDateTime().year}</p>
                            <p class="text description"><spring:message code="Customer.activereservations.hour" arguments="${reservation.reservationHour}"/></p>
                        </div>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
<style>
.contentContainer{
    display: flex;
    flex-wrap: wrap;
    width:100%;
    height: 100%;
    padding: 1%;
}
.filter-box{
    width: 20%;
    height: 100%;
    padding: 1.1%;
    border-radius: 1.25em;
    background-color: white;
    justify-content: center;
}
.presentation-text{
    color: black;
}
.card.horizontal{
    border-radius: .8rem;
    margin: 1%;
    max-height: 11rem;
    transition: 0.5s;
}
.reservation-list{
    width: 80%;
    height: 100%;
    display: flex;
    flex-wrap: wrap;
    padding: 0 3% 0 3%;
}
.card.horizontal:hover{
    transform: scale(1.1);
}
a{
    width: 100%;
    height: 100%;
}
</style>
