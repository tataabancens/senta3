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
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
    <div class="row">
        <%@ include file="../../components/navbar.jsp" %>
    </div>

    <div class="content-container">

        <div class="confirm-card card">
            <div class="card-content white-text">
                <span class="text description"><spring:message code="Cancel.subtitle"/></span>
                    <div class="center-btn">
                        <c:url value="/reservation-cancel?reservationId=${reservationId}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <spring:message code="Button.confirm" var="label"/>
                            <input type="submit" value="${label}" class="waves-effect waves-light btn confirm-btn red center">
                        </form:form>
                    </div>
            </div>
        </div>
    </div>
</body>
</html>

<style>

    .content-container{
        padding-left: 20px;
        padding-right: 20px;
        display: flex;
        flex-direction: column;
        justify-items: center;
        align-items: center;
    }
    .center-btn{
        margin-left: 25%;
        margin-top: 15%;
    }
    i{
        color: white;
        margin-right: 25px;
    }
    .card{
        border-radius: 16px;
        margin: 20px;
        padding: 20px;
        display: flex;
    }

    body{
        background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }



    .confirm-card{
        display:flex;
        justify-content: center;
        width: 40%;
        max-width: 60%;
        margin-left: 5%;
        margin-right: 5%;
    }



</style>
