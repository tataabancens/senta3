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

        <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

        <title>Senta3</title>
    </head>
    <body>
        <%@ include file="components/navbar.jsp" %>

        <div class="contentContainer">
            <div class="card restaurant-card">
                        <div class="col">
                            <span class="main-title">Reservas abiertas</span>
                            <c:forEach var="reservation" items="${reservations}">
                                <p class="items-title">Id: <c:out value="${reservation.reservationId}"/> </p>
                                <p class="items-title">Fecha: <c:out value="${reservation.reservationDate}"/> </p>
                                <p class="items-title">Status: <c:out value="${reservation.reservationStatus}"/> </p>
                                <a class="waves-effect waves-light btn red restaurant-btn">Cancelar</a>
                                <p class="items-title">---------</p>

                            </c:forEach>
                        </div>
            </div>

            <div class="dishList">
                <div class="card dish-card">
                    <a href="menu/create" class="add-card-link">
                        <div class="add-card-content">
                            <i class="large material-icons">add</i>
                            <span class="main-title">Crear Plato</span>
                        </div>
                    </a>
                </div>
                <c:forEach var="dish" items="${restaurant.dishes}">
                    <div class="card dish-card">
                        <div class="card-content white-text">
                            <div class="btn-row-card">
                                <a class="waves-effect waves-light btn restaurant-btn blue" href="menu/edit/dishId=${dish.id}">Editar</a>
                                <a class="waves-effect waves-light btn restaurant-btn red">Borrar</a>
                            </div>
                            <span class="main-title"><c:out value="${dish.dishName}"/></span>
                            <p class="title2"><c:out value="${dish.dishDescription}"/></p>
                            <p class="price">$<c:out value="${dish.price}"/></p>
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
    a{
        margin: 5px;
    }
    .contentContainer{
        display: flex;
        flex-wrap: wrap;
        margin-top: 30px;
        justify-content: space-evenly;
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
    .add-card-content {
        display: flex;
        flex-wrap: wrap;
        padding: 8px;
        width: inherit;
        height: inherit;
        justify-content: center;
        color: rgba(183, 179, 179, 0.87);
        align-items: center;
    }
    .dishList{
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;
        flex-wrap: wrap;
        width: 60%;
        margin-left: 5%;
        margin-right: 5%;
    }
    a.add-card-link{
        width: 100%;
        height: 100%;
    }
    .dish-card{
        width: 100%;
        margin: 8px;
        min-width: 150px;
        max-width: 40%;
        max-height: 500px;
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

