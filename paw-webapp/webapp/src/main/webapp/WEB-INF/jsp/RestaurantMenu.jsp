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
       <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

        <title>Senta3</title>
    </head>
    <body>
        <%@ include file="components/navbar.jsp" %>

        <div class="btn-row">
            <a class="waves-effect waves-light btn restaurant-btn green" href="menu/create">Crear Plato</a>
            <a class="waves-effect waves-light btn restaurant-btn blue" href="orders">Ver ordenes</a>
        </div>

        <div class="contentContainer">
            <div class="card restaurant-card">
                        <div class="col">
                            <p class="text title">Mesas abiertas: ${restaurant.totalTables}</p>
                            <a class="waves-effect waves-light btn restaurant-btn" href="editTables">Editar</a>
                            <span class="text title">Reservas abiertas</span>
                            <c:forEach var="reservation" items="${reservations}">
                                <p class="text">Id: <c:out value="${reservation.reservationId}"/> </p>
                                <p class="text">Fecha: <c:out value="${reservation.reservationHour}"/> </p>
                                <p class="text">Status: <c:out value="${reservation.reservationStatus}"/> </p>
                                <a class="waves-effect waves-light btn red restaurant-btn">Cancelar</a>
                                <p class="text">---------</p>

                            </c:forEach>
                        </div>
            </div>

            <div class="dishList">
                <c:forEach var="dish" items="${restaurant.dishes}">
                    <div class="card dish-card">
                        <div class="card-content white-text">
                            <div class="btn-row-card">
                                <a class="waves-effect waves-light btn restaurant-btn blue" href="menu/edit/dishId=${dish.id}">Editar</a>
                                <a class="waves-effect waves-light btn restaurant-btn red">Borrar</a>
                            </div>
                            <span class="text title"><c:out value="${dish.dishName}"/></span>
                            <p class="text"><c:out value="${dish.dishDescription}"/></p>
                            <p class="text price">$<c:out value="${dish.price}"/></p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>

<style>

    body{
        background-color: #F0F0F0;
    }
    .text{
        font-family: "Segoe UI", Lato, sans-serif;
        font-weight: normal;
        font-size: 23px;
        color: #463f3f;
    }
    .title{
        font-size: 28px;
    }
    a{
        margin: 5px;
    }
    .contentContainer{
        display: flex;
        justify-content: space-evenly;
        flex-wrap: wrap;
        padding: 25px;
    }
    .card{
        border-radius: 16px;
        display: flex;
        justify-content: center;
        min-width: 150px;
        max-width: 250px;
        padding: 10px;
        width: 100%;
    }
    .card.restaurant-card{
        max-width: 400px;
    }
    .dishList{
        display: flex;
        flex-direction: column;
        justify-content: center;
        flex-wrap: wrap;
        margin-left: 5%;
        margin-right: 5%;
    }

    .dish-card{
        width: 100%;
        margin: 10px;
        min-width: 150px;
        max-width: 450px;
        max-height: 500px;
    }
    .restaurant-card{
        margin-right: 10px;
        max-height: 500px;
    }
    .price{
        font-weight: bold;
        color: black;
    }
    .btn-row{
        margin-top: 15px;
        margin-left: 5%;
        margin-bottom: 15px;
    }
    .btn-row-card{
        margin-top: 5px;
        justify-content: right;
        margin-bottom: 5px;
    }

    .restaurant-btn{
        border-radius: 16px;
        background-color: #37A6E6;
        opacity: 57%;
    }

    .restaurant-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }



</style>

