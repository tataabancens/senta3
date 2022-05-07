<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
    <!-- Auto refresh each x seconds-->
    <meta http-equiv="refresh" content="300">

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
    <h1 class="presentation-text header-title"><spring:message code="Reservations.title"/></h1>
</div>
<div class="content-container">
    <table class="reservations">
        <thead>
        <tr>
            <th><h3 class="presentation-text"><spring:message code="Reservations.reservation"/></h3><a href="?orderBy=reservationid">^</a></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.name"/></h3><a href="?orderBy=customerid">^</a></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.people"/></h3><a href="?orderBy=qpeople">^</a><a href="orderBy=qpeople">^</a></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.hour"/></h3><a href="?orderBy=reservationhour">^</a></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.status"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.confirm"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.cancel"/></h3></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="reservation" items="${reservations}">
            <tr>
                <c:if test="${reservation.reservationStatus.name != 'REMOVED' }">
                <td data-label="Reserva" class="table-cell"><span class="text"><c:out value="${reservation.reservationId}"/></span></td>
                <td data-label="Nombre" class="table-cell"><span class="text"><c:out value="${reservation.customerName}"/></span></td>
                <td data-label="Personas" class="table-cell"><span class="text"><c:out value="${reservation.qPeople}"/></span></td>
                <td data-label="Hora" class="table-cell"><span class="text"><c:out value="${reservation.reservationHour}"/>:00</span></td>
                <td data-label="Estado" class="table-cell"><span class="text"><c:out value="${reservation.reservationStatus}"/></span></td>
                </c:if>

                <c:if test="${reservation.reservationStatus.name == 'OPEN' }">
                    <td data-label="Confirmar" class="table-cell">
                        <c:url value="/restaurant=${restaurantId}/seatCustomer=${reservation.reservationId}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <button type="submit" class="btn-floating large green">
                                <i class="material-icons">check_circle</i>
                            </button>
                        </form:form>
                    </td>
                    <td data-label="Cancelar" class="table-cell">
                        <a href="<c:url value="/restaurant=${restaurantId}/cancelReservationConfirmation/id=${reservation.reservationId}"/>" class="btn-floating large red">
                            <i class="material-icons">cancel</i>
                        </a>
                    </td>
                </c:if>

                <c:if test="${reservation.reservationStatus.name == 'CHECK_ORDERED' }">
                    <td data-label="Confirmar" class="table-cell">
                        <c:url value="/restaurant=${restaurantId}/showReceipt=${reservation.reservationId}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <button type="submit" class="btn-floating large blue">
                                <i class="material-icons">receipt</i>
                            </button>
                        </form:form>
                    </td>
                    <td data-label="Cancelar" class="table-cell">

                    </td>
                </c:if>

                <c:if test="${reservation.reservationStatus.name == 'SEATED' }">
                    <td data-label="Confirmar" class="table-cell">
                        <c:url value="/restaurant=${restaurantId}/orderCheckCustomer=${reservation.reservationId}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <button type="submit" class="btn-floating large green">
                                <i class="material-icons">check_circle</i>
                            </button>
                        </form:form>
                    </td>
                    <td data-label="Cancelar" class="table-cell">

                    </td>
                </c:if>

                <c:if test="${reservation.reservationStatus.name == 'CANCELED' }">
                    <td data-label="Confirmar" class="table-cell">

                    </td>
                    <td data-label="Cancelar" class="table-cell">
                        <c:url value="/restaurant=${restaurantId}/removeCustomer=${reservation.reservationId}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <button type="submit" class="btn-floating large red">
                                <i class="material-icons">cancel</i>
                            </button>
                        </form:form>
                    </td>
                </c:if>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>


<style>
    .presentation-text.header-title{
        color: #171616;
        font-size: 4rem;
    }
    .content-container{
        padding: 0 2%;
        margin: 40px auto 0;
        display: flex;
    }
    table{
        width: 100%;
        justify-content: space-evenly;
        border-collapse: collapse;
    }
    .reservations tbody tr td{
        padding: 0.3%;
        border: 1px solid black;
    }
    .presentation-text{
        color: black;
    }
    .text{
        font-size: 1.25rem;
        color: black;
    }
    .green{
        color: green;
    }
    .blue {
        color: blue;
    }
    .red{
        color: red;
    }
    .action-btn{
        display: none;
    }
    .table-cell{
        text-align: center;
        margin-bottom: 1.25rem;
        margin-top: 1.25rem;
    }
    @media (max-width: 768px) {
        .reservations thead{
            display: none;
        }
        .reservations, .reservations tbody, .reservations tr, .reservations td{
            display: block;
            width: 95%;
        }
        .reservations tbody{
            margin-bottom: 7%;
        }
        .reservations tbody tr td{
            text-align: center;
            padding-left: 50%;
            position: relative;
        }
        .reservations td:before{
            content: attr(data-label);
            color: black;
            border-color: white;
            font-family: "Goldplay", sans-serif;
            position: absolute;
            left: 0;
            width: 50%;
            padding-left: 2%;
        }
    }
</style>