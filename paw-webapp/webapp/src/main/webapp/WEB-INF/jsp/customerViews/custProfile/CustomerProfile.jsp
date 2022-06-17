<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">
    <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
    <title>Senta3</title>

</head>
<body>
<%@ include file="../../components/navbar.jsp" %>
<div class="restaurant-header" style="background-color: rgb(255, 242, 229);border-radius: 0px;">
    <div class="restaurant-info" style="margin-left: 2%;">
        <h1 class="presentation-text header-title"><spring:message code="Customer.profile"/></h1>
    </div>
    <div style="margin-right: 1.5%;">
        <a class="btn confirm-btn" href="${pageContext.request.contextPath}/logout">
            <spring:message code="Navbar.option.logout"/>
        </a>
    </div>
</div>
<div class="contentContainer">
    <div class="info" style="background-color: whitesmoke;border-radius: .8rem;padding-left: 1.5%;">
        <div class="profile-field" style="display: flex; align-items:center; width: fit-content">
            <div class="restaurant-field">
                <span class="presentation-text"><spring:message code="Customer.name"/> </span>
                <span class="text description">${customer.customerName}</span>
            </div>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" href="<c:url value="/profile/editName" />">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
        <div class="profile-field" style="display: flex; align-items:center; width: fit-content">
            <div class="restaurant-field">
                <span class="presentation-text"><spring:message code="Customer.phone"/> </span>
                <span class="text description">${customer.phone}</span>
            </div>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" href="<c:url value="/profile/editPhone" />">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
        <div class="profile-field" style="display: flex; align-items:center; width: fit-content">
            <div class="restaurant-field">
                <span class="presentation-text"><spring:message code="Customer.mail"/> </span>
                <span class="text description">${customer.mail}</span>
            </div>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" href="<c:url value="/profile/editMail" />">
                <i class="material-icons info-field">edit</i>
            </a>
        </div>
        <div class="profile-field" style="display: flex; align-items:center; width: fit-content">
            <div class="restaurant-field">
                <span class="presentation-text"><spring:message code="Customer.user"/> </span>
                <span class="text description">${username}</span>
            </div>
            <a class="waves-effect waves-light btn-floating btn-small plus-btn info-field" href="<c:url value="/profile/editUsername" />">
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
    .info{
        width: 50%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
    }
    body{
        background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }
    .presentation-text{
        font-size: clamp(1rem,1.25rem + 1vw,5rem);
    }
    .text.description{
        font-size: clamp(1rem,1.25rem + 1vw,5rem);
    }
    .profile-field:hover .info-field{
        display: block;
    }
    .profile-field:hover{
        background-color: #464242;
        color: white;
        border-radius: 16px;
    }
    .profile-field:hover .text.description{
        color: white;
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
        transition: ease-in;
    }
</style>
<script></script>