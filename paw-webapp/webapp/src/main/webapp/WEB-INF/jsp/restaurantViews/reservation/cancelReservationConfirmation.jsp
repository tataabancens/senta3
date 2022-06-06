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
            <div class="card-content">
                <span class="presentation-text"><spring:message code="Cancelreservation.confirm" arguments="${reservationId}"/> </span>
                    <div class="center" style="margin-top: 15%">
                        <c:url value="/restaurant=${restaurantId}/cancelReservationConfirmation/securityCode=${reservationSecurityCode}?orderBy=${orderBy}&direction=${direction}&filterStatus=${filterStatus}&page=${page}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <spring:message code="Button.cancel" var="label"/>
                            <input type="submit" value="${label}" class="waves-effect waves-light btn confirm-btn red" onclick="this.form.submit(); this.disabled=true;this.value='procesando'; ">
                        </form:form>
                    </div>
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
    body{
        background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }
</style>