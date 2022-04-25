<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <!-- <link rel="stylesheet" href="home"> -->

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <title>Hello, world!</title>

</head>
<body>
<nav>
    <div class="nav-wrapper">
        <a href="${pageContext.request.contextPath}/" class="logo">Senta3</a>
        <sec:authorize access="hasRole('RESTAURANT')">
            <a href="${pageContext.request.contextPath}/restaurant=1/menu" class="logo">Inicio</a>
        </sec:authorize>
        <sec:authorize access="!hasRole('RESTAURANT')">
            <a href="#" class="logo">Restaurantes</a>
        </sec:authorize>
        <sec:authorize access="!hasRole('RESTAURANT')">
            <a href="${pageContext.request.contextPath}/" class="logo">Inicio</a>
        </sec:authorize>
        <sec:authorize access="hasRole('RESTAURANT')">
            <a href="${pageContext.request.contextPath}/restaurant=1/orders" class="logo">Ordenes</a>
        </sec:authorize>
        <sec:authorize access="hasRole('RESTAURANT')">
            <a href="${pageContext.request.contextPath}/" class="logo">Restaurante</a>
        </sec:authorize>
        <sec:authorize access="!hasRole('RESTAURANT')">
            <a href="${pageContext.request.contextPath}/login" class="logo right">Iniciar sesion</a>
        </sec:authorize>
        <sec:authorize access="hasRole('RESTAURANT')">
            <a href="${pageContext.request.contextPath}/logout" class="logo right">Cerrar sesion</a>
        </sec:authorize>
    </div>
    <!--
    <div class="nav-wrapper navbar">
        <div class="row">
            <div class="col">
                <sec:authorize access="hasRole('RESTAURANT')">
                <a href="${pageContext.request.contextPath}/restaurant=1/menu">
                    <span class="logo">Senta3</span>
                </a>
                </sec:authorize>

                <sec:authorize access="!hasRole('RESTAURANT')">
                <a href="${pageContext.request.contextPath}/">
                    <span class="logo">Senta3</span>
                </a>
                </sec:authorize>

            </div>
            <sec:authorize access="hasRole('RESTAURANT')">
                <div class="col right">
                    <a href="${pageContext.request.contextPath}/logout">
                        <p class="logo smaller">log out</p>
                    </a>
                </div>
            </sec:authorize>
            <sec:authorize access="!hasRole('RESTAURANT')">
                <div class="col right">
                    <a href="${pageContext.request.contextPath}/login">
                        <p class="logo smaller">log in</p>
                    </a>
                </div>
            </sec:authorize>

        </div>
    </div>-->
</nav>
</body>
</html>

<style>
    body{
        font-family: 'Goldplay-Bold',sans-serif;
        background: white;

    }
    .nav-wrapper{
        background-color: white;
        align-items: center;
        color: #37A6E6;
    }
    nav a{
        font-size: 18px;
        text-transform: uppercase;
        color: #37A6E6;
        text-decoration: none;
        line-height: 50px;
        margin-top: 5px;
        margin-left: 20px;
        margin-right: 20px;
        position: relative;
        z-index: 1;
        display: inline-block;
        text-align: center;
    }
    a:nth-child(1){
        width: 100px;
    }

    a:nth-child(2){
        width: 100px;
    }
    a:nth-child(3){
        width: 100px;
    }
    .logo{
        color:  #37A6E6;
        margin-left: 2%;
        font-family: "Segoe UI", Arial, sans-serif;
        font-weight: bold;
        font-style: italic;
        font-size:1.5vw;
    }


    .smaller{
        font-size: 25px;
    }
</style>
