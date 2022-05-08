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
            <div class="card-content white-text">
                <span class="card-title text price center"><spring:message code="Cancelreservation.confirm" arguments="${reservationId}"/> </span>
                <div class="row margin-0">
                    <div class="col s12 center">
                        <c:url value="/restaurant=${restaurantId}/cancelReservationConfirmation/id=${reservationId}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <spring:message code="Button.cancel" var="label"/>
                            <input type="submit" value="${label}" class="waves-effect waves-light btn plus-btn red" onclick="this.disabled=true;this.value='procesando'; this.form.submit();">
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>

<style>
    .text{
        color:  #707070
    }

    .center{
        justify-content: center;
    }

    .card{
        display: flex;
        justify-self: center;
        border-radius: 0.8rem;
        justify-content: center;
        padding: 1%;
        width: 50%;
    }

</style>