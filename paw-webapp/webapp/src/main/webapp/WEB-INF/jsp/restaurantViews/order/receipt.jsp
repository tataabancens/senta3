<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="../../components/navbar.jsp" %>

<div class="page-container">

    <div class="card confirm-card">
        <div class="card-content wider-content center">
            <div style="margin-bottom: 5%;">
                <span class="presentation-text" style="font-size: 3rem;"><c:out value="${restaurant.restaurantName}"/></span>
            </div>
            <sec:authorize access="!hasRole('RESTAURANT')">
                <span class="presentation-text"><spring:message code="Receipt.subtitle"/></span>
            </sec:authorize>
            <div style="display: flex;margin-bottom: 4%;">
                <span class="presentation-text" style="font-size: 2rem;">Mesa: <c:out value="${reservation.tableNumber}"/></span>
            </div>
            <div class="center" style="margin-bottom: 4%;">
                <span class="presentation-text" style="font-size: 2rem;"><spring:message code="Order.title"/></span>
            </div>
            <div class="summary">
                <div class="titles" style="margin-bottom: 4%;">
                    <div class="dishname">
                        <span class="presentation-text" style="font-size: 2rem;"><spring:message code="Order.dish"/></span>
                    </div>
                    <div>
                        <span class="presentation-text" style="font-size: 2rem;"><spring:message code="Order.qty"/></span>
                    </div>
                    <div>
                        <span class="presentation-text" style="font-size: 2rem;"><spring:message code="Order.price"/></span>
                    </div>
                    <div>
                        <span class="presentation-text" style="font-size: 2rem;"><spring:message code="Order.total"/></span>
                    </div>
                </div>
                <c:forEach var="orderItem" items="${orderItems}">
                    <div class="titles">
                        <div class="dishname-div">
                            <span class="items-title text dishname"><c:out value="${orderItem.dish.dishName}"/></span>
                        </div>
                        <div>
                            <span class="items-title text"><c:out value="${orderItem.quantity}"/></span>
                        </div>
                        <div>
                            <fmt:formatNumber var="orderItemUnitPrice" type="number" value="${(orderItem.unitPrice * discountCoefficient)}" maxFractionDigits="2"/>
                            <span class="items-title text">$<c:out value="${orderItemUnitPrice}"/></span>
                        </div>
                        <div>
                            <fmt:formatNumber var="orderItemPrice" type="number" value="${(orderItem.unitPrice * orderItem.quantity * discountCoefficient)}" maxFractionDigits="2"/>
                            <span class="items-title text"><c:out value="${orderItemPrice}"/></span>
                        </div>
                    </div>
                </c:forEach>

                <hr/>

                <div class="titles" style="margin-top: 4%;">
                    <div >
                        <p class="presentation-text" style="font-size: 2.5rem;"><spring:message code="Order.total"/></p>
                    </div>
                    <div>
                        <fmt:formatNumber var="totalPrice" type="number" value="${(total * discountCoefficient)}" maxFractionDigits="2"/>
                        <p class="presentation-text" style="font-size: 2.5rem;">$<c:out value="${totalPrice}"/></p>
                    </div>
                </div>
                    <sec:authorize access="!hasRole('RESTAURANT')">
                        <div>
                            <c:if test="${canOrderReceipt}">
                                <c:url value="/order/send-receipt?reservationSecurityCode=${reservation.securityCode}&restaurantId=${restaurant.id}" var="postUrl"/>
                                <form:form action="${postUrl}" method="post">
                                    <spring:message code="Button.confirm" var="label"/>
                                    <input type="submit" value="${label}" class="waves-effect waves-light btn confirm-btn green right">
                                </form:form>
                            </c:if>
                            <c:if test="${!canOrderReceipt}">
                                <div disabled class="waves-effect waves-light btn confirm-btn right"><spring:message code="Receipt.title"/></div>
                            </c:if>
                        </div>
                    </sec:authorize>
                <sec:authorize access="hasRole('RESTAURANT')">
                    <div style="margin-top: 4%;">
                        <c:url value="/restaurant=${restaurantId}/finishCustomer=${reservationSecurityCode}?orderBy=${orderBy}&direction=${direction}&filterStatus=${filterStatus}&page=${page}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <spring:message code="Button.finishres" var="label"/>
                            <input type="submit" value="${label}" class="waves-effect waves-light btn confirm-btn green right">
                        </form:form>
                    </div>
                </sec:authorize>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>

<style>
    
    .summary{
        margin-top: 20px;
        width: 100%;
    }


    .card{
        border-radius: 16px;
    }

    body{
        background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }

    .center{
        justify-content: center;
    }

    .with-margin{
        margin-top: 10%;
        margin-bottom: 10%;
    }

    .page-container {
        display: flex;
        align-items: center;
        justify-content: center;
        margin-top: 5%;
    }

    .titles{
        display: flex;
        justify-content: space-between;
        margin-right: 10px;
    }

    .confirm-card{
        display:flex;
        justify-content: center;
        width: 40%;
        max-width: 60%;
        margin-left: 5%;
        margin-right: 5%;
    }

    .wider-content{
        width: 100%;
    }


</style>