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

    <title>Senta3</title>
</head>
<body >
<a id="full-menu"></a>
<%@ include file="components/navbar.jsp" %>

<div class="row">

    <div class="row">
        <div class="col s3">
            <div class="card restaurant-card">
                <div class="card-content white-text">
                    <span class="card-title text"><c:out value="${restaurant.restaurantName}"/></span>
                    <span class="text"><c:out value="${restaurant.phone}"/></span>
                </div>
            </div>
            <div class="card restaurant-card">
                <div class="card-content white-text">
                    <span class="card-title text center">Tu número de reserva es: <c:out value="${reservation.reservationId}"/></span>
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
            </div>

        </div>




        <c:url value="/menu" var="postPath"/>
            <div class="col s4">
            <c:forEach var="dish" items="${dish}">
                <div class="card dish-card">
                    <div class="card-content white-text">
                            <div class="block">
                                <span class="card-title title text "><c:out value="${dish.dishName}"/></span>
                            </div>
                            <div class ="block right">
                                <div class="center">
                                    <a class="waves-effect waves-light btn plus-btn"
                                       href="menu/orderItem?reservationId=${reservation.reservationId}&dishId=${dish.id}">+</a>
                                </div>
                            </div>
                        <p class="description"><c:out value="${dish.dishDescription}"/></p>
                        <p class="price">$<c:out value="${dish.price}"/></p>
                    </div>
                </div>
            </c:forEach>
            </div>

        <div class="col s5">
            <div class="card order-card">
                <div class="card-content white-text">
                    <div class="row">
                        <span class="card-title title text">Resumen de tu pedido:</span>
                    </div>

                    <div class="row">
                        <!-- acá va un for de la tabla orderItem -->
                        <div class="col">
                            <span class="card-title title text">Plato</span>
                        </div>
                        <div class="col center">
                            <span class="card-title title text">Cantidad</span>
                        </div>
                        <div class="col center">
                            <!-- acá va  -->
                            <span class="card-title title text">Precio x U</span>
                        </div>
                        <div class="col center">
                            <span class="card-title title text">Precio total</span>
                        </div>
                    </div>
                    <c:forEach var="orderItem" items="${orderItems}">
                        <div class="row">
                            <div class="col">
                                <span class="card-title title text"><c:out value="${orderItem.dishName}"/></span>
                            </div>
                            <div class="col center">
                                <span class="card-title title text"><c:out value="${orderItem.quantity}"/></span>
                            </div>
                            <div class="col center">
                                <!-- acá va  -->
                                <span class="card-title title text">$<c:out value="${orderItem.unitPrice}"/></span>
                            </div>
                            <div class="col center">
                                <span class="card-title title text"><c:out value="${orderItem.unitPrice * orderItem.quantity}"/></span>
                            </div>
                        </div>
                    </c:forEach>

                    <hr/>

                    <div class="row margin-0">
                        <div class="col s6">
                            <p class="price">Total</p>
                        </div>
                        <div class="col s6">
                            <p class="price right"><c:out value="${total}"/></p>
                        </div>
                    </div>
                    <div class="row margin-0">
                        <div class="col s6">
                            <c:if test="${selected > 0}">
                                <a class="waves-effect waves-light btn plus-btn red" href="order/empty-cart?reservationId=${reservation.reservationId}">Vaciar pedido</a>
                            </c:if>
                            <c:if test="${selected == 0}">
                                <a disabled class="waves-effect waves-light btn plus-btn red" href="order/empty-cart?reservationId=${reservation.reservationId}">Vaciar pedido</a>
                            </c:if>
                        </div>
                        <div class="col s6">
                            <c:if test="${selected > 0}">
                                <a class="waves-effect waves-light btn plus-btn green right" href="order/send-food?reservationId=${reservation.reservationId}&restaurantId=${restaurant.id}">Continuar</a>
                            </c:if>
                            <c:if test="${selected == 0}">
                                <a disabled class="waves-effect waves-light btn plus-btn green right" href="order/send-food?reservationId=${reservation.reservationId}&restaurantId=${restaurant.id}">Continuar</a>
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
    .text{
        color:  #707070
    }

    .margin-0{
        margin: 0;
    }


    .hr {
        display: block;
        height: 1px;
        border: 0;
        border-top: 1px solid #ccc;
        margin: 1em 0;
        padding: 0;
    }


    .plus-btn{
        background-color: #37A6E6;
        opacity: 57%;
        border-radius: 16px;
    }

    .plus-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }


    .restaurant-card{

    }

    .card{
        border-radius: 10px;
    }

    .dish-card{
        width: 100%;
    }

    .description{
        color:  #707070;
        font-size: 17px;
    }

    .price{
        font-size: 25px;
        font-weight: bold;
        color: black;
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

    .smaller{
        width: 100%;
        margin-bottom: 0;
        margin-top: 0;
    }

    .margin-bottom{
        margin-bottom: 10%;
    }

    .no-margin-botton{
        margin-bottom: 0%;
    }

    .order-card{
        width: 100%;

    }



</style>

