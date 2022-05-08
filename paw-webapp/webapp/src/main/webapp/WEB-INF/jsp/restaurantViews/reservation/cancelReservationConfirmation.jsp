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

    <!-- Materialize CSS -->
    <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="../../components/navbar.jsp" %>
    <div class="form-container">
        <div class="card">
            <span class="presentation-text"><spring:message code="Cancelreservation.confirm" arguments="${reservationId}"/> </span>
            <div class="btn-row">
                <a href="<c:url value="/restaurant=${restaurantId}/reservations"/>" class="btn plus-btn" style="background-color: #37A6E6;margin-right: 2rem; ">Volver</a>
                <c:url value="/restaurant=${restaurantId}/cancelReservationConfirmation/id=${reservationId}" var="postUrl"/>
                <form:form action="${postUrl}" method="post">
                    <input type="submit" value="CANCELAR RESERVA" class="waves-effect waves-light btn plus-btn red" onclick="this.disabled=true;this.value='procesando'; this.form.submit();">
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>

<style>
    .btn-row{
        display:flex ;
        margin-top: 3rem;
        width: 100%;
        justify-content: center;
    }

    .card{
        display: flex;
        flex-direction: column;
        justify-self: center;
        border-radius: 0.8rem;
        justify-content: center;
        padding: 1%;
        width: fit-content;
    }

</style>