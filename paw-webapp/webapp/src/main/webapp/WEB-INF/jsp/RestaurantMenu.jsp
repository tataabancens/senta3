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
    </head>
    <body>
        <%@ include file="components/navbar.jsp" %>

        <div class="btn-row">
            <a class="waves-effect waves-light btn green">Crear Plato</a>
        </div>

        <div class="row">
            <div class="col s3">
                <div class="card restaurant-card">
                    <div class="card-content white-text">
                        <span class="card-title text">Filter box</span>
                        <span class="text">Lorem ipsum</span>
                    </div>
                </div>
            </div>

            <div class="col offset-s1 s4">
                <c:forEach var="dish" items="${restaurant.dishes}">
                    <div class="card dish-card">
                        <div class="card-content white-text">
                            <div class="btn-row-card">
                                <a class="waves-effect waves-light btn blue" href="menu/edit/dishId=${dish.id}">Editar</a>
                                <a class="waves-effect waves-light btn red">Borrar</a>
                            </div>
                            <span class="card-title title text"><c:out value="${dish.dishName}"/></span>
                            <p class="description"><c:out value="${dish.dishDescription}"/></p>
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

