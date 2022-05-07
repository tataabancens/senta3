<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registro</title>
</head>
<body>
<%@ include file="../components/navbar.jsp" %>

<div class="content">
        <div class="card register-card">
            <span class="main-title"><spring:message code="Register.title"/></span>
            <c:url value="/registerShort/${customerId}/${reservationId}" var="postPath"/>
            <form:form modelAttribute="customerRegisterShortForm" action="${postPath}" method="post">
                <div class="input-field col s12 input">
                    <div>
                        <form:errors path="username" element="p" cssStyle="color:red"/>
                        <form:label path="username" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Register.user"/></form:label>
                        <form:input path="username" type="text"/>
                    </div>
                    <div>
                        <form:errors path="password" element="p" cssStyle="color:red"/>
                        <form:label path="password" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Register.password"/></form:label>
                        <form:input path="password" type="password"/>
                    </div>
                </div>
                <input type="submit" value="Continuar" class="continue-btn"/>
            </form:form>
        </div>
</div>
</body>
</html>
<style>
    .content{
        display: flex;
        justify-content: center;
        margin-top: 5%;
    }
    .card.register-card{
        padding: 2%;
        min-width: 40%;
        max-width: 60%;
        border-radius: .8rem;
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
</style>
