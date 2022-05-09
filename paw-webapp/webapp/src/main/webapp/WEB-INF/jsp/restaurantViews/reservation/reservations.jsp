<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.rawgit.com/Dogfalo/materialize/fc44c862/dist/css/materialize.min.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
    <script src="https://cdn.rawgit.com/Dogfalo/materialize/fc44c862/dist/js/materialize.min.js"></script>
    <script type="text/javascript" src="https://cdn.rawgit.com/pinzon1992/materialize_table_pagination/f9a8478f/js/pagination.js"></script>

    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
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
<%@ include file="../../components/navbar.jsp" %>
<div class="header">
    <h1 class="presentation-text header-title"><spring:message code="Reservations.title"/></h1>
</div>
<div class="filters-orderBy">
    <c:url value="/restaurant=${restaurantId}/reservations" var="postUrl"/>
    <form:form method="post" action="${postUrl}" modelAttribute="filterForm">
        <div class="filters">
            <div class="input-field">
                <form:select id="filterStatus" path="filterStatus">
                    <form:option value="0" label="OPEN"></form:option>
                    <form:option value="1" label="SEATED"></form:option>
                    <form:option value="2" label="CHECK_ORDERED"></form:option>
                    <form:option value="3" label="FINISHED"></form:option>
                    <form:option value="4" label="CANCELED"></form:option>
                    <form:option value="" label="ALL"></form:option>
                </form:select>
            </div>
        </div>
        <div class="orderBy">
            <div class="input-field">
                <form:select id="orderBy" path="orderBy">
                    <form:option value="reservationid">Reserva</form:option>
                    <form:option value="customerid">Nombre</form:option>
                    <form:option value="qpeople">Personas</form:option>
                    <form:option value="reservationhour">Hora</form:option>
                    <form:option value="reservationstatus">Estado</form:option>
                </form:select>
            </div>
        </div>
        <div class="order-orientation">
            <div class="input-field">
                <form:select id="orderDirection" path="direction">
                    <form:option value="ASC">Ascendente</form:option>
                    <form:option value="DESC">Desecendete</form:option>
                </form:select>
            </div>
        </div>
        <div style="display: flex;align-items: center;margin-left: 2%;margin-right: 2%;">
            <button type="submit" class="btn waves-effect waves-light confirm-btn">
                <span class="text description " style="font-size: 0.8rem;color: white">Aplicar</span>
            </button>
        </div>
    </form:form>
</div>
<div class="content-container">
    <table class="reservations" id="myTable">
        <thead>
        <tr>
            <th><h3 class="presentation-text"><spring:message code="Reservations.reservation"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.name"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.people"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.hour"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.status"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.actions"/></h3></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="reservation" items="${reservations}">
            <tr>
                <td data-label="Reserva" class="table-cell"><span class="text"><c:out value="${reservation.reservationId}"/></span></td>
                <td data-label="Nombre" class="table-cell"><span class="text"><c:out value="${reservation.customerName}"/></span></td>
                <td data-label="Personas" class="table-cell"><span class="text"><c:out value="${reservation.qPeople}"/></span></td>
                <td data-label="Hora" class="table-cell"><span class="text"><c:out value="${reservation.reservationHour}"/>:00</span></td>
                <td data-label="Estado" class="table-cell"><span class="text"><c:out value="${reservation.reservationStatus}"/></span></td>
                <c:if test="${reservation.reservationStatus.name == 'OPEN' }">
                    <td data-label="Confirmar" class="table-cell status">
                        <div style="margin-top: 15px">
                            <c:url value="/restaurant=${restaurantId}/seatCustomer=${reservation.reservationId}" var="postUrl"/>
                            <form:form action="${postUrl}" method="post">
                                <button type="submit" class="btn waves-effect waves-light green" style="margin-right: 4%;">
                                    <span class="text description" style="font-size: 0.8rem; color: white;">Aceptar</span>
                                </button>
                            </form:form>
                        </div>
                        <a href="<c:url value="/restaurant=${restaurantId}/cancelReservationConfirmation/id=${reservation.reservationId}"/>" class="btn waves-effect waves-light red">
                            <span class="text description" style="font-size: 0.8rem;color: white;"> Rechazar</span>
                        </a>
                    </td>
                </c:if>

                <c:if test="${reservation.reservationStatus.name == 'CHECK_ORDERED' }">
                    <td data-label="Confirmar" class="table-cell">
                        <div style="margin-top: 15px">
                            <c:url value="/restaurant=${restaurantId}/showReceipt=${reservation.reservationId}" var="postUrl"/>
                            <form:form action="${postUrl}" method="post">
                                <button type="submit" class="btn waves-effect waves-light blue">
                                    <span class="text description" style="font-size: 0.8rem; color: white">Ver Cuenta</span>
                                </button>
                            </form:form>
                        </div>
                    </td>
                </c:if>

                <c:if test="${reservation.reservationStatus.name == 'SEATED' }">
                    <td data-label="Confirmar" class="table-cell">
                        <div style="margin-top: 15px">
                            <c:url value="/restaurant=${restaurantId}/orderCheckCustomer=${reservation.reservationId}" var="postUrl"/>
                            <form:form action="${postUrl}" method="post">
                                <button type="submit" class="btn waves-effect waves-light blue">
                                    <span class="text description " style="font-size: 0.8rem;color: white">Pedir Cuenta</span>
                                </button>
                            </form:form>
                        </div>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="pagination-indicator">
    <c:if test="${page>1}">
        <a onclick="changePage(${page-1})" class="pagination"><i class="material-icons">chevron_left</i></a>
    </c:if>
    <p>${page}</p>
    <button onclick="changePage(${page+1})" class="pagination"></button>
</div>

</body>
</html>


<style>
    .presentation-text.header-title{
        color: #171616;
        font-size: 4rem;
    }
    .filters-orderBy{
        display: flex;
        width: fit-content;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        margin-left: 1%;
    }
    .order-orientation{
        display: flex;
        align-items: center;
        margin-left: 2%;
        margin-right: 2%;
    }
    .filters{
        display: flex;
        margin-left: 2%;
        margin-right: 2%;
    }
    .orderBy{
        display: flex;
        align-items: center;
        margin-left: 2%;
        margin-right: 2%;
    }
    .content-container{
        padding: 0 2%;
        display: flex;
    }
    table{
        width: 100%;
        justify-content: space-evenly;
    }
    .reservations tbody tr td{
        padding: 0.3%;
    }
    .presentation-text{
        color: black;
    }
    .text{
        font-size: 1.25rem;
        color: black;
    }
    .green{
        background-color: green;
    }
    .blue {
        background-color: blue;
    }
    .red{
        background-color: red;
    }
    .pagination-indicator{
        display: flex;
        position: fixed;
        right: 50%;
        bottom: 0;
    }
    .table-cell.status{
        display: flex;
        justify-content: space-evenly;
        align-items: center;
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
        select{
            display: flex;
        }
    }
</style>

<script type="text/javascript">

    $(document).ready(function(){
        $('select').formSelect();
    });

    function changePage(pageN) {
        let toRet = "?" + "filterStatus=" + "<c:out value="${filterForm.filterStatus}"/>" + "&orderBy=" + "<c:out value="${filterForm.orderBy}"/>" + "&direction=" + "<c:out value="${filterForm.direction}"/>" + "&page=" + pageN;
        window.location = toRet;
    }
</script>