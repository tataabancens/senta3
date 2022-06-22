<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">
    <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <title>Senta3</title>

</head>
<body>
<div class="row">
    <button class="mobile-nav-toggle" aria-controls="primary-navigation" aria-expanded="false"></button>
    <nav style="background: white;">
        <ul id="primary-navigation" data-visible="false" class="primary-navigation">
            <div class="left-side">
                <li>
                    <a href="<c:url value="/"/>">
                        <span class="logo" style="font-style: italic;">Senta3</span>
                    </a>
                </li>
                <sec:authorize access="hasRole('RESTAURANT')">
                    <li>
                        <a class="options" href="<c:url value="/restaurant=1/menu"/>" >
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
                            <a class="options selected" style="color: white;" href="<c:url value="/profile"/>" >
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
<div class="restaurant-header" style="background-color: rgb(255, 242, 229);border-radius: 0px;">
    <div class="restaurant-info" style="margin-left: 2%;">
        <h1 class="presentation-text header-title"><spring:message code="Restaurant.profile"/></h1>
    </div>
</div>
<div class="contentContainer">
    <div class="info" style="background-color: #F0F0F0;border-radius: .8rem;padding: 1.5%;">
        <div class="profile-field icon-row" style="display: flex;flex-direction: column; align-items:flex-start; width: fit-content">
            <div style="position: relative;width: 100%;">
                <a class="waves-effect waves-light btn-floating btn-small plus-btn tables-and-hours" href="<c:url value="/restaurant=${restaurantId}/editTables"/>">
                    <i class="material-icons table-and-hours">edit</i>
                </a>
                <span class="presentation-text"><spring:message code="Restaurant.tables.qty"/> </span>
                <span class="text description">${restaurant.totalChairs}</span>
            </div>
            <div>
                <span class="presentation-text"><spring:message code="Restaurant.hour.open"/></span>
                <span class="text description">${restaurant.openHour}:00</span>
            </div>
            <div>
                <span class="presentation-text"><spring:message code="Restaurant.hour.close"/></span>
                <span class="text description">${restaurant.closeHour}:00</span>
            </div>
        </div>
        <div class="profile-field" style="display: flex; align-items:center; width: fit-content">
            <div class="restaurant-field">
                <span class="presentation-text"><spring:message code="Restaurant.name"/> </span>
                <span class="text description">${restaurant.restaurantName}</span>
            </div>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" style="margin-left: 10px;" href="<c:url value="/restaurant=${restaurantId}/editName"/>">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
        <div class="profile-field" style="display: flex; align-items:center; width: fit-content">
            <div class="restaurant-field">
                <span class="presentation-text"><spring:message code="Restaurant.phone"/> </span>
                <span class="text description">${restaurant.phone}</span>
            </div>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" style="margin-left: 10px;" href="<c:url value="/restaurant=${restaurantId}/editPhone"/>">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
        <div class="profile-field" style="display: flex; align-items:center; width: fit-content">
            <div class="restaurant-field">
                <span class="presentation-text"><spring:message code="Restaurant.mail"/> </span>
                <span class="text description">${restaurant.mail}</span>
            </div>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" style="margin-left: 10px;" href="<c:url value="/restaurant=${restaurantId}/editMail"/>">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
        <div class="profile-field" style="display: flex; align-items:center; width: fit-content">
            <div class="restaurant-field">
                <span class="presentation-text"><spring:message code="Register.user"/> </span>
                <span class="text description">${username}</span>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<style>
    .contentContainer{
        width: 100%;
        height: 80%;
        padding: 1.5%;
        display: flex;
    }
    .restaurant-field{
        width: fit-content;
    }
    .presentation-text{
        font-size: 2rem;
    }
    .info{
        width: 50%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
    }
    body{
        background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }
    img{
        border-radius: 16px;
        width: 50%;
        height: 100%;
    }
    .restaurant-field .text.description{
        transition: 0.7s;
    }
    .profile-field{
        transition: 0.7s;
    }
    .profile-field.icon-row{
        display: flex;
        flex-direction: column;
        align-items:flex-start;
        width: fit-content;
        position: relative;
    }
    .profile-field.icon-row .tables-and-hours{
        display: none;
        transition: 0.7s;
    }

    .text.description{
        font-size: 2rem;
    }
    .profile-field:hover{
        background-color: #464242;
        color: white;
        border-radius: 16px;
    }
    .profile-field:hover .text.description{
        color: white;
    }
    .profile-field:hover .info-field{
        display: block;
    }

    .profile-field.icon-row:hover .tables-and-hours{
        display: block;
        position: absolute;
        top: 0;
        right: 0;
        background-color: white;
    }
    .tables-and-hours i{
        color: black;
    }
    .info-field{
        display: none;
    }
    a .info-field{
        background-color: white;
    }
    .info-field i{
        position: absolute;
        top: 0;
        right: 0;
        color: black;
    }
    .restaurant-field:hover .info-field{
        display: block;
    }
</style>
<script></script>