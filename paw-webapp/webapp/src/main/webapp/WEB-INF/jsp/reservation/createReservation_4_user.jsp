<%@ page import="java.util.LinkedList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <link rel="stylesheet" href="styless.css">

    <title>Sentate-Registro</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
<body>
<%@ include file="../components/navbar.jsp" %>

<div class="content">
    <c:url value="/createReservation-1/people=${qPeople}/hour=${hour}" var="postPath"/>

    <form:form modelAttribute="reservationForm" action="${postPath}" method="post">
        <div class="content-container">
            <div class="card register-card">
                <span class="main-title">Para reservar vamos a necesitar algunos datos:</span>
                <div class="input-field input">
                    <form:errors path="mail" element="p" cssStyle="color:red"/>
                    <form:label path="mail" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right">Mail:</form:label>
                    <form:input path="mail"  required="required" maxlength="50" type="text"/>
                </div>
                <div class="input-field input">
                    <form:errors path="phone" element="p" cssStyle="color:red"/>
                    <form:label path="phone" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right">Telefono:</form:label>
                    <form:input path="phone" required="required" maxlength="13" type="text"/>
                </div>
                <div class="input-field input">
                    <form:errors path="name" element="p" cssStyle="color: red"/>
                    <form:label path="name" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right">Nombre y Apellido:</form:label>
                    <form:input path="name" required="required" maxlength="50" type="text"/>
                </div>
                <div>
                    <form:label path="qPeople" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right">Comensales: <c:out value="${people}"/></form:label>
                </div>
                <div>
                    <form:label path="hour" style="font-size:20px; font-family: Lato,sans-serif; color:#463f3f;" class="helper-text" data-error="wrong" data-success="right">Hora: <c:out value="${hour}"/>hs</form:label>
                </div>
                <div class="submit center">
                    <input type="submit" value="Confirmar reserva!" class="continue-btn"/>
                </div>
            </div>
        </div>
    </form:form>
</div>

</body>
</html>

<style>

    body{
        background-color: #F0F0F0;
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
