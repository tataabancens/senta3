<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    <!-- Auto refresh each x seconds-->
    <meta http-equiv="refresh" content="10">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">

    <title>Senta3</title>
</head>
<body>
<%@ include file="../components/navbar.jsp" %>

<div class="header">
    <h1 class="presentation-text header-title">Pedidos</h1>
</div>
<div class="content-container">
    <div class="card incoming-orders">
        <span class="presentation-text title"><h5>Nuevos pedidos</h5></span>
        <div class="cardContainer">
            <c:forEach var="reservation" items="${reservations}">
                <c:forEach var="item" items="${incomingItems}">
                    <c:if test="${item.reservationId == reservation.reservationId}">
                        <div class="card order-card">
                            <div class="card-content white-text">
                                <span class="card-title title text"><c:out value="${item.dishName}"/></span>
                                <p class="description">Cantidad: <c:out value="${item.quantity}"/></p>
                                <p class="description">Reserva: <c:out value="${item.reservationId}"/></p>
                            </div>
                            <c:url value="/restaurant=${restaurantId}/orders/incomingToFinished-${item.orderItemId}" var="postUrl"/>
                            <form:form action="${postUrl}" method="post">
                                <input type="submit" value="Terminado" class="waves-effect waves-light btn confirm-btn blue center">
                            </form:form>
                        </div>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </div>
    </div>
    <div class="card finished-orders">
        <span class="presentation-text title"><h5>Pedidos listos</h5></span>
        <div class="cardContainer">
            <c:forEach var="reservation" items="${reservations}">
                <c:forEach var="item" items="${finishedItems}">
                    <c:if test="${item.reservationId == reservation.reservationId}">
                        <div class="card order-card">
                            <div class="card-content white-text">
                                <span class="card-title title text"><c:out value="${item.dishName}"/></span>
                                <p class="description">Cantidad: <c:out value="${item.quantity}"/></p>
                                <p class="description">Reserva: <c:out value="${item.reservationId}"/></p>
                            </div>
                            <c:url value="/restaurant=${restaurantId}/orders/finishedToDelivered-${item.orderItemId}" var="postUrl"/>
                            <form:form action="${postUrl}" method="post">
                                <input type="submit" value="Entregado" class="waves-effect waves-light btn blue center confirm-btn">
                            </form:form>
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

    .content-container{
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: space-between;
        padding: 20px;
        flex-wrap: wrap;
    }
    .card{
        border-radius: 16px;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        justify-content: flex-start;
        align-items: center;
    }
    .cardContainer{
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
        width: 100%;
    }
    .card.incoming-orders{
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        justify-content: center;
        padding: 10px;
        width: 48%;
        height: 100%;
        margin: 10px;
    }
    .card.finished-orders{
        display: flex;
        flex-direction: column;
        justify-content: center;
        padding: 10px;
        width: 48%;
        height: 100%;
        margin: 10px;
    }
    .order-card{
        width: 100%;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-content: flex-start;
        margin: 15px;
        max-width: 40%;
        padding: 15px;
    }

    .description{
        color:  #707070;
        font-size: 17px;
    }
    .header-title{
        color: white;
    }

    .center{
        justify-content: center;
    }

    .smaller{
        width: 100%;
        margin-bottom: 0;
        margin-top: 0;
    }



</style>

