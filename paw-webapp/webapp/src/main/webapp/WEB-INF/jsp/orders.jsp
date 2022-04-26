<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Auto refresh each x seconds-->
    <meta http-equiv="refresh" content="60">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
</head>
<body>
<%@ include file="components/navbar.jsp" %>

<div class="header">
    <span class="presentation-text title header-title"><h3>Pedidos realizados</h3></span>
</div>
<div class="content-container">
    <div class="card incoming-orders">
        <span class="presentation-text title"><h5>Nuevos pedidos</h5></span>
        <div class="cardContainer">
            <c:forEach var="reservation" items="${reservations}">
                <c:forEach var="item" items="${incomingItems}">
                    <c:if test="${item.reservationId == reservation.reservationId}">
                        <div class="card dish-card">
                            <div class="card-content white-text">
                                <span class="card-title title text"><c:out value="${item.dishName}"/></span>
                                <p class="description">Cantidad: <c:out value="${item.quantity}"/></p>
                                <p class="description">Reserva: <c:out value="${item.reservationId}"/></p>
                            </div>
                            <c:url value="${pageContext.request.contextPath}/restaurant=${restaurantId}/orders/incomingToFinished-${item.orderItemId}" var="postUrl"/>
                            <form:form action="${postUrl}" method="post">
                                <input type="submit" value="Terminado" class="waves-effect waves-light btn blue center">
                            </form:form>
                        </div>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </div>
    </div>
    <div class="card finished-orders">
        <span class="presentation-text title"><h5>Pedidos terminados</h5></span>
        <div>
            <c:forEach var="reservation" items="${reservations}">
                <c:forEach var="item" items="${finishedItems}">
                    <c:if test="${item.reservationId == reservation.reservationId}">
                        <div class="card dish-card">
                            <div class="card-content white-text">
                                <span class="card-title title text"><c:out value="${item.dishName}"/></span>
                                <p class="description">Cantidad: <c:out value="${item.quantity}"/></p>
                                <p class="description">Reserva: <c:out value="${item.reservationId}"/></p>
                            </div>
                            <c:url value="${pageContext.request.contextPath}/restaurant=${restaurantId}/orders/finishedToDelivered-${item.orderItemId}" var="postUrl"/>
                            <form:form action="${postUrl}" method="post">
                                <input type="submit" value="Terminado" class="waves-effect waves-light btn blue center">
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

    body{
        background-color: #F0F0F0;
    }
    .header{
        background: rgb(55,166,230);
        background: linear-gradient(70deg, rgba(55,166,230,1) 7%, rgba(240,240,240,1) 88%);
        display: flex;
        flex-direction: row;
        justify-content: center;
        color: white;
        min-height: 150px;
        max-height: 300px;
        font-size: 25px;
        border-radius: 20px;
        align-items: center;
        margin: 20px;
        padding: 15px;
    }
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
    .dish-card{
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

    .reservation-btn{
        border-radius: 8px;
        background-color: #37A6E6;
        color: white;
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



</style>

