<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <title>Senta3</title>
</head>
<body>
<%@ include file="components/navbar.jsp" %>

<div class="row">
    <div class="col s3">
        <div class="card restaurant-card">
            <div class="card-content white-text">
                <span class="card-title title text">Pedidos realizados</span>
            </div>
        </div>
    </div>

    <div class="col offset-s1 s4">
        <span class="res-title">Nuevos pedidos</span>
        <div>
            <c:forEach var="reservation" items="${reservations}">
                <c:forEach var="item" items="${items}">
                    <c:if test="${item.reservationId == reservation.reservationId}">
                        <div class="card dish-card">
                            <div class="card-content white-text">
                                <span class="card-title title text"><c:out value="${item.dishName}"/></span>
                                <p class="description">Cantidad: <c:out value="${item.quantity}"/></p>
                                <p class="description">Reserva: <c:out value="${item.reservationId}"/></p>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </div>

        <span class="res-title">Pedidos en camino</span>
        <div>
            <c:forEach var="reservation" items="${reservations}">
                    <c:forEach var="item" items="${incoming}">
                        <c:if test="${item.reservationId == reservation.reservationId}">
                            <div class="card dish-card">
                                <div class="card-content white-text">
                                    <span class="card-title title text"><c:out value="${item.dishName}"/></span>
                                    <p class="description">Cantidad: <c:out value="${item.quantity}"/></p>
                                    <p class="description">Reserva: <c:out value="${item.reservationId}"/></p>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>

<style>

    body{
        background-color: #F0F0F0;
    }
    .text{
        color:  #707070
    }


    .card{
        border-radius: 16px;
        display: grid;
    }

    .restaurant-card{
    }

    .dish-card{
        width: 100%;
    }

    .description{
        color:  #707070;
        font-size: 17px;
    }

    .res-title{
        font-size: 25px;
        color: #707070;
        margin-left: 5%;
        margin-top: 5%;
    }

    .reservation-btn{
        border-radius: 16px;
        background-color: #37A6E6;
        margin-top: 5%;
        opacity: 57%;
    }

    .reservation-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }

    .center{
        justify-content: center;
    }

    .smaller{
        width: 100%;
        margin-bottom: 0;
        margin-top: 0;
    }

    .already-reserved-btn{
    }

</style>

