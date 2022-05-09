<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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

    <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="../components/navbar.jsp" %>

<div class="row">
    <div class="col offset"></div>
    <div class="card">
        <div>
            <span class="presentation-text"><spring:message code="Error.title"/></span>
        </div>
        <div>
            <span class="text description"><spring:message code="Error.404"/></span>
        </div>
        <div>
            <span class="text description"><spring:message code="Error.dontworry"/></span>
        </div>
        <div class="center">
            <a class="waves-effect waves-light btn confirm-btn" href="<c:url value="/"/>"><spring:message code="Button.back"/></a>
        </div>
    </div>
</div>
</body>
</html>

<style>

    .page-container{
        display: flex;
        justify-content: center;
        align-content: center;
    }
    .card{
        display: flex;
        flex-direction: column;
        width: fit-content;
        height: 60%;
        flex-wrap: wrap;
        border-radius: 10px;
    }
    .center{
        justify-content: center;
    }


</style>