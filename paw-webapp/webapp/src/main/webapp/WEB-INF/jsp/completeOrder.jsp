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
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="components/navbar.jsp" %>

<div class="row">
    <div class="col s3">
        <div class="card restaurant-card">
            <div class="card-content white-text">
                <span class="card-title text"><c:out value="${}"/></span>
                <span class="text"><c:out value="${restaurant.phone}"/></span>
            </div>
        </div>
    </div>

    <div class="col offset-s1 s4">
        <div class="card dish-card">
            <div class="card-content white-text">
                <span class="card-title text price center">Estas realizando un pedido en</span>
                <div class="with-margin">
                    <span class="card-title text center"><c:out value="${restaurant.restaurantName}"/></span>
                </div>
                <div class="center">
                    <span class="title2 text center">Resumen de tu pedido:</span>
                </div>
                <div class="summary">
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
                                <span class="items-title text"><c:out value="${orderItem.dishName}"/></span>
                            </div>
                            <div class="col center">
                                <span class="items-title text"><c:out value="${orderItem.quantity}"/></span>
                            </div>
                            <div class="col center">
                                <!-- acá va  -->
                                <span class="items-title text">$<c:out value="${orderItem.unitPrice}"/></span>
                            </div>
                            <div class="col center">
                                <span class="items-title text"><c:out value="${orderItem.unitPrice * orderItem.quantity}"/></span>
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
                        <div class="col s12">
                            <a class="waves-effect waves-light btn reservation-btn green right" href="send-food/confirm?reservationId=${reservationId}&restaurantId=${restaurant.id}">Confirmar pedido</a>
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

    .summary{
        margin-top: 20px;
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

    .items-title{
        color:  #707070;
        font-size: 18px;
        text-overflow: ellipsis;
    }

    .title2{
        justify-content: center;
        color:  #707070;
        font-size: 20px;

    }

    .price{
        font-size: 25px;
        font-weight: bold;
        color: black;
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

    .with-margin{
        margin-top: 10%;
        margin-bottom: 10%;
    }

    .smaller{
        width: 100%;
        margin-bottom: 0;
        margin-top: 0;
    }

    .already-reserved-btn{
    }

</style>