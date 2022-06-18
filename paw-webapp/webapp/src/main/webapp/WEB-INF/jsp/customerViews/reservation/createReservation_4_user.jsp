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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">

<body>
<%@ include file="../../components/navbar.jsp" %>

    <c:url value="/createReservation-4/${reservation.securityCode}" var="postPath"/>
<div style="display: flex;justify-content: center;margin-top: 5%;">
    <form:form modelAttribute="reservationForm" action="${postPath}" method="post">
        <div class="card register-card">
            <span class="presentation-text"><spring:message code="Createreservation.register.title"/></span>
            <div class="input-field input">
                <form:errors path="mail" element="p" cssStyle="color:red"/>
                <form:label path="mail" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createreservation.register.mail"/></form:label>
                <form:input path="mail"  required="required" maxlength="50" type="text"/>
            </div>
            <div class="input-field input">
                <form:errors path="phone" element="p" cssStyle="color:red"/>
                <form:label path="phone" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createreservation.register.phone"/></form:label>
                <form:input path="phone" required="required" maxlength="13" type="text"/>
            </div>
            <div class="input-field input">
                <form:errors path="name" element="p" cssStyle="color: red"/>
                <form:label path="name" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createreservation.register.name"/></form:label>
                <form:input path="name" required="required" maxlength="50" type="text"/>
            </div>
            <div style="margin-top: 2%;">
                <label style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createreservation.register.date"/><c:out value="${reservation.getReservationOnlyDate()}"/></label>
            </div>
            <div style="margin-top: 2%;">
                <form:label path="qPeople" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createreservation.register.people"/><c:out value="${reservation.qPeople}"/></form:label>
            </div>
            <div style="margin-top: 2%;">
                <form:label path="hour" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createreservation.register.hour"/><c:out value="${reservation.reservationHour}"/>hs</form:label>
            </div>
            <div class="submit center">
                <spring:message code="Button.confirm" var="label"/>
                <spring:message code="Button.loading" var="label2"/>
                <input type="submit" value="${label}" class="btn confirm-btn center" style="margin-top: 5%;" onclick="this.form.submit(); this.disabled=true;this.value=${label2};"/>
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
        margin: 10px;
        justify-content: center;
        align-content: center;
        flex-direction: column;
        padding: 10px;
    }
    form{
        display: flex;
        flex-wrap: wrap;
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

    .input{
        margin-bottom: 1px;
    }

    .center{
        align-items: center;
    }

</style>
