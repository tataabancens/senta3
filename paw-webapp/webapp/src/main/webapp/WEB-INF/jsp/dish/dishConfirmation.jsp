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
    <%@ include file="../components/navbar.jsp" %>
</div>

    <!--<div class="col s4 offset-s3 card-margin">
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
            <img src="/resources/images/${imageId}" alt="La foto del plato">
                <div class="row margin-0">
                    <div class="col s12 center">
                        <a class="waves-effect waves-light btn confirm-btn green " href="${pageContext.request.contextPath}/restaurant=${restaurantId}/menu">Volver al inicio</a>
                    </div>
                </div>-->

    <div class="page-container">
            <div class="card confirm-card">
                <span class="main-title text center">Agrega una foto!</span>
                <div class="img-visualizer">
                    <div class="card visualizer">
                        <img src="${pageContext.request.contextPath}/resources_/images/${imageId}" alt="La foto del plato">
                    </div>
                </div>
                <c:url value="/restaurant=${restaurantId}/menu/dish=${dishId}/edit-photo" var="postPath"/>
                <form action="${postPath}" method="post" enctype="multipart/form-data">
                    <div class="img-row">
                        <input type="file" name="photo"/>
                        <div class="col s12 center">
                            <input type="submit" value="Cargar" class="continue-btn"/>
                        </div>
                    </div>
                </form>
                <div class="btn-row">
                    <a class="waves-effect waves-light btn confirm-btn green " href="${pageContext.request.contextPath}/restaurant=${restaurantId}/menu">Confirmar</a>
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
    }
    .visualizer{
        background-color:#37A6E6;
        padding: 8px;
    }
    img{
        border-radius: 16px;
    }
    .confirm-card{
        display: flex;
        flex-direction: column;
        align-content: center;
        padding: 20px;
        justify-content: center;
    }
    .img-visualizer{
        width: 100%;
        display: flex;
        margin: 20px;
        justify-content: center;
    }
    .img-row{
        width: 100%;
        display: flex;
        margin: 20px;
        justify-content: center;
    }
    .btn-row{
        width: 100%;
        display: flex;
        margin: 20px;
        justify-content: center;
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