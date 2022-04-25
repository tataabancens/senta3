<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
        <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    </head>
    <body>
        <div class="row">
            <%@ include file="components/navbar.jsp" %>
        </div>
        <div class="page-container">
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
            <div class="restaurant-content">
                <div class="card client-actions">
                        <span class="presentation-text box-comments">Para hacer una reserva:</span>
                        <sec:authorize access="!hasRole('RESTAURANT')">
                            <div class="reservation-action-btn">
                                <a class="waves-effect waves-light btn reservation-btn" href="register">Reservar</a>
                            </div>
                        </sec:authorize>
                        <sec:authorize access="hasRole('RESTAURANT')">
                            <div class="reservation-action-btn">
                                <a class="waves-effect waves-light btn reservation-btn" href="">Reservar</a>
                            </div>
                        </sec:authorize>
                        <span class="presentation-text box-comments">Si ya tenes una:</span>
                        <sec:authorize access="!hasRole('RESTAURANT')">
                        <div class="enter-reservation-btn">
                            <a class="waves-effect waves-light btn reservation-btn" href="findReservation?restaurantId=${restaurant.id}">Ingresar</a>
                        </div>
                        </sec:authorize>
                         <sec:authorize access="hasRole('RESTAURANT')">
                            <div class="enter-reservation-btn">
                                <a class="waves-effect waves-light btn reservation-btn" href="">Ingresar</a>
                            </div>
                        </sec:authorize>
                </div>
                <div class="dishList">
                    <c:forEach var="dish" items="${restaurant.dishes}">
                        <div class="card dish-card">
                            <span class="main-title"><c:out value="${dish.dishName}"/></span>
                            <span class="title2"><c:out value="${dish.dishDescription}"/></span>
                            <span class="price">$<c:out value="${dish.price}"/></span>
                        </div>
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
    .page-container{
        padding-left: 20px;
        padding-right: 20px;
    }
    .restaurant-content{
        margin-top: 30px;
        display: flex;
        width: 100%;
        justify-content: flex-start;
        flex-wrap: wrap;
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
        align-items: center;
        padding: 15px;
    }
    .restaurant-info{
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: flex-start;
        min-height: 150px;
        max-height: 300px;
    }
    .presentation-text.box-comments{
        color: white;
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
    .text.description{
        color: #463f3f;
        font-size: 21px;
    }

    .reservation-action-btn{
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
    }
    a{
        padding: 5px;
        justify-content: center;
        max-width: 200px;
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
        background-color: #37A6E6;
        padding: 10px;
        min-height: 150px;
        max-height: 250px;
        min-width: 300px;
        width: 25%;
        margin-right: 50px;
        max-width: 35%;
    }

    .dish-card{
        display: flex;
        justify-content:space-evenly;
        align-items: flex-start;
        height: 100%;
        flex-direction: column;
        min-width: 150px;
        max-width: 450px;
        min-height: 250px;
        max-height: 600px;
        padding: 20px;
        width: 100%;
        margin: 40px;
    }

    .description{
        color:  #707070;
        font-size: 17px;
    }
    .dishList{
        display: flex;
        padding-right: 20px;
        justify-self: flex-start;
        justify-content: flex-start;
        padding-left: 100px;
        flex-wrap: wrap;
        min-width: 70%;
        height: 100%;
        max-width: 60%;
    }


    .reservation-btn{
        border-radius: 16px;
        background-color: transparent;
        border-color: white;
        display: flex;
        font-family: 'Goldplay-Bold',sans-serif;
        font-weight: bold;
        font-size: 16px;
        color: white;
        margin-top: 5%;
    }

    .reservation-btn:hover{
        background-color: white;
        color: #37A6E6;
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

