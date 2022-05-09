<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
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
        <div><span class="presentation-text title"><spring:message code="Register.title"/></span></div>
        <div>
            <c:url value="/register" var="postPath"/>
            <form:form modelAttribute="customerRegisterForm" action="${postPath}" method="post">
                <div>
                    <form:errors path="mail" element="p" cssStyle="color:red"/>
                    <form:label path="mail" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Register.mail"/></form:label>
                    <form:input path="mail" type="text"/>
                </div>
                <div>
                    <form:errors path="username" element="p" cssStyle="color:red"/>
                    <form:label path="username" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Register.user"/></form:label>
                    <form:input path="username" type="text"/>
                </div>
                <div>
                    <form:errors path="customerName" element="p" cssStyle="color:red"/>
                    <form:label path="customerName" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Register.name"/></form:label>
                    <form:input path="customerName" type="text"/>
                </div>
                <div>
                    <form:errors path="password" element="p" cssStyle="color:red"/>
                    <form:label path="password" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Register.password"/></form:label>
                    <form:input path="password" type="password"/>
                </div>
                <div>
                    <form:errors path="phone" element="p" cssStyle="color:red"/>
                    <form:label path="phone" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Register.phone"/></form:label>
                    <form:input path="phone" type="text"/>
                </div>
                <spring:message code="Button.continue" var="label"/>
                <spring:message code="Button.loading" var="label2"/>
                <input type="submit" value="${label}" class="btn confirm-btn center" onclick="this.form.submit(); this.disabled=true;this.value=${label2}; "/>
            </form:form>
        </div>
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
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 1%;
        min-width: 25%;
        border-radius: .8rem;
    }
    .presentation-text.title{
        font-size: 2rem;
    }
    .center{
        align-items: center;
    }
</style>