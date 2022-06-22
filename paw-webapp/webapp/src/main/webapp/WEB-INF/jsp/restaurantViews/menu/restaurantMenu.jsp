<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <div class="row">
        <button class="mobile-nav-toggle" aria-controls="primary-navigation" aria-expanded="false"></button>
        <nav>
            <ul id="primary-navigation" data-visible="false" class="primary-navigation">
                <div class="left-side">
                    <li>
                        <a href="<c:url value="/"/>">
                            <span class="logo" style="font-style: italic;">Senta3</span>
                        </a>
                    </li>
                    <sec:authorize access="hasRole('RESTAURANT')">
                        <li>
                            <a class="options selected" style="color: white;" href="<c:url value="/restaurant=1/menu"/>" >
                                <spring:message code="Navbar.option.menu"/>
                            </a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('CUSTOMER')">
                        <li>
                            <a class="options" href="<c:url value="/active-reservations"/>">
                                <spring:message code="Navbar.option.reservations"/>
                            </a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('RESTAURANT')">
                        <li>
                            <a class="options" href="<c:url value="/restaurant=1/orders"/>">
                                <spring:message code="Navbar.option.orders"/>
                            </a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('RESTAURANT')">
                        <li>
                            <a class="options" href="<c:url value="/restaurant=1/reservations/open"/>">
                                <spring:message code="Navbar.option.reservations"/>
                            </a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('RESTAURANT')">
                        <li>
                            <a class="options" href="<c:url value="/restaurant=1/waiter"/>">
                                <spring:message code="Navbar.option.waiter"/>
                            </a>
                        </li>
                    </sec:authorize>
                </div>
                <div class="right-side">
                    <sec:authorize access="!isAuthenticated()">
                        <li>
                            <a class="options" href="<c:url value="/register"/>">
                                <spring:message code="Navbar.option.register"/>
                            </a>
                        </li>
                        <li>
                            <a class="options" href="<c:url value="/login"/>">
                                <spring:message code="Navbar.option.login"/>
                            </a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <sec:authorize access="hasRole('RESTAURANT')">
                            <li>
                                <a class="options" href="<c:url value="/profile"/>" >
                                    <spring:message code="Navbar.option.profile"/><c:out value="${restaurant.getRestaurantName()}"/>
                                </a>
                            </li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('CUSTOMER')">
                            <li>
                                <a class="options" href="<c:url value="/profile"/>" >
                                    <spring:message code="Navbar.option.profile"/>
                                        ${customer.user.getUsername()}
                                </a>
                            </li>
                        </sec:authorize>
                        <li>
                            <a class="options" href="${pageContext.request.contextPath}/logout">
                                <spring:message code="Navbar.option.logout"/>
                            </a>
                        </li>
                    </sec:authorize>
                </div>
            </ul>
        </nav>
    </div>
        <div class="contentContainer">
            <div class="card filter-box">
                <a href="<c:url value="/restaurant=${restaurantId}/category/create"/>" class="dish-card category-creation">
                    <i class="medium material-icons" style="color: rgb(255, 68, 31); ">add</i>
                    <span class="presentation-text" style="color: rgb(255, 68, 31);font-size: 1.5em;">Crear categoria</span>
                </a>
                <hr class="solid-divider" style="background-color: #171616;"/>
                <span class="presentation-text"><spring:message code="FilterBox.title"/></span>
                <ul class="categories">
                    <c:forEach var="category" items="${categories}">
                        <a href="<c:url value="/restaurant=1/menu?category=${category.id}"/>">
                            <c:if test="${currentCategory.name == category.name}">
                                <button class="waves-effect waves-light btn confirm-btn text description">
                                    <c:out value="${category.name}"/>
                                </button>
                            </c:if>
                            <c:if test="${currentCategory.name != category.name}">
                                <button class="waves-effect waves-light btn confirm-btn text description">
                                    <c:out value="${category.name}"/>
                                </button>
                            </c:if>
                        </a>
                    </c:forEach>
                </ul>
            </div>
            <div class="dish-categories">
                <div class="category-field">
                    <span class="presentation-text header-title" style="color: white;margin-left: 1%;"><c:out value="${currentCategory.name}"/></span>
                    <div style="display: flex;">
                        <a href="<c:url value="/restaurant=${restaurantId}/category=${currentCategory}/edit"/>">
                            <i class="small material-icons category-field">edit</i>
                        </a>
                        <a href="<c:url value="/restaurant=${restaurantId}/category/delete?categoryId=${currentCategory.id}"/>" style="justify-self: end;">
                            <i class="small material-icons category-field">delete</i>
                        </a>
                    </div>
                </div>
                <div class="dishList">
                    <a href="menu/create?currentCategory=${currentCategory.id}" class="dish-card dish-creation">
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
                                        <a class="waves-effect waves-light btn-floating btn-small plus-btn blue" href="<c:url value="/restaurant=1/menu/edit/dishId=${dish.id}"/>"><i class="material-icons">edit</i></a>
                                        <a class="waves-effect waves-light btn-floating btn-small plus-btn red" href="<c:url value="/restaurant=1/menu/edit/deleteDish=${dish.id}"/>"><i class="material-icons">delete</i></a>
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
        width: 45%;
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
        min-width: 13rem;
        width: 15%;
        height: fit-content;
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
        transition: 0.3s;
    }
    .dish-card.dish-creation:hover{
        transform: scale(1.05);
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
    .category-field{
        background-color: rgb(255, 68, 31);
        align-items: center;
        justify-content: space-between;
        height: 3em;
        border-radius: .8rem;
        display: flex;
        transition: 0.5s;
    }
    .small.material-icons.category-field{
        color: white;
        margin-left: 0.5%;
        height: 2rem;
        display: none;
    }
    .category-field:hover .small.material-icons.category-field{
        display: block;
    }
    .dish-card.category-creation{
        background-color: white;
        width: 85%;
        align-items: center;
        transition: 1s;
        height: 4em;
        flex-direction: row;
    }
    .dish-card.category-creation:hover {
        transform: scale(1.05);
    }
    @media screen and (max-width: 1350px){
        .dish-card{
            height: 9rem;
        }
        .dish-img{
            width: auto;
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
