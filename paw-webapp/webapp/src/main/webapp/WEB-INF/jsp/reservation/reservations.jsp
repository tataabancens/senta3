<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <title>Title</title>

</head>
<body>
<!--<c:forEach var="reservation" items="${reservations}">

            <div class="notification-item">
                <span class="title2">Hora: <c:out value="${reservation.reservationHour}"/>:00</span>
                <div>
                    <span class="title2">Id: <c:out value="${reservation.reservationId}"/></span>
                </div>
                <span class="title2">Estado: <c:out value="${reservation.reservationStatus}"/>
                    <a class="waves-effect waves-light btn red confirm-btn" href="cancelReservationConfirmation/id=${reservation.reservationId}">Cancelar</a>
                    <a class="waves-effect waves-light btn red confirm-btn" href="seatCustomer=${reservation.reservationId}">Sentar</a>
            </div>
        </c:forEach>-->
<div class="header">
    <span class="presentation-text title header-title"><h2>Reservas</h2></span>
</div>
<div class="content-container">
    <table class="reservations-header">
        <thead>
            <tr>
                <th><h3 class="presentation-text">Nombre</h3></th>
                <th><h3 class="presentation-text">Personas</h3></th>
                <th><h3 class="presentation-text">Hora</h3></th>
                <th><h3 class="presentation-text">Reserva</h3></th>
                <th><h3 class="presentation-text">Estado</h3></th>
                <th><h3 class="presentation-text">Acciones</h3></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="reservation" items="${reservations}">
                <tr>
                    <td data-label="Nombre"><span class="text table-cell">FullReservation</span></td>
                    <td data-label="Personas"><span class="text table-cell">FullReservation</span></td>
                    <td data-label="Hora"><span class="text table-cell"><c:out value="${reservation.reservationHour}"/>:00</span></td>
                    <td data-label="Reserva"><span class="text table-cell"><c:out value="${reservation.reservationId}"/></span></td>
                    <td data-label="Acciones">
                        <a href="#!" class="btn-floating green">
                            <i class="material-icons">check</i>
                        </a>
                        <a href="#!" class="btn-floating red">
                            <i class="material-icons">close</i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <!--<c:forEach var="reservation" items="${reservations}">

        <div class="reservation-card">
            <h2 class="presentation-text card-title">Pepito</h2>
        </div>
    </c:forEach>-->
</div>
</body>
</html>


<style>
    body{
        background-color: black;
    }
    .header{
        background: black;
        background: linear-gradient(90deg, white 7%, black 88%);
        display: flex;
        flex-direction: row;
        justify-content: center;
        color: white;
        min-height: 15%;
        font-size: 30rem;
        border-radius: 20px;
        align-items: center;
        margin: 20px;
        padding: 15px;
    }
    .header-title{
        color: white;
    }
    .content-container{
        padding: 2%;
        display: flex;
    }
    table{
        width: 100%;
        justify-content: space-evenly;
    }
    .presentation-text{
        color: white;
    }
    .text{
        color:white;
    }
    .green{
        color: green;
    }
    .red{
        color: red;
    }
    .text.table-cell{
        font-size: 1.25rem;
        justify-content: center;
    }
</style>