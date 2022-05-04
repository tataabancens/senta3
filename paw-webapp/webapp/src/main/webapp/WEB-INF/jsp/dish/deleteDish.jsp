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

    <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="../components/navbar.jsp" %>

<div class="row">
    <c:url value="/restaurant=${restaurantId}/menu/edit/deleteDish=${dishId}" var="postPath"/>
    <form:form action="${postPath}" method="post">
    <div class="col s4 offset-s3 card-margin">
        <div class="card dish-card">
            <div class="card-content white-text">
                <span class="card-title text price center">Estás seguro que desea borrar el plato ${dish.dishName}?</span>
                <div class="row margin-0">
                    <div class="col s12 center">
                        <input type="submit" value="Confirmar" class="continue-btn red"/>
                    </div>
                </div>
            </div>

        </div>
    </div>
    </form:form>
</div>
</div>
</body>
</html>

<style>

    .text{
        color:  #707070
    }

    .summary{
        margin-top: 20px;
    }


    .card{
        border-radius: 16px;
        display: grid;
    }

    .restaurant-card{
    }

    

    .center{
        justify-content: center;
    }

    .card-margin{
        margin-top: 10%;
    }

    .smaller{
        width: 100%;
        margin-bottom: 0;
        margin-top: 0;
    }

    .already-reserved-btn{
    }

    .continue-btn{
        font-family: "Goldplay", sans-serif;
        border-radius: 10px;
        background-color: #37A6E6;
        margin-top: 5%;
        opacity: 57%;
        padding: 2%;
        color: white;
    }
    .continue-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }

</style>