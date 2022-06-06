<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
    <script src="https://cdn.rawgit.com/Dogfalo/materialize/fc44c862/dist/js/materialize.min.js"></script>

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
    <title>Senta3</title>

</head>
<body>
<%@ include file="../../components/navbar.jsp" %>
<div class="restaurant-header" style="background-color: rgb(255, 242, 229);border-radius: 0px;">
    <div class="restaurant-info" style="margin-left: 2%;">
        <h1 class="presentation-text header-title"><spring:message code="Reservations.title"/></h1>
    </div>
</div>

<div class="tabs">
    <a class="category-tab" href="<c:url value="/restaurant=${restaurantId}/reservations/open"/>">
        <span class="secondary-text tab">OPEN</span>
    </a>
    <a class="category-tab" href="<c:url value="/restaurant=${restaurantId}/reservations/seated"/>" >
        <span class="tab secondary-text" >SEATED</span>
    </a>
    <a class="category-tab" href="<c:url value="/restaurant=${restaurantId}/reservations/checkordered"/>">
        <span class="tab secondary-text"  >CHECK ORDERED</span>
    </a>
    <a class="active-category-tab" href="<c:url value="/restaurant=${restaurantId}/reservations/finished"/>">
        <span class="tab secondary-text" >FINISHED</span>
    </a>
    <a class="category-tab" href="<c:url value="/restaurant=${restaurantId}/reservations/canceled"/>">
        <span class="tab secondary-text" >CANCELED</span>
    </a>
    <a class="category-tab" href="<c:url value="/restaurant=${restaurantId}/reservations/all"/>">
        <span class="tab secondary-text" >ALL</span>
    </a>
</div>

<c:url value="/restaurant=${restaurantId}/reservations/finished" var="postUrl"/>
<form:form method="post" action="${postUrl}" modelAttribute="filterForm">
    <div class="filters-orderBy">
        <div class="orderBy">
            <div class="input-field">
                <form:select id="orderBy" path="orderBy">
                    <form:option value="reservationid"><spring:message code="Reservations.reservation"/></form:option>
                    <form:option value="customerid"><spring:message code="Reservations.name"/></form:option>
                    <form:option value="qpeople"><spring:message code="Reservations.people"/></form:option>
                    <form:option value="reservationhour"><spring:message code="Reservations.hour"/></form:option>
                </form:select>
            </div>
        </div>
        <div class="order-orientation">
            <div class="input-field">
                <form:select id="orderDirection" path="direction">
                    <form:option value="ASC"><spring:message code="Reservations.order.asc"/></form:option>
                    <form:option value="DESC"><spring:message code="Reservations.order.dec"/></form:option>
                </form:select>
            </div>
        </div>
        <div style="display: flex;align-items: center;margin-left: 2%;margin-right: 2%;">
            <button type="submit" class="btn waves-effect waves-light confirm-btn">
                <span class="text description " style="font-size: 0.8rem;color: white"><spring:message code="Button.confirm"/></span>
            </button>
        </div>
    </div>
</form:form>

<div>
    <form action="/createReservation-1">
        <spring:message code="Createreservation.title" var="label"/>
        <input class = "btn " style="margin-left: 5%; border-radius: 16px" type="submit" value='${label}'/>
    </form>
</div>

<div class="content-container">
    <table class="reservations" id="myTable">
        <thead>
        <tr>
            <th><h3 class="presentation-text"><spring:message code="Reservations.reservation"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.table"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.name"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.people"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.date"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.hour"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Reservations.status"/></h3></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="reservation" items="${reservations}">
            <tr>
                <td data-label="Reserva" class="table-cell"><span class="text"><c:out value="${reservation.id}"/></span></td>
                <td data-label="Mesa" class="table-cell"><span class="text"><c:out value="${reservation.tableNumber}"/></span></td>
                <td data-label="Nombre" class="table-cell"><span class="text"><c:out value="${reservation.customer.customerName}"/></span></td>
                <td data-label="Personas" class="table-cell"><span class="text"><c:out value="${reservation.qPeople}"/></span></td>
                <td data-label="Fecha" class="table-cell"><span class="text"><c:out value="${reservation.getReservationOnlyDate()}"/></span></td>
                <td data-label="Hora" class="table-cell"><span class="text"><c:out value="${reservation.reservationHour}"/>:00</span></td>
                <td data-label="Estado" class="table-cell"><span class="text"><c:out value="${reservation.reservationStatus}"/></span></td>




            </tr>
        </c:forEach>
        </tbody>


    </table>
</div>
<div class="pagination-indicator">
    <c:if test="${page>1}">
        <button onclick="changePage(${page-1})" class="pagination"><i class="small material-icons">chevron_left</i></button>
    </c:if>
    <div class="page-background">
        <p class=" text description page-number">${page}</p>
    </div>
    <button onclick="changePage(${page+1})" class="pagination"><i class="small material-icons">chevron_right</i></button>
</div>
</body>
</html>


<style>
    .filters-orderBy{
        display: flex;
        flex-wrap: wrap;
        width: fit-content;
        min-width: 50rem;
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
    .text.description.page-number{
        font-size: 1rem;
        color: white;
    }
    .page-background{
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 0.7rem;
        width: 2rem;
        height: 2rem;
        background-color: #37A6E6;
    }
    .pagination{
        background-color: transparent;
        border: none;
        height: fit-content;
        width: fit-content;
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
        justify-content: center;
        align-items: center;
        align-self: end;
        margin-top: 2rem;
    }
    .table-cell.status{
        display: flex;
        justify-content: space-evenly;
        align-items: center;
    }

    .tabs{
        display: flex;
        background-color: transparent;

        justify-content: space-around;
    }
    .category-tab{
        color: black;
        width: 100%;
        text-align: center;
        border: solid 1px grey;
    }
    .category-tab:hover{
        background-color: #ddd;
    }
    .active-category-tab{
        background-color: #ccc;
        color: black;
        width: 100%;
        text-align: center;
        border: solid 1px grey;
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
