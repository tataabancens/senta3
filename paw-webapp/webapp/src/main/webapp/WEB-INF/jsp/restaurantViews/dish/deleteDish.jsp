<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
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
<%@ include file="../../components/navbar.jsp" %>


<div class="pageContainer">
    <c:url value="/restaurant=${restaurantId}/menu/edit/deleteDish=${dishId}" var="postPath"/>
    <form:form action="${postPath}" method="post">
        <div class="card">
            <span class="presentation-text"><spring:message code="Deletedish.sure" arguments="${dish.dishName}"/> </span>
            <spring:message code="Button.confirm" var="label"/>
            <input type="submit" value="${label}" class="btn confirm-btn"/>
        </div>
    </form:form>
</div>
</body>
</html>

<style>
    .pageContainer{
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .card{
        padding: 1rem;
        width: 40%;
        border-radius: .8rem;
        justify-content: center;
        align-items: center;
        flex-direction: column;
    }
    .btn.confirm-btn{
        margin-bottom: 2em;
        background-color: red;
    }

    body{
        background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }

</style>