<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="es">
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

        <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">


        <title>Senta3</title>
        <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
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
                        <sec:authorize access="hasRole('CUSTOMER')">
                            <li>
                                <a class="options" href="<c:url value="/history"/>">
                                    <spring:message code="Navbar.option.history"/>
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
                        <sec:authorize access="hasRole('CUSTOMER')">
                            <li>
                                <a class="options selected" style="color: white;" href="<c:url value="/"/>" >
                                    <spring:message code="Navbar.option.customer.menu"/>
                                </a>
                            </li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('RESTAURANT')">
                            <li>
                                <a class="options" href="<c:url value="/restaurant=1/menu"/>" >
                                    <spring:message code="Navbar.option.menu"/>
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
        <div class="restaurant-header">
            <div class="restaurant-info" style="background-color: rgb(255, 242, 229);">
                <div>
                    <i class="medium material-icons">restaurant</i>
                </div>
                <div>
                    <div class="presentation-text title restaurant-title">
                        <span class="presentation-text header-title"><c:out value="${restaurant.restaurantName}"/></span>
                    </div>
                    <div class="presentation-text restaurant-description">
                        <span><spring:message code="Fullmenu.phone"/> </span>
                        <span><c:out value="${restaurant.phone}"/></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="page-container">
            <div class="restaurant-content">
                <div class="card left-section">
                    <div class="client-actions center">
                        <span class="presentation-text box-comments"><spring:message code="Menu.reservation.new.title"/></span>
                        <sec:authorize access="hasRole('CUSTOMER')">
                            <div class="reservation-action-btn">
                                <c:url value="/createReservation-0" var="postUrl"/>
                                <form:form action="${postUrl}" method="post">
                                    <spring:message code="Button.reserve" var="label"/>
                                    <input type="submit" value="${label}" class="waves-effect waves-light btn confirm-btn">
                                </form:form>
                            </div>
                        </sec:authorize>
                        <sec:authorize access="!isAuthenticated()">
                            <div class="reservation-action-btn">
                                <c:url value="/createReservation-0" var="postUrl"/>
                                <form:form action="${postUrl}" method="post">
                                    <spring:message code="Button.reserve" var="label"/>
                                    <input type="submit" value="${label}" class="waves-effect waves-light btn confirm-btn">
                                </form:form>
                            </div>
                        </sec:authorize>
                        <sec:authorize access="hasAnyRole('RESTAURANT', 'WAITER', 'KITCHEN')">
                            <div class="reservation-action-btn">
                                <a disabled class="waves-effect waves-light btn confirm-btn" href=""><spring:message code="Menu.reservation.new"/></a>
                            </div>
                        </sec:authorize>
                        <span class="presentation-text box-comments"><spring:message code="Menu.reservation.exists.title"/></span>
                        <sec:authorize access="hasRole('CUSTOMER')">
                            <div class="enter-confirm-btn">
                                <a class="waves-effect waves-light btn confirm-btn" href="findReservation?restaurantId=${restaurant.id}"><spring:message code="Menu.reservation.exists"/></a>
                            </div>
                        </sec:authorize>
                        <sec:authorize access="!isAuthenticated()">
                            <div class="enter-confirm-btn">
                                <a class="waves-effect waves-light btn confirm-btn" href="findReservation?restaurantId=${restaurant.id}"><spring:message code="Menu.reservation.exists"/></a>
                            </div>
                        </sec:authorize>
                        <sec:authorize access="hasAnyRole('RESTAURANT', 'WAITER', 'KITCHEN')">
                            <div class="enter-confirm-btn">
                                <a disabled class="waves-effect waves-light btn confirm-btn" href=""><spring:message code="Menu.reservation.exists"/></a>
                            </div>
                        </sec:authorize>
                    </div>
                    <div class="filter-box">
                        <span class="presentation-text"><spring:message code="FilterBox.title"/></span>
                        <ul class="categories">
                            <c:forEach var="category" items="${categories}">
                                <a href="<c:url value="/?category=${category.id}"/>">
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
                </div>
                <div class="dish-categories">
                    <div class="category-field">
                        <span class="presentation-text header-title" style="color: white;margin-left: 1%;"><c:out value="${currentCategory.name}"/></span>
                    </div>
                    <div class="dishList">
                        <c:forEach var="dish" items="${dishes}">
                            <sec:authorize access="hasRole('CUSTOMER')">
                                <c:url value="/createReservation-1" var="postUrl"/>
                            </sec:authorize>
                            <sec:authorize access="hasRole('RESTAURANT')">
                                <c:url value="/restaurant=1/menu/edit/dishId=${dish.id}" var="postUrl"/>
                            </sec:authorize>
                            <a class="card horizontal" href="${postUrl}">
                                <div class="card-image">
                                    <c:if test="${dish.imageId > 0}">
                                        <img src="<c:url value="/resources_/images/${dish.imageId}"/>" alt="La foto del plato"/>
                                    </c:if>
                                    <c:if test="${dish.imageId == 0}">
                                        <img src="<c:url value="/resources/images/fotoDefault.png"/>" alt="Es una foto default"/>
                                    </c:if>
                                </div>
                                <div class="card-stacked">
                                    <div class="card-content">
                                        <div>
                                            <span class="presentation-text info" style="color: #171616;font-size: 1.1rem;"><c:out value="${dish.dishName}"/></span>
                                            <p class="text description info"><c:out value="${dish.dishDescription}"/></p>
                                            <c:if test="${reservation.reservationDiscount}">
                                                <span id="original-price" class="text price">$<c:out value="${dish.price}"/></span>
                                                <fmt:formatNumber var="dishPrice" type="number" value="${(dish.price * discountCoefficient)}" maxFractionDigits="2"/>
                                                <span id="discounted-price" class="text price">$<c:out value="${dishPrice}"/></span>
                                            </c:if>
                                        </div>
                                        <c:if test="${!reservation.reservationDiscount}">
                                            <span class="text price info" style="font-weight: 700;font-size: 0.8rem;">$<c:out value="${dish.price}"/></span>
                                        </c:if>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

<style>
    .card.horizontal{
        width: 30em;
        height: 8rem;
        margin: 1%;
        box-shadow: 0 1.4rem 8rem rgba(0,0,0,.35);
        transition: 0.8s;
    }
    .card.horizontal:hover{
        transform: scale(1.1);
    }
    .btn.confirm-btn{
        margin-bottom: 0.5em;
    }
    .card.horizontal .card-image{
        object-fit: cover;
        max-width: 20%;
        margin-left: 2%;
    }
    .card.horizontal .card-image img{
        border-radius: .8rem;
        width: clamp(5rem,100%,10rem);
        height: clamp(5rem,100%,10rem);
        aspect-ratio: 1/1;
    }
    .card-stacked{
        height: 100%;
    }
    .page-container{
        padding-left: 20px;
        padding-right: 20px;
    }
    .restaurant-info{
        width: 100%;
        background-color: rgb(16, 24, 28);
    }
    .restaurant-content {
        margin-top: 30px;
        display: flex;
        width: 100%;
        justify-content: flex-start;
        flex-wrap: wrap;
    }
    .card.left-section{
        display: flex;
        flex-direction: column;
        justify-content: center;
        width: 22%;
        align-items: center;
        padding: 10px;
        height: clamp(34em,100%,40em);
    }
    .categories{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
    .btn.text.description{
        color: white;
    }
    .client-actions{
        margin-top: 1em;
    }
    .filter-box{
        display: flex;
        margin-top: 1em;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        width: 100%;
    }
    .card-info p{
        margin-block-start: 0;
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

    .presentation-text.box-comments{
        color: #171616;
    }
    i{
        color: rgb(255, 68, 31);
        margin-right: 25px;
    }
    .reservation-action-btn{
        display: flex;
        flex-direction: row;
        justify-content: center;
    }
    .category-field{
        background-color: rgb(255, 68, 31);
        align-items: center;
        justify-content: space-between;
        height: 3em;
        border-radius: .8rem;
        display: flex;
    }
    .dish-categories{
        margin-left: 2em;
        min-width: 500px;
        width: 73%;
    }
    .dishList{
        display: flex;
        justify-content: flex-start;
        flex-wrap: wrap;
        width: 100%;
    }
    @media (max-width: 600px){
        .restaurant-content{
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            flex-wrap: wrap;
        }
        .card.left-section{
            width: 100%;
        }
        .card.horizontal{
            margin: 2%;
        }
        .card.horizontal:hover{
            transform: scale(1.1);
        }
        .dish-categories{
            width: 100%;
        }
        .card.left-section{
            width: clamp(15em,100%,20em);
        }
    }


</style>
<script>
    const primaryNav = document.querySelector(".primary-navigation");
    const navToggle = document.querySelector(".mobile-nav-toggle");
    const buttons = document.querySelector("a");
    navToggle.addEventListener('click',() => {
        const visibility = primaryNav.getAttribute('data-visible');
        if (visibility=== "false"){
            primaryNav.setAttribute('data-visible',true);
            navToggle.setAttribute('aria-expanded',true);
        }else if(visibility==="true"){
            primaryNav.setAttribute('data-visible',false);
            navToggle.setAttribute('aria-expanded',false);
        }
    })
</script>

