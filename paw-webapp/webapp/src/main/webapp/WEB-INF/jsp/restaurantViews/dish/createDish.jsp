<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->

    <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">

    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
</head>
<body>
<%@ include file="../../components/navbar.jsp" %>

<div class="form-container">
    <c:url value="/restaurant=${restaurantId}/menu/create" var="postPath"/>
    <form:form modelAttribute="createDishForm" action="${postPath}" method="post">
        <div class="card card-content">
                <span class="presentation-text"><spring:message code="Createdish.title"/></span>
            <div style="margin: 30px">
                <div class="disName">
                    <form:errors path="dishName" element="p" cssStyle="color:red"/>
                    <form:label path="dishName" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createdish.form.name"/></form:label>
                    <form:input path="dishName" required="required" type="text"/>
                </div>
                <div class="dishPrice">
                    <form:errors path="dishPrice" element="p" cssStyle="color: red"/>
                    <form:label path="dishPrice" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createdish.form.price"/></form:label>
                    <form:input path="dishPrice" required="required" type="text"/>
                </div>
                <div class="dishDesc">
                    <form:errors path="dishDesc" element="p" cssStyle="color:red"/>
                    <form:label path="dishDesc" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createdish.form.description"/></form:label>
                    <form:textarea path="dishDesc" type="text" required="required" style="height:8em;"/>
                </div>
            </div>
            <div class="input-field">
                <form:select  path="category">
                    <form:options items="${categories}"></form:options>
                    <form:option  value="${category}" selected="${category}"></form:option>
                </form:select>
            </div>
            <div class="col s12 center">
                <spring:message code="Button.continue" var="label"/>
                <input type="submit" value="${label}" class="btn confirm-btn"/>

            </div>
                </div>
            </div>
        </div>
    </form:form>
</body>
</html>

<style>
    body{
        background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }
    .form-container{
        display: flex;
        padding-top: 30px;
        justify-content: center;
    }
    .card{
        border-radius: 16px;
        margin: 20px;
        padding: 20px;
        display: flex;
    }
    .card.card-content{
        justify-content: center;
        flex-direction: column;
        align-content: center;
        font-family: "Segoe UI", Lato, sans-serif;
        min-height: 500px;
        height: fit-content;
        min-width: 400px;
        width: 500px;
        max-width: 600px;
    }

    select{
        display: flex;
    }

    .image-upload{
        margin: 20px;
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
