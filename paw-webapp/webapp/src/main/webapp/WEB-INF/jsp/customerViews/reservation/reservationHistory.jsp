
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <title>Senta3</title>
</head>
<body>
<%@ include file="../../components/navbar.jsp" %>
<div class="restaurant-header" style="background-color: rgb(255, 242, 229);border-radius: 0px;">
    <div class="restaurant-info" style="margin-left: 2%;">
        <h1 class="presentation-text header-title">Resumen del pedido<!--<spring:message code="Kitchen.title"/>--></h1>
    </div>
</div>
<div class="contentContainer">

    <table>
        <thead>
        <tr>
            <th><h3 class="presentation-text"><spring:message code="Order.dish"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Order.qty"/></h3></th>
            <th><h3 class="presentation-text"><spring:message code="Order.subtotal"/></h3></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="orderItem" items="${orderItemList}">
            <tr>
                <td data-label="Dish-name" class="table-cell"><span class="text description"><c:out value="${orderItem.dish.dishName}"/></span></td>
                <td data-label="Dish-qty" class="table-cell"><span class="text description"><c:out value="${orderItem.quantity}"/></span></td>
                <td data-label="Dish-price" class="table-cell"><span class="text description"><c:out value="${orderItem.unitPrice}"/></span></td>
            </tr>
            <hr class="solid-divider">
        </c:forEach>
        </tbody>
    </table>
    <div style="display: flex;justify-content: space-between;">
        <span class="presentation-text" style="font-size: 2.3rem;"><spring:message code="Order.total"/></span>
        <span class="presentation-text" style="font-size: 2.3rem;">$<c:out value="${total}"/></span>
    </div>
</div>
</body>
</html>

<style>
    .contentContainer{
        display: flex;
        flex-direction: column;
        padding: 0 2% 0 2%;
    }
    table{
        width: 100%;
        justify-content: space-evenly;
    }
</style>
<script type="text/javascript">


</script>
