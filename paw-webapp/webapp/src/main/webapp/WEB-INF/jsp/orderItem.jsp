<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
</head>
<body>
<%@ include file="components/navbar.jsp" %>
<div class="row">
    <div class=" col s4 offset-s4 box card dish-card test-box">
            <div class="card-content white-text">
                <div class="block">
                    <span class="card-title title text "><c:out value="${dish.dishName}"/></span>
                </div>
                <c:url value="/menu/orderItem?reservationId=${reservationId}&dishId=${dish.id}" var="postUrl"/>
                <form:form modelAttribute="orderForm" action="${postUrl}" method="post">
                    <div class ="block right center">
                        <form:errors path="orderItem.quantity" element="p" cssStyle="color: red"/>
                        <form:label path="orderItem.quantity" class="helper-text" data-error="wrong" data-success="right">QTY</form:label>
                        <form:input path="orderItem.quantity" type="number" cssClass="center" min="1"/>
                        <div class="center">
                            <input type="submit" value="+" class="waves-effect waves-light btn continue-btn" >
                        </div>
                    </div>
                </form:form>
                <p class="description"><c:out value="${dish.dishDescription}"/></p>
                <p class="price">$<c:out value="${dish.price}"/></p>
            </div>
    </div>
</div>
</body>
</html>

<style>

    body{
        background-color: #F0F0F0;
    }
    .text{
        color:  #707070
    }


    .card{
        border-radius: 16px;
        display: grid;
    }

    .restaurant-card{
    }

    .dish-card{
        width: 100%;
    }

    .description{
        color:  #707070;
        font-size: 17px;
    }

    .title2{
        justify-content: center;
        color:  #707070;
        font-size: 20px;
    }

    .price{
        font-size: 25px;
        font-weight: bold;
        color: black;
    }

    .continue-btn{
        padding-inline: 7%;
        padding-block: 1%;
        border-radius: 16px;
        background-color: #37A6E6;
        margin-top: 5%;
        opacity: 57%;
    }

    .continue-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }

    .center{
        justify-content: center;
    }

    .with-margin{
        margin-top: 10%;
        margin-bottom: 10%;
    }

    .smaller{
        width: 100%;
        margin-bottom: 0;
        margin-top: 0;
    }

    .already-reserved-btn{
    }

</style>
