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
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">
    <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <title>Perfil</title>

</head>
<body>
<%@ include file="components/navbar.jsp" %>
<div class="header">
    <h1 class="presentation-text header-title">Perfil</h1>
</div>
<div class="contentContainer">
    <div class="info">
        <sec:authorize access="hasRole('RESTAURANT')">
        <div class="restaurant-field icon-row">
            <div>
                <a class="waves-effect waves-light btn-floating btn-small plus-btn tables-and-hours" href="editTables">
                    <i class="material-icons table-and-hours">edit</i>
                </a>
                <span class="presentation-text">Mesas: </span>
                <span class="text">${restaurant.totalChairs}</span>
            </div>
            <div>
                <span class="presentation-text">Hora de apertura: </span>
                <span class="text">${restaurant.openHour}</span>
            </div>
            <div>
                <span class="presentation-text">Hora de cierre: </span>
                <span class="text">${restaurant.closeHour}</span>
            </div>
        </div>
        <div class="restaurant-field">
            <span class="presentation-text">Nombre del restaurante: </span>
            <span class="text">${restaurant.restaurantName}</span>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" href="editName">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
        <div class="restaurant-field">
            <span class="presentation-text">Telefono: </span>
            <span class="text">${restaurant.phone}</span>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" href="editPhone">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
        <div class="restaurant-field">
            <span class="presentation-text">Email: </span>
            <span class="text">${restaurant.mail}</span>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" href="editMail">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
        </sec:authorize>
        <div class="restaurant-field">
            <span class="presentation-text">Nombre de usuario: </span>
            <span class="text">${username}</span>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" href="editTables">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
        <div class="restaurant-field">
            <span class="presentation-text">Contraseña: </span>
            <span class="text">restaurant no tiene el getter</span>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" href="editTables">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
    </div>
    <sec:authorize access="hasRole('RESTAURANT')">
    <div class="imageContainer">
        <img src="${pageContext.request.contextPath}/resources/images/masterchef.jpg" alt="imagen de restarante">
    </div>
    </sec:authorize>
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
        color: white;
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
    .text{
        color: white;
    }
    .restaurant-field{
        transition: 0.7s;
    }
    .restaurant-field.icon-row .tables-and-hours{
        display: none;
        transition: 0.7s;
    }
    .restaurant-field:hover{
        background-color: #332f2f;
        border-radius: 16px;
    }
    .restaurant-field.icon-row:hover .tables-and-hours{
        display: block;
        position: relative;
        left: 90%;
        bottom: 90%;
        background-color: white;
    }
    .tables-and-hours i{
        color: black;
    }
    .info-field{
        display:none;
    }
    a .info-field{
        background-color: white;
    }
    .info-field i{
        color: black;
    }
    .restaurant-field:hover .info-field{
        display: block;
    }
</style>
<script></script>