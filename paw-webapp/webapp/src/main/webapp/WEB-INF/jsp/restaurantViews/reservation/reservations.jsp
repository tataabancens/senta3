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
    <div class="filters">
        <div class="input-field">
            <select onchange="this.options[this.selectedIndex].value && (window.location = this.options[this.selectedIndex].value);">
                <option value="" disabled selected>Filtrar por status</option>
                <option value="?filterStatus=0">OPEN</option>
                <option value="?filterStatus=1">SEATED</option>
                <option value="?filterStatus=2">CHECK_ORDERED</option>
                <option value="?filterStatus=3">FINISHED</option>
                <option value="?filterStatus=4">CANCELED</option>
            </select>
        </div>
    </div>
    <div class="orderBy">
        <div class="input-field">
            <select onchange="this.options[this.selectedIndex].value && (window.location = this.options[this.selectedIndex].value);">
                <option value="" disabled selected>Ordenar por campo</option>
                <option value="">Reserva</option>
                <option value="">Nombre</option>
                <option value="">Personas</option>
                <option value="">Hora</option>
                <option value="">Estado</option>
            </select>
        </div>
    </div>
</div>
<!--<select onchange="this.options[this.selectedIndex].value && (window.location = this.options[this.selectedIndex].value);">
    <option value="">Filtrar por status</option>
    <option value="?filterStatus=0">OPEN</option>
    <option value="?filterStatus=1">SEATED</option>
    <option value="?filterStatus=2">CHECK_ORDERED</option>
    <option value="?filterStatus=3">FINISHED</option>
    <option value="?filterStatus=4">CANCELED</option>
</select>-->
<div class="content-container">
    <table class="reservations" id="myTable">
        <thead>
        <tr>
            <th><h3 class="presentation-text"><spring:message code="Reservations.reservation"/></h3><a href="?orderBy=reservationid&direction=DESC">↓</a>----------<a href="?orderBy=reservationid&direction=ASC">↑</a></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.name"/></h3><a href="?orderBy=customerid&direction=DESC">↓</a>----------<a href="?orderBy=customerid&direction=ASC">↑</a></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.people"/></h3><a href="?orderBy=qpeople&direction=DESC">↓</a>----------<a href="?orderBy=qpeople&direction=ASC">↑</a></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.hour"/></h3><a href="?orderBy=reservationhour&direction=DESC">↓</a>----------<a href="?orderBy=reservationhour&direction=ASC">↑</a></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.status"/></h3><a href="?orderBy=reservationstatus&direction=DESC">↓</a>----------<a href="?orderBy=reservationstatus&direction=ASC">↑</a></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.actions"/></h3></th>
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
                    <td data-label="Confirmar" class="table-cell status">
                        <c:url value="/restaurant=${restaurantId}/seatCustomer=${reservation.reservationId}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <button type="submit" class="btn waves-effect waves-light green" style="margin-right: 4%;">
                                <span class="text description" style="font-size: 0.8rem; color: white;">Aceptar</span>
                            </button>
                        </form:form>
                        <a href="<c:url value="/restaurant=${restaurantId}/cancelReservationConfirmation/id=${reservation.reservationId}"/>" class="btn waves-effect waves-light red">
                            <span class="text description" style="font-size: 0.8rem;color: white;"> Rechazar</span>
                        </a>
                    </td>
                </c:if>

                <c:if test="${reservation.reservationStatus.name == 'CHECK_ORDERED' }">
                    <td data-label="Confirmar" class="table-cell">
                        <c:url value="/restaurant=${restaurantId}/showReceipt=${reservation.reservationId}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <button type="submit" class="btn waves-effect waves-light blue">
                                <span class="text description" style="font-size: 0.8rem; color: white">Ver Cuenta</span>
                            </button>
                        </form:form>
                    </td>
                </c:if>

                <c:if test="${reservation.reservationStatus.name == 'SEATED' }">
                    <td data-label="Confirmar" class="table-cell">
                        <c:url value="/restaurant=${restaurantId}/orderCheckCustomer=${reservation.reservationId}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <button type="submit" class="btn waves-effect waves-light blue">
                                <span class="text description " style="font-size: 0.8rem;color: white">Ver Cuenta</span>
                            </button>
                        </form:form>
                    </td>
                </c:if>

                <c:if test="${reservation.reservationStatus.name == 'CANCELED' }">
                    <td data-label="Confirmar" class="table-cell">
                        <c:url value="/restaurant=${restaurantId}/removeCustomer=${reservation.reservationId}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <button type="submit" class="btn waves-effect waves-light red">
                                <span class="text description" style="font-size: 0.8rem; color: white;">Eliminar</span>
                            </button>
                        </form:form>
                    </td>
                </c:if>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="pagination-indicator">
    <ul class="pagination pager" id="myPager"></ul>
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
        align-content: center;
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
        $('#myTable').pageMe({
            pagerSelector:'#myPager',
            activeColor: 'blue',
            prevText:'Anterior',
            nextText:'Siguiente',
            showPrevNext:true,
            hidePageNumbers:false,
            perPage:10
        });
    });
    $(document).ready(function(){
        $('select').formSelect();
    });
</script>