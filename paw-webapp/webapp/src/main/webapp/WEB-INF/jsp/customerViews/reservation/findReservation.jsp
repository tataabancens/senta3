<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="../../components/navbar.jsp" %>
<div class="form-container">
            <div class="card card-content">
                <span class="presentation-text"><spring:message code="Findreservation.subtitle"/></span>
                <c:url value="/findReservation" var="postUrl"/>
                <form:form modelAttribute="findReservationForm" action="${postUrl}" method="post">
                    <div class="reservationId">
                        <div>
                            <form:errors path="securityCode" element="p" cssClass="error"/>
                            <form:label path="securityCode">Codigo:</form:label>
                            <form:input type="text" path="securityCode"/>
                        </div>
                    </div>
                    <div class="submit center">
                        <spring:message code="Button.search" var="label"/>
                        <input type="submit" value="${label}" class="btn confirm-btn">
                    </div>
                </form:form>
            </div>
</div>
</body>
</html>

<style>

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
        justify-content: space-evenly;
        flex-direction: column;
        font-family: "Segoe UI", Lato, sans-serif;
        min-height: 150px;
        height: 300px;
        max-height: 800px;
        min-width: 400px;
        width: 500px;
        max-width: 600px;
    }


</style>