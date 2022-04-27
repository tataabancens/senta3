<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "java.io.*,java.util.*" %>


<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body >
<a id="full-menu"></a>
<%@ include file="components/navbar.jsp" %>

<div class="restaurant-header">
    <div class="restaurant-info">
        <div>
            <i class="large material-icons">restaurant</i>
        </div>
        <div>
            <div class="presentation-text title restaurant-title">
                <span><c:out value="${restaurant.restaurantName}"/></span>
            </div>
            <div class="presentation-text restaurant-description">
                <span>Telefono: </span>
                <span><c:out value="${restaurant.phone}"/></span>
            </div>
        </div>
    </div>
</div>
<div class="page-container">
    <div class="restaurant-content">
        <div class="dishList">
            <c:forEach var="dish" items="${restaurant.dishes}">
                <div class="card dish-card">
                    <div class="imageContainer">
                        <img class="dish-image" src="${pageContext.request.contextPath}/resources/images/${dish.imageId}" alt="imagen del plato">
                        <a class="btn-floating btn-large waves-effect waves-light plus-btn"
                           href="menu/orderItem?reservationId=${reservation.reservationId}&dishId=${dish.id}"><i class="material-icons">add</i></a>
                    </div>
                    <div class="dish-info">
                        <div class="dish-card-name">
                            <div class="start">
                                <span class="main-title dishName"><c:out value="${dish.dishName}"/></span>
                            </div>
                        </div>
                        <span class="title2 dishName"><c:out value="${dish.dishDescription}"/></span>
                        <span class="price dishName">$<c:out value="${dish.price}"/></span>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="orders-and-info">
            <div class="card client-actions">
                <span class="main-title center">Tu número de reserva es: <c:out value="${reservation.reservationId}"/></span>
                <div class="center">
                    <c:if test="${ordered > 0}">
                        <a class="waves-effect waves-light btn plus-btn" href="order/send-receipt?reservationId=${reservation.reservationId}&restaurantId=${restaurant.id}">Pedir cuenta</a>
                    </c:if>
                    <c:if test="${ordered == 0}">
                        <a disabled class="waves-effect waves-light btn plus-btn" href="order/send-receipt?reservationId=${reservation.reservationId}&restaurantId=${restaurant.id}">Pedir cuenta</a>
                    </c:if>
                </div>
                <div class="center div-padding">
                    <a class="waves-effect waves-light btn plus-btn red" href="reservation-cancel?reservationId=${reservation.reservationId}&restaurantId=${restaurant.id}">Cancelar Reserva</a>
                </div>
            </div>
            <div class="orderList">
                <div class="card order-card">
                    <span class="main-title">Resumen de tu pedido:</span>
                    <div class="order-headers">
                        <span class="title2 dishname">Plato</span>
                        <span class="title2">Cantidad</span>
                        <span class="title2">Subtotal</span>
                    </div>
                    <div class="order-info">
                        <c:forEach var="orderItem" items="${orderItems}">
                            <div class="order-item">
                                <div class="order-field center"><span class="items-title center dishname"><c:out value="${orderItem.dishName}"/></span></div>
                                <div class="order-field center"><span class="items-title center"><c:out value="${orderItem.quantity}"/></span></div>
                                <div class="order-field center"><span class="items-title center"><c:out value="${orderItem.unitPrice * orderItem.quantity}"/></span></div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="order-total">
                        <div>
                            <p class="price">Total</p>
                        </div>
                        <div>
                            <p class="price right"><c:out value="${total}"/></p>
                        </div>
                    </div>
                    <div class="order-btn-row">
                        <div>
                            <c:if test="${selected > 0}">
                                <c:url value="/order/empty-cart?reservationId=${reservation.reservationId}" var="postUrl"/>
                                <form:form action="${postUrl}" method="post">
                                    <input type="submit" value="Vaciar pedido" class="waves-effect waves-light btn plus-btn red">
                                </form:form>
                            </c:if>
                            <c:if test="${selected == 0}">
                                <a disabled class="waves-effect waves-light btn plus-btn red">Vaciar pedido</a>
                            </c:if>
                        </div>
                        <div>
                            <c:if test="${selected > 0}">
                                <a class="waves-effect waves-light btn plus-btn green right" href="order/send-food?reservationId=${reservation.reservationId}&restaurantId=${restaurant.id}">Continuar</a>
                            </c:if>
                            <c:if test="${selected == 0}">
                                <a disabled class="waves-effect waves-light btn plus-btn green right">Continuar</a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>

<style>

    body{
        background-color: #F0F0F0;
    }
    .page-container{
        padding-top: 20px;
        padding-left: 20px;
        padding-right: 20px;
        display: flex;
        flex-wrap: wrap-reverse;

    }
    .restaurant-header{
        background: rgb(55,166,230);
        background: linear-gradient(70deg, rgba(55,166,230,1) 7%, rgba(240,240,240,1) 88%);
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        min-height: 150px;
        max-height: 300px;
        border-radius: 20px;
        margin: 20px;
        align-items: center;
        padding: 15px;
    }
    .restaurant-info{
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: flex-start;
        width: 50%;
        height: 100%;
    }
    .restaurant-content{
        margin-top: 30px;
        display: flex;
        width: 100%;
        justify-content: flex-start;
        flex-wrap: wrap;
    }
    .orders-and-info{
        width: 45%;
        justify-items: center;
        height: 100%;
    }
    i{
        color: white;
        margin-right: 25px;
    }
    .presentation-text.restaurant-title{
        font-size: 30px;
        color:white;
    }
    .presentation-text.restaurant-description{
        color: white;
        font-size: 21px;
    }
    .dishList{
        display: flex;
        padding-right: 20px;
        justify-self: flex-start;
        justify-content: flex-start;
        padding-left: 20px;
        flex-wrap: wrap;
        min-width: 30%;
        width: 55%;
        height: 100%;
    }
    .card{
        border-radius: 16px;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        justify-content: flex-start;
        align-items: center;
    }
    .card.client-actions{
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        padding: 10px;
        min-height: 150px;
        max-height: 250px;
        min-width: 300px;
        width: 100%;
    }
    .btn-floating.btn-large{
        position: absolute;
        bottom: 271px;
        right: -5px;
    }
    .dish-card{
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        width: 40%;
        min-height: 300px;
        height: 20%;
        margin: 20px;
    }
    .dish-image{
        border-radius: 16px 16px 16px 16px;
        height: 100%;
        width: 100%;
    }
    .imageContainer{
        width: 100%;
        height: 45%;
    }
    .dish-info{
        display: flex;
        flex-wrap: wrap;
        width: 100%;
        height: 30%;
        padding: 8px;
    }
    .dish-add{
        display: flex;
        width: 100%;
        justify-content: flex-end;
    }
    .dish-card-name{
        display: flex;
        width: 100%;
        justify-content: space-between;
    }
    .orderList{
        width: 100%;
        height: 100%;
    }
    .order-card{
        display: flex;
        justify-content: flex-start;
        align-items: flex-start;
        max-height: 100%;
        flex-direction: column;
        min-width: 150px;
        min-height: 250px;
        padding: 20px;
        width: 100%;
        margin: 8px;
    }
    .order-headers{
        display: flex;
        width: 100%;
        margin-top: 10px;
        margin-bottom: 10px;
        justify-content: space-evenly;
    }
    .order-info{
        display: flex;
        flex-direction: column;
        width: 100%;
        justify-content: space-evenly;
    }
    .order-item{
        display: flex;
        width: 100%;
        justify-content: space-evenly;
    }
    .order-field{
        width: 100%;
        display: flex;
        justify-content: space-between;
    }
    .order-total{
        width: 100%;
        display: flex;
        justify-content: space-around;
    }
    .order-btn-row{
        width: 100%;
        display: flex;
        justify-content: space-around;
    }
    .start{
        justify-self: left;
    }
    .end{
        justify-self: right;
    }
    .center{
        justify-self: center;
    }


    .plus-btn{
        background-color: #37A6E6;
        opacity: 90%;
    }

    .plus-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }

    .description{
        color:  #707070;
        font-size: 17px;
    }


    .block-parent{
        justify-content: space-between;
    }

    .block{
        display: inline-block;
    }

    .reservation-btn:enabled{
        border-radius: 16px;
        background-color: #37A6E6;
        margin-top: 5%;
        opacity: 57%;
    }

    .reservation-btn:enabled{
        border-radius: 16px;
        background-color:  #707070;
        margin-top: 5%;
        opacity: 57%;
    }

    .div-padding{
        padding: 8px;
    }

    .reservation-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }

    .center{
        justify-content: center;
    }

    .order-card{
        width: 100%;

    }
    .title2{
        font-size: 1.25vw;
    }



</style>

