<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <link rel="stylesheet" href="styless.css">

    <title>Hello, world!</title>
<body>
    <%@ include file="components/navbar.jsp" %>

    <div class="row">
        <div class="col s3">
            <div class="card restaurant-card">
                <div class="card-content white-text">
                    <span class="card-title text"><c:out value="${restaurant.restaurantName}"/></span>
                    <span class="text"><c:out value="${restaurant.phone}"/></span>
                </div>
            </div>
        </div>

        <c:url value="/register" var="postPath"/>
        <form:form modelAttribute="reservationForm" action="${postPath}" method="post">
        <div class="col s6">
            <div class="card register-card">
                <div class="card-content white-text">
                    <span class="card-title text">Para reservar vamos a necesitar algunos datos:</span>

                    <span class="card-title text">Email:</span>
                    <div class="row">
                        <form:errors path="mail" element="p" cssStyle="color:red"/>

                        <div class="input-field col s12 input">
                            <form:input path="mail" class="validate" />

                            <form:label path="mail" class="helper-text" data-error="wrong" data-success="right">Mail</form:label>
                        </div>
                    </div>
                    <span class="card-title text">Nombre y Apellido:</span>
                    <div class="row">
                        <div class="input-field col s12 input">
                            <input type="email" class="validate">
                            <span class="helper-text" data-error="wrong" data-success="right">Nombre y apellido</span>
                        </div>
                    </div>

                    <div class="col s12 btns">
                        <a class="waves-effect waves-light btn back-btn">Volver</a>

                        <a class="waves-effect waves-light btn continue-btn">Continuar</a>
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
    .text{
        color:  #707070;
    }

    .btns{
        display: flex;
        align-items: center;
        flex-grow: 1;
        justify-content: space-between;
        margin-left: 30%;
        margin-right: 30%;
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

</style>
