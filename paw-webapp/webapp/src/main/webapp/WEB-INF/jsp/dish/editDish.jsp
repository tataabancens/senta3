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

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">

    <title>Editar Plato</title>
<body>
    <%@ include file="../components/navbar.jsp" %>

    <div class="form-container">
        <c:url value="/restaurant=${restaurantId}/menu/edit/dishId=${dishId}" var="postPath"/>
        <form:form modelAttribute="editDishForm" action="${postPath}" method="post">
            <div class="card card-content">
                    <span class="main-title">Editar Plato</span>
                    <span class="title2"><c:out value="${dish.dishName}"/></span>
                <div class="disName">
                    <form:errors path="dishName" element="p" cssStyle="color:red"/>
                    <form:label path="dishName" class="helper-text" data-error="wrong" data-success="right">Nombre del Plato:</form:label>
                    <form:input path="dishName" type="text"/>
                </div>
                <div class="dishDesc">
                    <form:errors path="dishDesc" element="p" cssStyle="color:red"/>
                    <form:label path="dishDesc" class="helper-text" data-error="wrong" data-success="right">Descripcion:</form:label>
                    <form:input path="dishDesc" type="text"/>
                </div>
                <div class="dishPrice">
                    <form:errors path="dishPrice" element="p" cssStyle="color: red"/>
                    <form:label path="dishPrice" class="helper-text" data-error="wrong" data-success="right">Precio:</form:label>
                    <form:input path="dishPrice" type="text"/>
                </div>
                <div class="submit center">
                    <input type="submit" value="Confirmar" class="continue-btn"/>
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
    .form-container{
        display: flex;
        padding-top: 30px;
        justify-content: center;
    }

    .card{
        border-radius: 16px;
        padding: 20px;
        display: flex;
    }
    .card.card-content{
        justify-content: center;
        flex-direction: column;
        align-content: center;
        font-family: "Segoe UI", Lato, sans-serif;
        min-height: 500px;
        height: 600px;
        max-height: 800px;
        min-width: 400px;
        width: 500px;
        max-width: 600px;
    }

    .card-description.text{
        font-family: "Goldplay", sans-serif;
        font-size: 25px;
        margin-bottom: 15px;
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
