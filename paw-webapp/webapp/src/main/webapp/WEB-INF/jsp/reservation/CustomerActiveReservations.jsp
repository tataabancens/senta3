<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reservas</title>
</head>
<body>
<%@ include file="../components/navbar.jsp" %>
<div class="header">
    <h1 class="presentation-text header-title">Reservas</h1>
</div>
<div class="contentContainer">
    <div class="filter-box">
        <span class="presentation-text">Filtros:</span>
        <ul>
            <li><span class="text">algo</span></li>
            <li><span class="text">otro</span></li>
            <li><span class="text">eso</span></li>
        </ul>
    </div>
    <div class="reservation-list">
        <c:forEach var="reservation" items="${reservations}">
            <div class="card horizontal">
                <a href="<c:url value="menu/?reservationId=${reservation.reservationId}"/>">
                    <div class="card-stacked">
                        <div class="card-content">
                            <span class="text">${reservation.customerName}</span>
                            <p>Personas: ${reservation.qPeople}</p>
                            <p>En ${reservation.restaurantName}</p>
                            <p>A las ${reservation.reservationHour}</p>
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
    max-height: 20%;
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
