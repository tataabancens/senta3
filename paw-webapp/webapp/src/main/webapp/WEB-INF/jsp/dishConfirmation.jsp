<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<div class="row">
    <%@ include file="components/navbar.jsp" %>
</div>

    <div class="col s4 offset-s3 card-margin">
        <div class="card dish-card">
            <div class="card-content white-text">
                <span class="card-title text price center">Creaste el plato!</span>
                <span class="card-title text price center">Agregale una foto!</span>

                <div class="row">
                    <div class="input-field col s12 input">
                        <c:url value="/restaurant=${restaurantId}/menu/dish=${dishId}/edit-photo" var="postPath"/>
                        <form action="${postPath}" method="post" enctype="multipart/form-data">
                            <input type="file" name="photo"/>
                            <div class="col s12 center">
                                <input type="submit" value="Cargar" class="continue-btn"/>
                            </div>
                        </form>
                    </div>
                    </div>
                </div>
                <div class="row margin-0">
                    <div class="col s12 center">
                        <a class="waves-effect waves-light btn reservation-btn green " href="${pageContext.request.contextPath}/restaurant=${restaurantId}/menu">Volver al inicio</a>
                    </div>
                </div>

    <div class="page-container">
            <div class="card confirm-card">
                <div class="card-content white-text">
                    <span class="main-title text center">Creaste el plato!</span>
                    <div class="row margin-0">
                        <div class="col s12 center">
                            <a class="waves-effect waves-light btn reservation-btn green " href="${pageContext.request.contextPath}/restaurant=${restaurantId}/menu">Volver al inicio</a>
                        </div>
                    </div>
            </div>
        </div>
    </div>


</body>
</html>

<style>

    body{
        background-color: #F0F0F0;
    }


    .card{
        border-radius: 16px;
        display: grid;
    }


    .reservation-btn{
        display: flex;
        border-radius: 16px;
        background-color: #37A6E6;
        margin-top: 5%;
        opacity: 57%;
        width: 35%;
        min-width: 10%;
        font-size: 1vw;
        text-align: center;
    }

    .reservation-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }

    .center{
        justify-content: center;
    }


    .page-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
    }

    .confirm-card{
        display:flex;
        justify-content: center;
        width: 40%;
        max-width: 60%;
        margin-left: 5%;
        margin-right: 5%;
    }



</style>