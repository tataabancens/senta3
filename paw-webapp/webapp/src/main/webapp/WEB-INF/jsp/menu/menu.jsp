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
        <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">
        <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

        <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">


        <title>Senta3</title>
        <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    </head>
    <body>
        <div class="row">
            <%@ include file="../components/navbar.jsp" %>
        </div>
        <div class="page-container">
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
            <div class="restaurant-content">
                <div class="card client-actions center">
                        <span class="presentation-text box-comments">Para hacer una reserva:</span>
                        <sec:authorize access="!hasRole('RESTAURANT')">
                            <div class="reservation-action-btn">
                                <a class="waves-effect waves-light btn confirm-btn" href="createReservation-1">Reservar</a>
                            </div>
                        </sec:authorize>
                        <sec:authorize access="hasRole('RESTAURANT')">
                            <div class="reservation-action-btn">
                                <a disabled class="waves-effect waves-light btn confirm-btn" href="">Reservar</a>
                            </div>
                        </sec:authorize>
                        <span class="presentation-text box-comments">Si ya tenes una:</span>
                        <sec:authorize access="!hasRole('RESTAURANT')">
                        <div class="enter-confirm-btn">
                            <a class="waves-effect waves-light btn confirm-btn" href="findReservation?restaurantId=${restaurant.id}">Ingresar</a>
                        </div>
                        </sec:authorize>
                         <sec:authorize access="hasRole('RESTAURANT')">
                            <div class="enter-confirm-btn">
                                <a disabled class="waves-effect waves-light btn confirm-btn" href="">Ingresar</a>
                            </div>
                        </sec:authorize>
                </div>
                <div class="dishList">
                    <c:forEach var="dish" items="${restaurant.dishes}">
                        <div class="card dish-card">
                            <div class="imageContainer">
                                <c:if test="${dish.imageId > 0}">
                                    <img class="dish-image" src="<c:url value="/resources_/images/${dish.imageId}"/>" alt="La foto del plato"/>
                                </c:if>
                                <c:if test="${dish.imageId == 0}">
                                    <img class="dish-image" src="<c:url value="/resources/images/fotoDefault.png"/>" alt="Es una foto default"/>
                                </c:if>
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
        </div>
    </body>
</html>

<style>

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
        background: linear-gradient(70deg, rgb(245, 245, 245) 7%, rgb(23, 22, 22) 88%);
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
        color: #171616;
    }
    i{
        color: #171616;
        margin-right: 25px;
    }
    .presentation-text.restaurant-description{
        color: #171616;
        font-size: 29px;
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
        display: flex;
        border-radius: 16px;
        flex-direction: row;
        flex-wrap: wrap;
        justify-content: flex-start;
        align-items: center;
    }
    .card.client-actions{
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        background-color: white;
        padding: 10px;
        min-height: 150px;
        max-height: 250px;
        min-width: 300px;
        width: 25%;
        margin-right: 50px;
        max-width: 35%;
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
    


</style>

