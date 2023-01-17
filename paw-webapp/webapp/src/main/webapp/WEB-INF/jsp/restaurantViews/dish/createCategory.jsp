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
    <c:if test="${category != null}">
        <c:url value="/restaurant=${restaurantId}/category=${category}/edit" var="postPath"/>
    </c:if>
    <c:if test="${category == null}">
        <c:url value="/restaurant=${restaurantId}/category/create" var="postPath"/>
    </c:if>
    <form:form modelAttribute="createCategoryForm" action="${postPath}" method="post">
    <div class="card card-content">
        <span class="presentation-text"><spring:message code="Create.category.title"/></span>
        <div style="margin: 30px">
            <div>
                <form:errors path="categoryName" element="p" cssStyle="color:red"/>
                <form:label path="categoryName" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Create.category.name"/></form:label>
                <form:input path="categoryName" required="required" maxlength="20" type="text"/>
            </div>
            <div class="col s12 center" style="margin-top: 10%;">
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
        min-height: 3px;
        height: fit-content;
        min-width: 400px;
        width: 500px;
        max-width: 600px;
    }
    body{
        background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }
</style>
