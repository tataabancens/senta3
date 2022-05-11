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

<div class="content">
    <c:url value="/confirmReservation/${reservation.reservationId}" var="postPath"/>

    <form:form modelAttribute="confirmReservationForm" action="${postPath}" method="post">
        <div class="content-container">
            <div class="card register-card">
                <span class="presentation-text"><spring:message code="Reservation.confirm.check"/></span>
                <div class="input-field input" style="margin-bottom: 1%">
                    <form:errors path="mail" element="p" cssStyle="color:red"/>
                    <form:label path="mail" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createreservation.register.mail"/></form:label>
                    <form:input path="mail"  style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" required="required" maxlength="50" type="text"/>
                </div>
                <div class="input-field input" style="margin-bottom: 1%">
                    <form:errors path="phone" element="p" cssStyle="color:red"/>
                    <form:label path="phone" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createreservation.register.phone"/></form:label>
                    <form:input path="phone"  style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" required="required" maxlength="50" type="text"/>

                </div>
                <div class="input-field input" style="margin-bottom: 8%">
                    <form:errors path="name" element="p" cssStyle="color:red"/>
                    <form:label path="name" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createreservation.register.name"/></form:label>
                    <form:input path="name"  style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" required="required" maxlength="50" type="text"/>
                </div>
                <div style="margin-bottom: 1%">
                    <form:label path="qPeople" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Confirmreservation.people" arguments="${reservation.qPeople}"/></form:label>
                </div>
                <div style="margin-bottom: 3%">
                    <span style="font-family: Lato,sans-serif;font-size:20px"><spring:message code="Confirmreservation.date"/></span>
                </div>
                <div style="margin-bottom: 1%">
                    <form:label path="hour" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Confirmreservation.hour" arguments="${reservation.reservationHour}"/></form:label>
                </div>
                <div class="submit center">
                    <spring:message code="Button.confirm" var="label"/>
                    <spring:message code="Button.loading" var="label2"/>
                    <input type="submit" value="${label}" class="btn confirm-btn" onclick="this.form.submit(); this.disabled=true;this.value=${label2}; "/>
                </div>
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
    border-radius: 16px;
    display: flex;
    flex-wrap: wrap;
    margin: 10px;
    justify-content: center;
    align-content: center;
    flex-direction: column;
    min-height: 300px;
    height: 100%;
    min-width: 250px;
    width: 100%;
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

