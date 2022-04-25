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

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="components/navbar.jsp" %>
<div class="form-container">
            <div class="card card-content">
                <span class="main-title"><c:out value="${dish.dishName}"/></span>
                <c:url value="/menu/orderItem?reservationId=${reservationId}&dishId=${dish.id}" var="postUrl"/>
                <form:form modelAttribute="orderForm" action="${postUrl}" method="post">
                    <div class ="orderItem">
                        <p class="title2"><c:out value="${dish.dishDescription}"/></p>
                        <p class="price">$<c:out value="${dish.price}"/></p>
                        <form:errors path="orderItem" element="p" cssStyle="color: red"/>
                        <form:label path="orderItem.quantity" class="helper-text" data-error="wrong" data-success="right">QTY</form:label>
                        <form:input path="orderItem.quantity" type="number" min="1" max="50" required="required" value="0" cssClass="center"/>
                        <div class="submit center">
                            <input type="submit" value="Confirmar" class="waves-effect waves-light btn continue-btn">
                        </div>
                    </div>
                </form:form>
            </div>
    </div>
</body>
</html>

<style>

    body{
        background-color: #F0F0F0;
    }
    .form-container{
        display: flex;
        padding-top: 30px;
        justify-content: center;
    }
    .card{
        border-radius: 16px;
        padding: 20px;
        display: flex;
    }
    .card.card-content{
        justify-content: center;
        flex-direction: column;
        font-family: "Segoe UI", Lato, sans-serif;
        min-height: 150px;
        height: 500px;
        max-height: 800px;
        min-width: 400px;
        width: 500px;
        max-width: 600px;
    }

    .description{
        font-family: "Segoe UI", Lato, sans-serif;
        color:  #707070;
        font-size: 20px;
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
