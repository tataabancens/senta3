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

    <title>Sentate-Register</title>
<body>
<%@ include file="components/navbar.jsp" %>

<div class="row">
    <div class="col s3"></div>     <!-- acá va el restaurant card -->


    <c:url value="/restaurant=${restaurantId}/menu/create" var="postPath"/>
    <form:form modelAttribute="createDishForm" action="${postPath}" method="post">
        <div class="col s6 center">
            <div class="card register-card">
                <div class="card-content white-text">
                    <span class="card-title text">Crear Plato</span>
                    <div class="row">
                        <div class="input-field col s12 input">
                            <form:errors path="dishName" element="p" cssStyle="color:red"/>
                            <form:label path="dishName" class="helper-text" data-error="wrong" data-success="right">Nombre del Plato:</form:label>
                            <form:input path="dishName" type="text"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 input">
                            <form:errors path="dishDesc" element="p" cssStyle="color:red"/>
                            <form:label path="dishDesc" class="helper-text" data-error="wrong" data-success="right">Descripcion:</form:label>
                            <form:input path="dishDesc" type="text"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12 input">
                            <form:errors path="dishPrice" element="p" cssStyle="color: red"/>
                            <form:label path="dishPrice" class="helper-text" data-error="wrong" data-success="right">Precio:</form:label>
                            <form:input path="dishPrice" type="text"/>
                        </div>
                    </div>
                    <div class="col s12 center">

                        <input type="submit" value="Continuar" class="continue-btn"/>

                    </div>
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
    .text{
        color:  #707070;
    }

    .card{
        border-radius: 16px;
        display: grid;
    }

    .restaurant-card{
        width:100%;
    }

    .register-card{

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
