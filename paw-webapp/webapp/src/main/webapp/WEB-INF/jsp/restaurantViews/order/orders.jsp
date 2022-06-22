<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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

    <!-- Auto refresh each x seconds-->
    <meta http-equiv="refresh" content="10">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

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
                        <a class="options selected" style="color:white;" href="<c:url value="/restaurant=1/orders"/>">
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
<div class="content-container">
    <div class="card incoming-orders">
        <div>
            <span class="presentation-text title"><h5><spring:message code="Kitchen.new.orders.title"/></h5></span>
        </div>
        <div class="cardContainer">
            <c:if test="${incomingItems.size() == 0}">
                <div style="display: flex; justify-content: center;width: 100%;">
                    <span class="presentation-text title"><h11><spring:message code="Kitchen.new.no"/></h11></span>
                </div>
            </c:if>
            <c:forEach var="item" items="${incomingItems}">
                <div class="card order-card">
                    <div class="card-content white-text">
                        <span class="presentation-text" style="color: #171616;"><c:out value="${item.dish.dishName}"/></span>
                        <p class="text description"><spring:message code="Kitchen.order.qty"/> <c:out value="${item.quantity}"/></p>
                        <p class="text description"><spring:message code="Kitchen.order.table"/> <c:out value="${item.reservation.tableNumber}"/></p>
                        <p class="text description"><spring:message code="Kitchen.order.res"/> <c:out value="${item.reservation.securityCode}"/></p>
                    </div>
                    <c:url value="/restaurant=${restaurantId}/orders/incomingToFinished-${item.id}" var="postUrl"/>
                    <form:form action="${postUrl}" method="post">
                        <spring:message code="Button.finish" var="label"/>
                        <input type="submit" value="${label}" class="waves-effect waves-light btn confirm-btn blue center">
                    </form:form>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="card finished-orders">
        <div>
            <span class="presentation-text title"><h5><spring:message code="Kitchen.old.orders.title"/></h5></span>
        </div>
        <div class="cardContainer">
            <c:if test="${finishedItems.size() == 0}">
                <div style="display: flex; justify-content: center;align-items: center;margin-top: 20%; width: 100%;">
                    <span class="presentation-text title"><h11><spring:message code="Kitchen.order.no"/></h11></span>
                </div>
            </c:if>
            <c:forEach var="item" items="${finishedItems}">
                <div class="card order-card">
                    <div class="card-content white-text">
                        <span class="presentation-text" style="color: #171616;"><c:out value="${item.dish.dishName}"/></span>
                        <p class="text description"><spring:message code="Kitchen.order.qty"/><c:out value="${item.quantity}"/></p>
                        <p class="text description"><spring:message code="Kitchen.order.table"/> <c:out value="${item.reservation.tableNumber}"/></p>
                        <p class="text description"><spring:message code="Kitchen.order.res"/> <c:out value="${item.reservation.securityCode}"/></p>
                    </div>
                    <c:url value="/restaurant=${restaurantId}/orders/finishedToDelivered-${item.id}" var="postUrl"/>
                    <form:form action="${postUrl}" method="post">
                        <spring:message code="Button.deliver" var="label"/>
                        <input type="submit" value="${label}" class="waves-effect waves-light btn blue center confirm-btn">
                    </form:form>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>

<style>

    .content-container{
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: space-between;
        padding: 20px;
        flex-wrap: wrap;
    }
    .card{
        border-radius: 16px;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        justify-content: flex-start;
        align-items: center;
    }
    .cardContainer{
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
        width: 100%;
    }
    .card.incoming-orders{
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        padding: 10px;
        min-height: 30em;
        width: 48%;
        height: 100%;
        margin: 10px;
    }
    .card.finished-orders{
        display: flex;
        flex-direction: column;
        padding: 10px;
        width: 48%;
        min-height: 30em;
        height: 100%;
        margin: 10px;
    }
    .order-card{
        width: 100%;
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-content: flex-start;
        margin: 15px;
        max-width: 40%;
        padding: 15px;
    }

    .description{
        color:  #707070;
        font-size: 17px;
    }
    .header-title{
        color: white;
    }

    .center{
        justify-content: center;
    }

    @media (max-width: 600px) {
        .card.incoming-orders{
            width: 100%;
        }
        .card.finished-orders{
            width: 100%;
        }
    }

</style>

