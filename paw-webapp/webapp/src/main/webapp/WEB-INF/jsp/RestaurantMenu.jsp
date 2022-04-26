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

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <title>Senta3</title>
    </head>
    <body>
        <%@ include file="components/navbar.jsp" %>

        <div class="contentContainer">
            <div class="notificationContainer">
                <div class="card">
                    <div>
                        <span class="main-title">Mesas abiertas: <c:out value=" ${restaurant.totalTables}"/></span>
                        <span class="main-title">Horario: <c:out value=" ${restaurant.openHour}"/> a <c:out value=" ${restaurant.closeHour}"/></span>
                        <a class="waves-effect waves-light btn-floating btn-small plus-btn" href="editTables">  <i class="material-icons">edit</i></a>
                    </div>

                </div>
                <div class="card reservations-card">
                    <span class="main-title">Reservas abiertas</span>
                    <c:forEach var="reservation" items="${reservations}">

                            <div class="notification-item">
                                <span class="title2">Hora:<c:out value="${reservation.reservationHour}"/>:00</span>
                                <span class="title2">Id:<c:out value="${reservation.reservationId}"/> </span>
                                <span class="title2">Status:<c:out value="${reservation.reservationStatus}"/> </span>
                                <a class="waves-effect waves-light btn red confirm-btn" href="cancelReservationConfirmation/id=${reservation.reservationId}">Cancelar</a>
                            </div>

                    </c:forEach>
                </div>
            </div>


            <div class="dishList">
                <div class="card dish-card">
                    <a href="menu/create" class="add-card-link">
                        <div class="add-card-content">
                            <i class="large material-icons">add</i>
                            <span class="main-title" style="color: rgba(183, 179, 179, 0.87); ">Crear Plato</span>
                        </div>
                    </a>
                </div>
                <c:forEach var="dish" items="${restaurant.dishes}">

                <div class="card dish-card">
                    <div class="btn-row-card">
                        <a class="waves-effect waves-light btn-floating btn-small plus-btn blue" href="menu/edit/dishId=${dish.id}"><i class="material-icons">edit</i></a>
                        <a class="waves-effect waves-light btn-floating btn-small plus-btn red" href="menu/edit/deleteDish=${dish.id}"><i class="material-icons">delete</i></a>
                    </div>
                    <div class="imageContainer">
                        <img class="dish-image" src="${pageContext.request.contextPath}/resources/images/${dish.imageId}" alt="imagen del plato">
                    </div>
                    <div class="dish-card-text">
                        <span class="main-title dishText"><c:out value="${dish.dishName}"/></span>
                        <span class="title2 dishText"><c:out value="${dish.dishDescription}"/></span>
                        <span class="price">$<c:out value="${dish.price}"/></span>
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

    .card-content{
        height: 30%;
        width: 100%;
    }
    .card-img{
        position: relative;
        top: 0;
        height: 45%;
        width: 100%;
    }
    .dish-image{
        border-radius: 16px 16px 16px 16px;
        height: 100%;
        width: 100%;
    }



    .restaurant-btn{
        border-radius: 16px;
        background-color: #37A6E6;
        opacity: 100%;
    }

    .dishName{
        width: 90%;
        display: block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .reservations-card{
        display: flex;
        flex-direction: column;
    }

</style>

