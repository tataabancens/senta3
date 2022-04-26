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
            <div class="notificationContainer">
                <div class="card restaurant-card">
                    <div>
                        <p class="text title">Mesas abiertas: ${restaurant.totalTables}</p>
                    </div>
                    <div>
                        <p class="text title">Horario: ${restaurant.openHour} a ${restaurant.closeHour}</p>
                    </div>
                    <div>
                        <a class="waves-effect waves-light btn restaurant-btn" href="editTables">Editar</a>
                    </div>
                </div>
                <div class="card restaurant-card">
                    <span class="main-title">Reservas abiertas</span>
                    <c:forEach var="reservation" items="${reservations}">
                        <div class="card notification-card">
                            <div class="notification-item"><span class="title2">Hora:<c:out value="${reservation.reservationHour}"/>:00</span></div>
                            <div class="notification-item"><span class="title2">Id:<c:out value="${reservation.reservationId}"/> </span></div>
                            <div class="notification-item"><span class="title2">Status:<c:out value="${reservation.reservationStatus}"/> </span></div>
                            <div class="notification-item"><a class="waves-effect waves-light btn red restaurant-btn">Cancelar</a></div>
                        </div>
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
                        <div class="card-content">
                            <div class="btn-row-card">
                                <a class="waves-effect waves-light btn restaurant-btn blue" href="menu/edit/dishId=${dish.id}">Editar</a>
                                <a class="waves-effect waves-light btn restaurant-btn red" href="menu/edit/deleteDish=${dish.id}">Borrar</a>
                            </div>
                            <span class="main-title dishName"><c:out value="${dish.dishName}"/></span>
                            <p class="title2 dishName"><c:out value="${dish.dishDescription}"/></p>
                            <p class="price dishName">$<c:out value="${dish.price}"/></p>
                        </div>
                        <div class="card-img">
                            <img class="dish-image" src="${pageContext.request.contextPath}/resources/images/${dish.imageId}" alt="imagen del plato">
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
    .notificationContainer{
        display: flex;
        flex-direction: column;
        width: 20%;
    }
    .card{
        border-radius: 16px;
        display: flex;
        justify-content: center;
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
        width: 70%;
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
        height: 20%;
        max-height: 500px;
    }
    .card-content{
        height: 100%;
        width: 50%;
    }
    .card-img{
        min-height: 50%;
        min-width: 25%;
        height: 100%;
        width: 50%;
    }
    .dish-image{
        border-radius: 16px 16px 16px 16px;
        height: 100%;
        width: 100%;
    }
    .main-title{
        font-size: 1.5vw;
    }
    .title2{
        font-size: 1vw;
    }
    .price{
        font-size: 1vw;
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
    .dish-card:hover{
        height: 20%;
    }
    .restaurant-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }

    .dishName{
        width: 200px;
        display: block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

</style>

