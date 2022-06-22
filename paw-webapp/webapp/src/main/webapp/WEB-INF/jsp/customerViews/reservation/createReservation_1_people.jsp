<%@ page import="java.util.LinkedList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

<body>
<%@ include file="../../components/navbar.jsp" %>

    <c:url value="/createReservation-1" var="postPath"/>
<div style="display: flex;justify-content: center;margin-top: 5%;">
    <div class="card register-card">
        <span class="presentation-text"><spring:message code="Createreservation.people.title"/></span>
        <c:url value="/createReservation-1" var="postPath"/>
        <form:form modelAttribute="qPeopleForm" action="${postPath}" method="post">
            <div class="input-field">
                <form:errors path="number" element="p" cssStyle="color:red"/>
                <form:label path="number" class="helper-text" data-error="wrong" data-success="right"/>
                <form:input path="number" required="required" type="text"/>
            </div>
            <spring:message code="Button.confirm" var="label"/>
            <input type="submit" value="${label}" class="btn confirm-btn center"/>
        </form:form>
    </div>
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
        margin: 10px;
        justify-content: space-evenly;
        align-content: center;
        flex-direction: column;
        min-height: 250px;
        min-width: 450px;
        padding: 20px;
    }

    .btn.confirm-btn{
        margin-left: 20%;
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