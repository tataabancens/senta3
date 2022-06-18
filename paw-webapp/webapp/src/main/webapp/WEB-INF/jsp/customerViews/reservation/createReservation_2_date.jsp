<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
<body>
<%@ include file="../../components/navbar.jsp" %>

    <c:url value="/createReservation-2/${reservationSecurityCode}" var="postPath"/>
<div style="display: flex;justify-content: center;margin-top: 5%;">
    <form:form modelAttribute="dateForm" action="${postPath}" method="post">
        <div class="card register-card">
            <span class="presentation-text"><spring:message code="Createreservation.day.title"/></span>
            <div>
                <form:errors path="date" element="p" cssStyle="color:red"/>
                <form:label path="date" class="helper-text" data-error="wrong" data-success="right"/>
                <form:input type="date" required="required" path="date"/>
            </div>
            <div class="submit center">
                <spring:message code="Button.confirm" var="label"/>
                <input type="submit" value="${label}" class="btn confirm-btn"/>
            </div>
        </div>
    </form:form>
</div>


</body>
</html>

<style>
    body{
        background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }
    .card{
        border-radius: .8rem;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-evenly;
        align-content: center;
        flex-direction: column;
        min-height: 250px;
        min-width: 450px;
        padding: 20px;
    }
    form{
        display: flex;
        flex-wrap: wrap;
    }
    .content{
        display: flex;
        justify-content: center;
        padding: 20px;
        align-content: center;
    }
    span{
        font-family: "Segoe UI", Lato, sans-serif;
        font-size: 23px;
    }
    input{
        font-family: "Segoe UI", Lato, sans-serif;
        font-size: 20px;
    }
    select{
        display: flex;
    }

    .back-btn{
        border-radius: 16px;
        margin-top: 5%;
        background-color: #E63737 ;
        opacity: 87%;
    }

    .back-btn:hover{
        border-radius: 16px;
        margin-top: 5%;
        background-color: #E63737 ;
        opacity: 100%;
    }

    .input{
        margin-bottom: 1px;
    }

    .center{
        align-items: center;
    }

</style>
