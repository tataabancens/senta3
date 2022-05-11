<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "java.io.*,java.util.*" %>


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
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body >
<a id="full-menu"></a>
<%@ include file="../../components/navbar.jsp" %>

<div class="restaurant-header">
    <div class="restaurant-info">
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
    <div class="reservation-info" style="margin-right: 4%;">
            <p class="presentation-text header-title info">Reserva:</p>
            <p class="presentation-text header-title info">Dia: hoy</p>
            <span class="presentation-text header-title info">Hora: <c:out value="${reservation.reservationHour}"/>:00</span>
    </div>
</div>
<div class="page-container">
    <div class="orders-and-info">
        <div class="card filter-box">
            <div class="client-actions">
                <span class="presentation-text text-center"><spring:message code="Fullmenu.reservation.number"/> <c:out value="${reservation.reservationId}"/></span>
                <c:if test="${canOrderReceipt}">
                    <a class="waves-effect waves-light btn confirm-btn text description " href="<c:url value="/order/send-receipt?reservationId=${reservation.reservationId}&restaurantId=${restaurant.id}"/>"><spring:message code="Fullmenu.receipt"/></a>
                </c:if>
                <c:if test="${!canOrderReceipt}">
                    <a disabled class="waves-effect waves-light btn confirm-btn text description " href=""><spring:message code="Fullmenu.receipt"/></a>
                </c:if>
                <div class="center div-padding">
                    <a class="waves-effect waves-light btn confirm-btn red text description " href="<c:url value="/reservation-cancel?reservationId=${reservation.reservationId}&restaurantId=${restaurant.id}"/>"><spring:message code="Fullmenu.reservation.cancel"/></a>
                </div>
            </div>
            <div>
                <span class="presentation-text"><spring:message code="FilterBox.title"/></span>
                <ul class="categories">
                    <c:forEach var="category" items="${categories}">
                        <a href="<c:url value="/menu?reservationId=${reservation.reservationId}&category=${category}"/>" style="margin: 0.2vw">
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
        </div>
        <sec:authorize access="isAuthenticated()">
            <c:if test="${!reservation.reservationDiscount}">
                <c:if test="${customer.points >= 100}">
                    <div class="card client-actions discounts">
                        <span class="presentation-text discounts"><spring:message code="Fullmenu.discount"/></span>
                        <c:url value="/menu/applyDiscount/${reservation.reservationId}" var="postUrl_actDisc"/>
                        <form:form action="${postUrl_actDisc}" method="post">
                            <spring:message code="Button.activate" var="label"/>
                            <input type="submit" value="${label}" class="waves-effect waves-light btn confirm-btn text description ">
                        </form:form>
                    </div>
                </c:if>
            </c:if>
            <c:if test="${reservation.reservationDiscount}">
                <div class="card client-actions discounts">
                    <span class="presentation-text"><spring:message code="Fullmenu.discount.apply"/></span>
                    <c:url value="/menu/cancelDiscount/${reservation.reservationId}" var="postUrl_undoDisc"/>
                    <form:form action="${postUrl_undoDisc}" method="post">
                        <spring:message code="Button.cancel" var="label"/>
                        <input type="submit" value="${label}" class="waves-effect waves-light btn confirm-btn text description ">
                    </form:form>
                </div>
            </c:if>
        </sec:authorize>
        <div class="orderList">
            <div class="card order-card">
                <span class="presentation-text"><spring:message code="Order.title"/></span>
                <div class="order-headers">
                    <span class="presentation-text"><spring:message code="Order.dish"/></span>
                    <span class="presentation-text"><spring:message code="Order.qty"/></span>
                    <span class="presentation-text"><spring:message code="Order.total"/></span>
                </div>
                <hr class="solid-divider">
                <div class="order-info">
                    <c:forEach var="orderItem" items="${orderItems}">
                        <div class="order-item">
                            <div class="order-field center"><span class="text description "><c:out value="${orderItem.dishName}"/></span></div>
                            <div class="order-field center"><span class="text description "><c:out value="${orderItem.quantity}"/></span></div>
                            <fmt:formatNumber var="orderItemPrice" type="number" value="${(orderItem.unitPrice * orderItem.quantity * discountCoefficient)}" maxFractionDigits="2"/>
                            <div class="order-field center"><span class="text description "><c:out value="${orderItemPrice}"/></span></div>
                            <c:url value="/order/remove-dish?orderItemId=${orderItem.orderItemId}&reservationId=${reservation.reservationId}" var="postUrl_remDish"/>
                            <form:form action="${postUrl_remDish}" method="post">
                                <button type="submit" class="small btn-floating" style="background-color: #757575">
                                    <i class="material-icons clear-symbol">clear</i>
                                </button>
                            </form:form>
                        </div>
                        <hr class="solid-divider">
                    </c:forEach>
                </div>
                <div class="order-total">
                    <div>
                        <p class="presentation-text"><spring:message code="Order.total"/></p>
                    </div>
                    <div>
                        <fmt:formatNumber var="totalPrice" type="number" value="${(total * discountCoefficient)}" maxFractionDigits="2"/>
                        <p class="presentation-text right"><c:out value="${totalPrice}"/></p>
                    </div>
                </div>
                <div class="order-btn-row">
                    <div>
                        <c:if test="${selected > 0}">
                            <c:url value="/order/empty-cart?reservationId=${reservation.reservationId}" var="postUrl"/>
                            <form:form action="${postUrl}" method="post">
                                <spring:message code="Order.empty" var="label"/>
                                <input type="submit" value="${label}" class="waves-effect waves-light btn confirm-btn red text description">
                            </form:form>
                        </c:if>
                        <c:if test="${selected == 0}">
                            <a disabled class="waves-effect waves-light btn confirm-btn red text description "><spring:message code="Order.empty"/></a>
                        </c:if>
                    </div>
                    <div>
                        <c:if test="${selected > 0}">
                            <a class="waves-effect waves-light btn confirm-btn green text description " href="<c:url value="/order/send-food?reservationId=${reservation.reservationId}&restaurantId=${restaurant.id}"/>"><spring:message code="Button.continue"/></a>
                        </c:if>
                        <c:if test="${selected == 0}">
                            <a disabled class="waves-effect waves-light btn confirm-btn green text description "><spring:message code="Button.continue"/></a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="dish-categories">
        <c:if test="${reservation.reservationStatus.name != 'SEATED'}">
            <div style="display: flex; flex-wrap: wrap;justify-content:center; width: fit-content;">
                <div class="card information">
                    <i class="medium material-icons" style="color: #0d0d56">info</i>
                    <span class="presentation-text" style="color: #0d0d56; font-size: 1.2rem;margin-right: 5px;"><spring:message code="Order.disclaimer"/></span>
                </div>
            </div>
        </c:if>
        <div>
            <h3 class="presentation-text header-title"><c:out value="${currentCategory.spanishDescr}"/></h3>
        </div>
        <div class="dishList">
            <c:forEach var="dish" items="${restaurant.dishes}">
                <c:if test="${unavailable.contains(dish.id)}">
                <div class="card horizontal">
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
                                <span class="presentation-text"><c:out value="${dish.dishName}"/></span>
                                <p class="text description"><c:out value="${dish.dishDescription}"/></p>
                                <c:if test="${reservation.reservationDiscount}">
                                    <span id="original-price" class="text price">$<c:out value="${dish.price}"/></span>
                                    <fmt:formatNumber var="dishPrice" type="number" value="${(dish.price * discountCoefficient)}" maxFractionDigits="2"/>
                                    <span id="discounted-price" class="text price">$<c:out value="${dishPrice}"/></span>
                                </c:if>
                            </div>
                            <c:if test="${!reservation.reservationDiscount}">
                                <span class="text price info">$<c:out value="${dish.price}"/></span>
                            </c:if>
                        </div>
                    </div>
                </div>
                </c:if>
                <c:if test="${!unavailable.contains(dish.id)}">
                    <a href="<c:url value="/menu/orderItem?reservationId=${reservation.reservationId}&dishId=${dish.id}"/>"  class="card horizontal">
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
                                        <span class="presentation-text info"><c:out value="${dish.dishName}"/></span>
                                        <p class="text description info"><c:out value="${dish.dishDescription}"/></p>
                                        <c:if test="${reservation.reservationDiscount}">
                                            <span id="original-price" class="text price">$<c:out value="${dish.price}"/></span>
                                            <fmt:formatNumber var="dishPrice" type="number" value="${(dish.price * discountCoefficient)}" maxFractionDigits="2"/>
                                            <span id="discounted-price" class="text price">$<c:out value="${dishPrice}"/></span>
                                        </c:if>
                                    </div>
                                    <c:if test="${!reservation.reservationDiscount}">
                                        <span class="text price info">$<c:out value="${dish.price}"/></span>
                                    </c:if>
                                </div>
                            </div>
                    </a>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>


</body>
</html>

<style>
    .restaurant-header{
        display: flex;
        justify-content: space-between;
        background-color: rgb(255, 242, 229);
        border-radius: 0px;
    }
    .presentation-text.header-title.info{
        color: #E63737;
        font-size: clamp(1rem,1.2vw,2rem);
    }
    .page-container{
        padding: 1%;
        display: flex;
    }
    .card.horizontal{
        width: clamp(30rem,35%,50rem);
        height: clamp(8rem,25%,9rem);
        box-shadow: 0 1.4rem 8rem rgba(0,0,0,.35);
        transition: 0.8s;
        margin-right: 4%;
    }
    .reservation-info p{
        margin-block-end: 0.5em;
        margin-block-start: 0;
    }
    .presentation-text.info{
        color: black;
        font-size: clamp(0.7rem,1rem + 0.1vw,2rem) ;
    }
    .text.description.info{
        font-size: clamp(0.7rem,0.8rem + 0.1vw,1.1rem);
    }
    .card .card-content{
        padding: 10px;
    }
    .card.horizontal .card-image{
        max-width: 25%;
        margin-left: 1%;
    }
    .card.horizontal .card-image img{
        border-radius: .8rem;
        aspect-ratio: 1.1/1;
    }
    .card-stacked{
        height: 100%;
    }
    .orders-and-info{
        display: flex;
        flex-direction: column;
        width: 26%;
        height: 100%;
    }
    .presentation-text.text-center{
        text-align: center;
    }
    .dish-categories{
        display: flex;
        flex-direction: column;
        width: 74%;
    }
    .card.client-actions.discounts{
        max-width: 60%;
    }
    .presentation-text.discounts{
        font-size: 1.18rem;
    }
    .client-actions{
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-bottom: 5%;
    }
    .card.filter-box{
        width: 60%;
    }
    .btn.confirm-btn{
        color: white;
    }
    .btn-floating{
        width: 25px;
        height: 25px;
    }
    .btn-floating i{
        line-height: 25px;
    }
    i{
        color: rgb(255, 68, 31);
        align-self: center;
        margin-right: 25px;
    }
    .material-icons.clear-symbol{
        color: white;
    }
    .dishList{
        display: flex;
        flex-wrap: wrap;
        width: 100%;
        height: 100%;
    }
    .categories{
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    .card{
        border-radius: 16px;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        justify-content: flex-start;
        align-items: center;
    }
    .card.client-actions{
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        padding: 10px;
        min-height: 150px;
        max-height: 250px;
        min-width: 30%;
        width: 100%;
    }
    .orderList{
        width: 100%;
        height: 100%;
    }
    .order-card{
        display: flex;
        justify-content: flex-start;
        align-items: flex-start;
        max-height: 100%;
        flex-direction: column;
        min-width: 150px;
        min-height: 250px;
        padding: 20px;
        width: 100%;
    }
    .order-headers{
        display: flex;
        width: 100%;
        margin-top: 10px;
        margin-bottom: 10px;
        justify-content: space-evenly;
    }
    .order-info{
        display: flex;
        flex-direction: column;
        width: 100%;
        justify-content: space-evenly;
    }
    .orders-and-info{
        margin-right: 5%;
    }
    .order-item{
        display: flex;
        width: 100%;
        align-items: center;
        justify-content: space-evenly;
    }
    .order-field{
        width: 100%;
        display: flex;
        justify-content: space-between;
    }
    .order-total{
        width: 100%;
        display: flex;
        justify-content: space-around;
    }
    .card.horizontal:hover{
        transform: scale(1.1);
    }
    .order-btn-row{
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
    }
    .center{
        justify-self: center;
    }
    .div-padding{
        padding: 8px;
    }
    .center{
        justify-content: center;
    }
    p{
        line-height: 1.3;
    }
    .text.description{
        font-size: 1rem;
    }
    #original-price{
        text-decoration: line-through;
    }
    #discounted-price{
        color: blue;
        margin-left: 12%;
    }


</style>

