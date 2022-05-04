<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
    <head>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Materialize CSS -->
        <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">
       <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">

        <title>Senta3</title>
    </head>
    <body>
        <%@ include file="../components/navbar.jsp" %>

        <div class="header">
            <h1 class="presentation-text header-title">Menú</h1>
        </div>
        <div class="contentContainer">

            <div class="dishList">
                <a href="menu/create" class="dish-card dish-creation">
                    <i class="large material-icons" style="color: rgba(183, 179, 179, 0.87); ">add</i>
                    <span class="main-title" style="color: rgba(183, 179, 179, 0.87); ">Crear Plato</span>
                </a>
                <c:forEach var="dish" items="${restaurant.dishes}">

                <div class="dish-card">
                    <div class="btn-row-card">
                        <a class="waves-effect waves-light btn-floating btn-small plus-btn blue" href="menu/edit/dishId=${dish.id}"><i class="material-icons">edit</i></a>
                        <a class="waves-effect waves-light btn-floating btn-small plus-btn red" href="menu/edit/deleteDish=${dish.id}"><i class="material-icons">delete</i></a>
                    </div>
                    <div class="card-content">
                        <div class="dish-img">
                            <c:if test="${dish.imageId > 0}">
                                <img src="<c:url value="/resources_/images/${dish.imageId}"/>" alt="La foto del plato"/>
                            </c:if>
                            <c:if test="${dish.imageId == 0}">
                                <img src="<c:url value="/resources/images/fotoDefault.png"/>" alt="Es una foto default"/>
                            </c:if>
                        </div>
                        <div class="card-info">
                            <span class="presentation-text"><c:out value="${dish.dishName}"/></span>
                            <p class="text description"><c:out value="${dish.dishDescription}"/></p>
                            <span class="text price">$<c:out value="${dish.price}"/></span>
                        </div>
                    </div>
                </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>

<style>

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
    .dish-card{
        flex-direction: column;
        width: 35%;
        transition: 0.7s;
    }
    .card-content{
        display: flex;
    }
    .presentation-text{
        font-family:'Nunito',sans-serif;
        font-weight: 700;
        font-size: 1.3rem;
    }
    .text.description{
        font-family: 'Quicksand',sans-serif;
        font-weight: 600;
        font-size: 1rem;
    }
    .text.price{
        font-weight: 600;
        font-size: 1.4rem;
    }
    .dish-card.dish-creation{
        display: flex;
        flex-wrap: wrap;
        padding: 8px;
        justify-content: center;
        align-items: center;
    }
    .dishList{
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;
        flex-wrap: wrap;
        width: 90%;
        margin-left: 5%;
        margin-right: 5%;
    }
    .btn-row-card{
        display: none;
        justify-content: flex-end;
        padding-right: 1.5%;
    }
    .dish-card:hover .btn-row-card{
        display: flex;
    }

</style>

