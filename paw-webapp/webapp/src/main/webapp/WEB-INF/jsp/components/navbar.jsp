<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <!-- <link rel="stylesheet" href="home"> -->

    <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">

    <title>Hello, world!</title>

</head>
<body>
<button class="mobile-nav-toggle" aria-controls="primary-navigation" aria-expanded="false"></button>
<nav>
    <ul id="primary-navigation" data-visible="false" class="primary-navigation flex">
        <div class="left-side">
            <li>
                <a href="<c:url value="/"/>">
                    <span class="logo" style="font-style: italic;">Senta3</span>
                </a>
            </li>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] == '/'}">
                <sec:authorize access="hasRole('CUSTOMER')">
                    <li>
                        <a class="options selected" style="color: white;" href="<c:url value="/"/>" >
                            <spring:message code="Navbar.option.customer.menu"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] != '/'}">
                <sec:authorize access="hasRole('CUSTOMER')">
                    <li>
                        <a class="options" href="<c:url value="/"/>" >
                            <spring:message code="Navbar.option.customer.menu"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] == '/history'}">
                <sec:authorize access="hasRole('CUSTOMER')">
                    <li>
                        <a class="options selected" style="color: white;" href="<c:url value="/history"/>">
                            <spring:message code="Navbar.option.history"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] != '/history'}">
                <sec:authorize access="hasRole('CUSTOMER')">
                    <li>
                        <a class="options" href="<c:url value="/history"/>">
                            <spring:message code="Navbar.option.history"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] == '/active-reservations'}">
                <sec:authorize access="hasRole('CUSTOMER')">
                    <li>
                        <a class="options selected" style="color: white;" href="<c:url value="/active-reservations"/>">
                            <spring:message code="Navbar.option.reservations"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] != '/active-reservations'}">
                <sec:authorize access="hasRole('CUSTOMER')">
                    <li>
                        <a class="options" href="<c:url value="/active-reservations"/>">
                            <spring:message code="Navbar.option.reservations"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] != '/restaurant=1/menu'}">
                <sec:authorize access="hasRole('RESTAURANT')">
                    <li>
                        <a class="options" href="<c:url value="/restaurant=1/menu"/>" >
                            <spring:message code="Navbar.option.menu"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] == '/restaurant=1/menu'}">
                <sec:authorize access="hasRole('RESTAURANT')">
                    <li>
                        <a class="options selected" style="color: white;" href="<c:url value="/restaurant=1/menu"/>" >
                            <spring:message code="Navbar.option.menu"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] == '/restaurant=1/orders'}">
                <sec:authorize access="hasRole('RESTAURANT')">
                    <li>
                        <a class="options selected" style="color: white;" href="<c:url value="/restaurant=1/orders"/>">
                            <spring:message code="Navbar.option.orders"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] != '/restaurant=1/orders'}">
                <sec:authorize access="hasRole('RESTAURANT')">
                    <li>
                        <a class="options" href="<c:url value="/restaurant=1/orders"/>">
                            <spring:message code="Navbar.option.orders"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] != '/restaurant=1/reservations/open'}">
                <sec:authorize access="hasRole('RESTAURANT')">
                    <li>
                        <a class="options" href="<c:url value="/restaurant=1/reservations/open"/>">
                            <spring:message code="Navbar.option.reservations"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] == '/restaurant=1/reservations/open'}">
                <sec:authorize access="hasRole('RESTAURANT')">
                    <li>
                        <a class="options selected" style="color: white;" href="<c:url value="/restaurant=1/reservations/open"/>">
                            <spring:message code="Navbar.option.reservations"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] == '/restaurant=1/waiter'}">
                <sec:authorize access="hasRole('RESTAURANT')">
                    <li>
                        <a class="options selected" style="color: white;" href="<c:url value="/restaurant=1/waiter"/>">
                            <spring:message code="Navbar.option.waiter"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
            <c:if test="${requestScope['javax.servlet.forward.request_uri'] != '/restaurant=1/waiter'}">
                <sec:authorize access="hasRole('RESTAURANT')">
                    <li>
                        <a class="options" href="<c:url value="/restaurant=1/waiter"/>">
                            <spring:message code="Navbar.option.waiter"/>
                        </a>
                    </li>
                </sec:authorize>
            </c:if>
        </div>
        <div class="right-side">
                <c:if test="${requestScope['javax.servlet.forward.request_uri'] != '/register'}">
                    <sec:authorize access="!isAuthenticated()">
                        <li>
                            <a class="options" href="<c:url value="/register"/>">
                                <spring:message code="Navbar.option.register"/>
                            </a>
                        </li>
                    </sec:authorize>
                </c:if>
                <c:if test="${requestScope['javax.servlet.forward.request_uri'] != '/login'}">
                    <sec:authorize access="!isAuthenticated()">
                        <li>
                            <a class="options" href="<c:url value="/login"/>">
                                <spring:message code="Navbar.option.login"/>
                            </a>
                        </li>
                    </sec:authorize>
                </c:if>
            <sec:authorize access="isAuthenticated()">
                <sec:authorize access="hasRole('RESTAURANT')">
                    <c:if test="${requestScope['javax.servlet.forward.request_uri'] == '/restaurant=1/profile'}">
                        <li>
                            <a class="options selected" style="color: white;" href="<c:url value="/profile"/>" >
                                <spring:message code="Navbar.option.profile"/><c:out value="${restaurant.getRestaurantName()}"/>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${requestScope['javax.servlet.forward.request_uri'] != '/restaurant=1/profile'}">
                        <li>
                            <a class="options" href="<c:url value="/profile"/>" >
                                <spring:message code="Navbar.option.profile"/><c:out value="${restaurant.getRestaurantName()}"/>
                            </a>
                        </li>
                    </c:if>
                </sec:authorize>
                <sec:authorize access="hasRole('CUSTOMER')">
                    <c:if test="${requestScope['javax.servlet.forward.request_uri'] == '/customerProfile'}">
                        <li>
                            <a class="options selected" style="color: white;" href="<c:url value="/profile"/>" >
                                <spring:message code="Navbar.option.profile"/><c:out value="${customer.user.getUsername()}"/>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${requestScope['javax.servlet.forward.request_uri'] != '/customerProfile'}">
                        <li>
                            <a class="options" href="<c:url value="/profile"/>" >
                                <spring:message code="Navbar.option.profile"/>
                                    ${customer.user.getUsername()}
                            </a>
                        </li>
                    </c:if>
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
</body>
</html>

<style>
    body{
        overflow-x: hidden;
    }
    nav{
        background-color: white;
    }
    .flex{
        display: flex;
        gap: var(--gap,1rem);
    }
    .primary-navigation{
        list-style: none;
        padding: 0;
        margin: 0;
        justify-content: space-between;
    }
    .mobile-nav-toggle{
        display: none;
    }
    .logo{
        font-family: "Goldplay", sans-serif;
        font-size: 1.9rem;
        font-weight: bold;
        color: rgb(255, 68, 31);
    }
    .primary-navigation a{
        color: rgb(255, 68, 31);
        font-family:'Nunito',sans-serif;
        font-weight: 700;
        font-size: 1.5rem;
        text-decoration: none;
    }
    .primary-navigation a > [aria-hidden="true"]{
        font-weight: 700;
        margin-inline-end: 0.5em;
    }
    .primary-navigation a.options:hover{
        background-color: rgb(245, 135, 21);
        color: white;
    }
    .selected {
        background-color: rgb(245, 135, 21);
        color: white;
    }
    @media (max-width: 1100px){
        .primary-navigation{
            position: fixed;
            --gap: 2em;
            inset: 0 0 0 55%;
            z-index: 1000;
            flex-direction: column;
            padding: min(30vh,10rem) 2rem;
            background: hsla(0, 4%, 16%, 0.1);
            backdrop-filter: blur(1em);
            transform: translateX(100%);
            transition: transform 350ms ease-out;
        }
        .primary-navigation[data-visible="true"]{
            transform: translateX(0%);
        }
        .primary-navigation a{
            color: black;
        }
        .mobile-nav-toggle{
            display: block;
            position: absolute;
            z-index: 9999;
            background: url("/resources/images/menu.png") center;
            width: 3rem;
            aspect-ratio: 1;
            border: 0;
            top: 2rem;
            right: 2rem;
        }
    }
</style>
<script type="text/javascript">
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
