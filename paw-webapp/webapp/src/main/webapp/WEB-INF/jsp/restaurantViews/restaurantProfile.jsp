<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: gonza
  Date: 01/05/2022
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">
    <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <title>Senta3</title>

</head>
<body>
<%@ include file="../components/navbar.jsp" %>
<div class="restaurant-header" style="background-color: rgb(255, 242, 229);border-radius: 0px;">
    <div class="restaurant-info" style="margin-left: 2%;">
        <h1 class="presentation-text header-title"><spring:message code="Restaurant.profile"/></h1>
    </div>
</div>
<div class="contentContainer">
    <div class="info">
        <div class="profile-field icon-row" style="display: flex;flex-direction: column; align-items:flex-start; width: fit-content">
            <div>
                <a class="waves-effect waves-light btn-floating btn-small plus-btn tables-and-hours" href="<c:url value="/restaurant=${restaurantId}/editTables"/>">
                    <i class="material-icons table-and-hours">edit</i>
                </a>
                <span class="presentation-text"><spring:message code="Restaurant.tables.qty"/> </span>
                <span class="text description">${restaurant.totalChairs}</span>
            </div>
            <div>
                <span class="presentation-text"><spring:message code="Restaurant.hour.open"/></span>
                <span class="text description">${restaurant.openHour}:00</span>
            </div>
            <div>
                <span class="presentation-text"><spring:message code="Restaurant.hour.close"/></span>
                <span class="text description">${restaurant.closeHour}:00</span>
            </div>
        </div>
        <div class="profile-field" style="display: flex; align-items:center; width: fit-content">
            <div class="restaurant-field">
                <span class="presentation-text"><spring:message code="Restaurant.name"/> </span>
                <span class="text description">${restaurant.restaurantName}</span>
            </div>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" style="margin-left: 10px;" href="<c:url value="/restaurant=${restaurantId}/editName"/>">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
        <div class="profile-field" style="display: flex; align-items:center; width: fit-content">
            <div class="restaurant-field">
                <span class="presentation-text"><spring:message code="Restaurant.phone"/> </span>
                <span class="text description">${restaurant.phone}</span>
            </div>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" style="margin-left: 10px;" href="<c:url value="/restaurant=${restaurantId}/editPhone"/>">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
        <div class="profile-field" style="display: flex; align-items:center; width: fit-content">
            <div class="restaurant-field">
                <span class="presentation-text"><spring:message code="Restaurant.mail"/> </span>
                <span class="text description">${restaurant.mail}</span>
            </div>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" style="margin-left: 10px;" href="<c:url value="/restaurant=${restaurantId}/editMail"/>">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
        <div class="profile-field" style="display: flex; align-items:center; width: fit-content">
            <div class="restaurant-field">
                <span class="presentation-text"><spring:message code="Register.user"/> </span>
                <span class="text description">${username}</span>
            </div>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" style="margin-left: 10px;" href="<c:url value="/profile/editUsername"/>">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
    </div>
</div>
</body>
</html>
<style>
    .contentContainer{
        width: 100%;
        height: 80%;
        padding: 1.5%;
        display: flex;
    }
    .restaurant-field{
        width: fit-content;
    }
    .presentation-text{
        font-size: 2rem;
    }
    .info{
        width: 50%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
    }
    .imageContainer{
        display: flex;
        justify-content: center;
        width: 50%;
        height: 70%;
    }
    img{
        border-radius: 16px;
        width: 50%;
        height: 100%;
    }
    .restaurant-field .text.description{
        transition: 0.7s;
    }
    .profile-field{
        transition: 0.7s;
    }
    .profile-field.icon-row .tables-and-hours{
        display: none;
        transition: 0.7s;
    }

    .text.description{
        font-size: 2rem;
    }
    .profile-field:hover{
        background-color: #464242;
        color: white;
        border-radius: 16px;
    }
    .profile-field:hover .text.description{
        color: white;
    }
    .profile-field:hover .info-field{
        display: block;
    }

    .profile-field.icon-row:hover .tables-and-hours{
        display: block;
        position: relative;
        left: 240;
        bottom: 0;
        background-color: white;
    }
    .tables-and-hours i{
        color: black;
    }
    .info-field{
        display: none;
    }
    a .info-field{
        background-color: white;
    }
    .info-field i{
        position: absolute;
        top: 0;
        right: 0;
        color: black;
    }
    .restaurant-field:hover .info-field{
        display: block;
    }
</style>
<script></script>