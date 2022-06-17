<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<%@ include file="../../components/navbar.jsp" %>
<div class="restaurant-header" style="background-color: rgb(255, 242, 229);border-radius: 0px;">
    <div class="restaurant-info" style="margin-left: 2%;">
        <h1 class="presentation-text header-title"><spring:message code="Waiter.title"/></h1>
    </div>
</div>
<div class="content-container">
    <div class="card finished-orders">
        <div style="position: absolute;top: 0;">
            <span class="presentation-text title"><h5><spring:message code="Waiter.hands"/></h5></span>
        </div>
        <div class="cardContainer">
            <c:forEach var="reservation" items="${reservations}">

                <c:if test="${reservation.hand}">
                    <div class="card order-card">
                        <div class="card-content white-text">
                            <p class="presentation-text" style="color: #171616;"><spring:message code="Kitchen.order.table"/> <c:out value="${reservation.tableNumber}"/></p>
                            <p class="presentation-text" style="color: #171616;"><spring:message code="Kitchen.order.name"/> <c:out value="${reservation.customer.user.username}"/></p>
                        </div>
                        <c:url value="/restaurant=${restaurantId}/waiter/lowerHand-${reservation.securityCode}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <spring:message code="Button.attended" var="label"/>
                            <input type="submit" value="${label}" class="waves-effect waves-light btn blue center confirm-btn">
                        </form:form>
                    </div>
                </c:if>

            </c:forEach>
        </div>
    </div>

    <div class="card finished-orders">
        <div style="position: absolute;top: 0;">
            <span class="presentation-text title"><h5><spring:message code="Kitchen.old.orders.title"/></h5></span>
        </div>
        <div class="cardContainer">
            <c:forEach var="reservation" items="${reservations}">
                <c:forEach var="item" items="${finishedItems}">
                    <c:if test="${item.reservation.id == reservation.id}">
                        <div class="card order-card">
                            <div class="card-content white-text">
                                <span class="presentation-text" style="color: #171616;"><c:out value="${item.dish.dishName}"/></span>
                                <p class="text description"><spring:message code="Kitchen.order.qty"/><c:out value="${item.quantity}"/></p>
                                <p class="text description"><spring:message code="Kitchen.order.table"/> <c:out value="${item.reservation.tableNumber}"/></p>
                                <p class="text description"><spring:message code="Kitchen.order.res"/> <c:out value="${item.reservation.securityCode}"/></p>
                            </div>
                            <c:url value="/restaurant=${restaurantId}/waiter/finishedToDelivered-${item.id}" var="postUrl"/>
                            <form:form action="${postUrl}" method="post">
                                <spring:message code="Button.deliver" var="label"/>
                                <input type="submit" value="${label}" class="waves-effect waves-light btn blue center confirm-btn">
                            </form:form>
                        </div>
                    </c:if>
                </c:forEach>
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
        justify-content: center;
        padding: 10px;
        width: 48%;
        height: 100%;
        margin: 10px;
    }
    .card.finished-orders{
        display: flex;
        flex-direction: column;
        justify-content: center;
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

