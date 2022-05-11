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
    <title>Senta3</title>
</head>
<body>
<%@ include file="../components/navbar.jsp" %>

<div class="content">
        <div class="card register-card">
            <span class="presentation-text"><spring:message code="Register.title"/></span>
            <c:url value="/registerShort/${customerId}/${reservationId}" var="postPath"/>
            <form:form modelAttribute="customerRegisterShortForm" action="${postPath}" method="post">
                <div class="input-field col s12 input">
                    <div class="input-field input" style="margin-bottom: 1%">
                        <form:errors path="mail" element="p" cssStyle="color:red"/>
                        <form:label path="mail" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createreservation.register.mail"/></form:label>
                        <form:input path="mail"  style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" required="required" maxlength="50" type="text"/>
                    </div>
                    <div>
                        <form:errors path="username" element="p" cssStyle="color:red"/>
                        <form:label path="username" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Register.user"/></form:label>
                        <form:input path="username" type="text"/>
                    </div>
                    <div>
                        <form:errors path="psPair" element="p" cssStyle="color:red"/>
                        <form:label path="psPair.password" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Register.password"/></form:label>
                        <form:input path="psPair.password" type="password"/>
                    </div>
                    <div>
                        <form:errors path="psPair" element="p" cssStyle="color:red"/>
                        <form:label path="psPair.checkPassword" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Register.password"/></form:label>
                        <form:input path="psPair.checkPassword" type="password"/>
                    </div>
                </div>
                <spring:message code="Button.continue" var="label"/>
                <input type="submit" value="${label}" class="btn confirm-btn"/>
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
    .content{
        display: flex;
        justify-content: center;
        margin-top: 30px;
    }
    .presentation-text{
        font-size: clamp(1.3rem,2.5em,9rem);
    }
    .card.register-card{
        display: flex;
        flex-direction: column;
        padding: 2%;
        margin: 2%;
        width: clamp(10em,28em,30em);
        border-radius: .8rem;
    }
</style>
