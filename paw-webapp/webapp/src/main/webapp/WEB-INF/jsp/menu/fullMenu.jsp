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
    <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body >
<a id="full-menu"></a>
<%@ include file="../components/navbar.jsp" %>

<div class="restaurant-header">
    <div class="restaurant-info">
        <div>
            <i class="large material-icons">restaurant</i>
        </div>
        <div>
            <div class="presentation-text title restaurant-title">
                <h3 class="presentation-text header-title"><c:out value="${restaurant.restaurantName}"/></h3>
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
                    <c:if test="${unavailable.contains(dish.id)}">
                        <a disabled href="" class="selection-area">
                            <div class="imageContainer">
                                <c:if test="${imageId > 0}">
                                    <img src="<c:url value="/resources_/images/${dish.imageId}"/>" alt="La foto del plato"/>
                                </c:if>
                                <c:if test="${imageId == 0}">
                                    <img src="<c:url value="/resources/images/fotoDefault.png"/>" alt="Es una foto default"/>
                                </c:if>
                            </div>
                            <div class="dish-card-text">
                                <div class="dish-card-name">
                                    <div class="start">
                                        <span class="main-title dishName"><c:out value="${dish.dishName}"/></span>
                                    </div>
                                </div>
                                <span class="title2 dishName"><c:out value="${dish.dishDescription}"/></span>
                                <span class="price dishName">$<c:out value="${dish.price}"/></span>
                            </div>
                        </a>
                    </c:if>
                    <c:if test="${!unavailable.contains(dish.id)}">

                        <a href="<c:url value="menu/orderItem?reservationId=${reservation.reservationId}&dishId=${dish.id}"/>" class="selection-area">
                            <div class="imageContainer">
                                <c:if test="${dish.imageId > 0}">
                                    <img class="dish-image" src="<c:url value="/resources_/images/${dish.imageId}"/>" alt="La foto del plato"/>
                                </c:if>
                                <c:if test="${dish.imageId == 0}">
                                    <img class="dish-image" src="<c:url value="/resources/images/fotoDefault.png"/>" alt="Es una foto default"/>
                                </c:if>
                            </div>
                            <div class="dish-card-text">
                                <div class="dish-card-name">
                                    <div class="start">
                                        <span class="main-title dishName"><c:out value="${dish.dishName}"/></span>
                                    </div>
                                </div>
                                <span class="title2 dishName"><c:out value="${dish.dishDescription}"/></span>
                                <span class="price dishName">$<c:out value="${dish.price}"/></span>
                            </div>
                        </a>
                    </c:if>
                </div>
            </c:forEach>
        </div>
        <div class="orders-and-info">
            <div class="card client-actions">
            <span class="main-title center">Tu número de reserva es: <c:out value="${reservation.reservationId}"/></span>
                <c:if test="${ordered > 0}">

                    <a class="waves-effect waves-light btn confirm-btn" href="<c:url value="order/send-receipt?reservationId=${reservation.reservationId}&restaurantId=${restaurant.id}"/>">Cuenta</a>
                </c:if>
                <c:if test="${ordered == 0}">
                    <a disabled class="waves-effect waves-light btn confirm-btn" href="">Cuenta</a>
                </c:if>
                <div class="center div-padding">
                    <a class="waves-effect waves-light btn confirm-btn red" href="<c:url value="reservation-cancel?reservationId=${reservation.reservationId}&restaurantId=${restaurant.id}"/>">Cancelar Reserva</a>
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
                    <hr class="solid-divider">
                    <div class="order-info">
                        <c:forEach var="orderItem" items="${orderItems}">
                            <div class="order-item">
                                <div class="order-field center"><span class="items-title "><c:out value="${orderItem.dishName}"/></span></div>
                                <div class="order-field center"><span class="items-title center"><c:out value="${orderItem.quantity}"/></span></div>
                                <div class="order-field center"><span class="items-title center"><c:out value="${orderItem.unitPrice * orderItem.quantity}"/></span></div>
                                <a href=""><i class="order-clear small material-icons">clear</i></a>
                            </div>
                            <hr class="solid-divider">
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
                                    <input type="submit" value="Vaciar pedido" class="waves-effect waves-light btn confirm-btn red">
                                </form:form>
                            </c:if>
                            <c:if test="${selected == 0}">
                                <a disabled class="waves-effect waves-light btn confirm-btn red">Vaciar pedido</a>
                            </c:if>
                        </div>
                        <div>
                            <c:if test="${selected > 0}">
                                <a class="waves-effect waves-light btn confirm-btn green" href="order/send-food?reservationId=${reservation.reservationId}&restaurantId=${restaurant.id}">Continuar</a>
                            </c:if>
                            <c:if test="${selected == 0}">
                                <a disabled class="waves-effect waves-light btn confirm-btn green">Continuar</a>
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

    .page-container{
        padding-top: 20px;
        padding-left: 20px;
        padding-right: 20px;
        display: flex;
        flex-wrap: wrap-reverse;

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
        color: black;
        align-self: center;
        margin-right: 25px;
    }
    .order-clear{
        color: #707070;
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
    .selection-area{
        width: inherit;
        height: inherit;
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
        min-width: 30%;
        width: 100%;
    }
    .btn-floating.btn-large{
        position: absolute;
        bottom: 271px;
        right: -5px;
    }
    a:nth-child(1){
        width: fit-content;
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
    .dish-card:hover{
        transform: scale(1.1);
    }
    .dish-card{
        transition: 0.5s;
    }
    .order-btn-row{
        width: 100%;
        display: flex;
        justify-content: space-around;
    }
    .start{
        justify-self: left;
    }
    .center{
        justify-self: center;
    }


    .div-padding{
        padding: 8px;
    }


    .center{
        justify-content: center;
    }

    .order-card{
        width: 100%;

    }




</style>

