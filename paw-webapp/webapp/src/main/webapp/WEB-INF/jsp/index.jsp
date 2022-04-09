<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

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
    <body>
        <%@ include file="components/navbar.jsp" %>

        <div class="row">
            <div class="col s3">
                <div class="card restaurant-card">
                    <div class="card-content white-text">
                        <span class="card-title text"><c:out value="${restaurant.restaurantName}"/></span>
                        <span class="text"><c:out value="${restaurant.phone}"/></span>
                        <div class="row center smaller">
                            <a class="waves-effect waves-light btn reservation-btn already-reserved-btn green" href="register">Reservar</a>
                        </div>
                        <div class="row center smaller">
                            <a class="waves-effect waves-light btn reservation-btn already-reserved-btn">Ya tengo reserva</a>
                        </div>
                        <div class="row center smaller">
                            <a class="waves-effect waves-light btn reservation-btn already-reserved-btn red">cancelar reserva</a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col offset-s1 s4">
                <c:forEach var="dish" items="${restaurant.dishes}">
                    <div class="card dish-card">
                        <div class="card-content white-text">
                            <span class="card-title title text"><c:out value="${dish.dishName}"/></span>
                            <p class="description">I am a very simple card. I am good at containing small bits of information.
                                I am convenient because I require little markup to use effectively.</p>
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
    .text{
        color:  #707070
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

    .description{
        color:  #707070;
        font-size: 17px;
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

    .smaller{
        width: 100%;
        margin-bottom: 0;
        margin-top: 0;
    }

    .already-reserved-btn{
    }

</style>

