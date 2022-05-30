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
        <%@ include file="../../components/navbar.jsp" %>
        <div class="restaurant-header" style="background-color: rgb(255, 242, 229);border-radius: 0px;">
            <div class="restaurant-info" style="margin-left: 2%;">
                <h1 class="presentation-text header-title"><spring:message code="Restaurant.menu.title"/></h1>
            </div>
        </div>
        <div class="contentContainer">
            <div class="card filter-box">
                <span class="presentation-text"><spring:message code="FilterBox.title"/></span>
                <ul class="categories">
                    <c:forEach var="category" items="${categories}">
                        <a href="<c:url value="/restaurant=1/menu?category=${category}"/>">
                            <c:if test="${currentCategory.description == category.description}">
                                <button class="waves-effect waves-light btn confirm-btn text description">
                                    <c:out value="${category.spanishDescr}"/>
                                </button>
                            </c:if>
                            <c:if test="${currentCategory.description != category.description}">
                                <button class="waves-effect waves-light btn confirm-btn text description">
                                    <c:out value="${category.spanishDescr}"/>
                                </button>
                            </c:if>
                        </a>
                    </c:forEach>
                </ul>
            </div>
            <div class="dish-categories">
                <div>
                    <h3 class="presentation-text header-title"><c:out value="${currentCategory.spanishDescr}"/></h3>
                </div>
                <div class="dishList">
                    <a href="menu/create" class="dish-card dish-creation">
                        <i class="large material-icons" style="color: rgba(183, 179, 179, 0.87); ">add</i>
                        <span class="main-title" style="color: rgba(183, 179, 179, 0.87); "><spring:message code="Restaurant.createdish"/></span>
                    </a>
                    <c:forEach var="dish" items="${dishes}">

                        <div class="dish-card">
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
                                    <div class="btn-row-card">
                                        <a class="waves-effect waves-light btn-floating btn-small plus-btn blue" href="menu/edit/dishId=${dish.id}"><i class="material-icons">edit</i></a>
                                        <a class="waves-effect waves-light btn-floating btn-small plus-btn red" href="menu/edit/deleteDish=${dish.id}"><i class="material-icons">delete</i></a>
                                    </div>
                                    <span class="presentation-text dish-title"><c:out value="${dish.dishName}"/></span>
                                    <p class="text description"><c:out value="${dish.dishDescription}"/></p>
                                    <span class="text price">$<c:out value="${dish.price}"/></span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
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
        justify-content: space-between;
        padding: 1em;
    }
    .dish-card{
        flex-direction: column;
        width: 100%;
        height: 9.7rem;
        transition: 0.7s;
    }
    .dish-img{
        width: auto;
    }
    .card-content{
        display: flex;
        width: 100%;
        height: inherit;
    }
    p {
        margin-block-start: 0.3em;
    }
    p .text.description{
        font-size: clamp(0.5rem,0.8vw,3rem);
    }
    .card-info{
        flex-grow: 1;
    }
    .btn.confirm-btn{
        color: white;
    }
    .categories{
        display: flex;
        flex-direction: column;
        align-items: center;
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
    .card.filter-box{
        min-width: 10rem;
        width: 15%;
        max-height: 20em;
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
        justify-content: flex-start;
        flex-wrap: wrap;
        width: 100%;
    }
    .dish-categories{
        width: 80%;
        margin-left: 1%;
    }
    .btn-row-card{
        display: flex;
        position: absolute;
        right: 0;
        top: 0;
        justify-content: flex-end;
        padding-right: 1.5%;
    }
    @media screen and (max-width: 1920px){
        .presentation-text.info{
            font-size: 1rem;
        }
        .text.description.info{
            font-size: 0.8rem;
        }
        .text.price.info{
            font-size: 0.8rem;
        }
    }
    @media screen and (max-width: 1350px){
        .dish-card{
            height: 9rem;
        }
        .dish-img{
            width: auto;
        }
        .presentation-text.info{
            font-size: 1rem;
        }
        .text.description.info{
            font-size: 0.8rem;
        }
        .text.price.info{
            font-size: 0.8rem;
        }
    }
    @media screen and (max-width: 1080px){
        .dish-card{
            width: 100%;
            height: 9rem;
        }
        p{
            margin-block-start: 0.1em;
        }
        .dish-img{
            width: auto;
        }
        .presentation-text.info{
            font-size: 1rem;
        }
        .text.description.info{
            font-size: 0.8rem;
        }
        .text.price.info{
            font-size: 0.8rem;
        }
    }
    @media screen and (max-width: 868px){
        .dish-card{
            padding: 1rem;
            flex-direction: column;
            max-height: none;
        }
        .text.price{
            bottom: 0;
        }
        .dish-img{
            min-width: auto;
        }
        .card-content{
            height: 100%;
        }
    }

    @media screen and (max-width: 768px){
        .dish-card{
            padding: 0.5rem;
            flex-direction: column;
            height: auto;
        }
        .dish-img{
            min-width: auto;
        }
    }
    @media (max-width: 600px){
        .contentContainer{
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }
        .dish-categories{
            width: 100%;
        }
        .dish-card{
            height: 10em;
            flex-direction: column;
        }
        .dish-img{
            width: 7em;
            height: 7em;
        }
        .dish-card.card-content{
            display: flex;
            flex-direction: column;
        }
    }
</style>
