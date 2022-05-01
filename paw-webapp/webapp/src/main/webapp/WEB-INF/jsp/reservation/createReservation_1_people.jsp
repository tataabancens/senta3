<%@ page import="java.util.LinkedList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">

    <title>Sentate-Registro</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

<body>
<%@ include file="../components/navbar.jsp" %>

<div class="content">
    <c:url value="/createReservation-1" var="postPath"/>
        <div class="content-container">
            <div class="card register-card">
                <span class="main-title">Cuanta gente viene a comer?</span>

                <div class="input-field">
                    <select id="qPeople" name="qPeople">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                    </select>
                </div>
                <div class="submit center">
                    <input type="button" id="continueButton" value="Continuar" class="continue-btn" onclick="return validateSelect()"/>
                </div>
            </div>
        </div>
</div>

</body>
</html>

<style>
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

<script>
    function validateSelect()
    {
        const sel = document.getElementById('qPeople');
        window.location.href = 'createReservation-1/people=' + sel.value;
    }
</script>