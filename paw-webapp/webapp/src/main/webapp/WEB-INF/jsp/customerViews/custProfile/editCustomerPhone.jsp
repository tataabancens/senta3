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

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">

    <title>Senta3</title>
<body>
<%@ include file="../../components/navbar.jsp" %>

<div class="form-container">
    <c:url value="/profile/editPhone" var="postPath"/>
    <form:form modelAttribute="editPhoneForm" action="${postPath}" method="post">
        <div class="card card-content">
            <span class="presentation-text" style="margin-bottom: 5%;"><spring:message code="Customer.edit.phone"/></span>
            <div class="disName">
                <form:errors path="phone" element="p" cssStyle="color:red"/>
                <form:input path="phone" type="text"/>
            </div>
            <div class="submit center">
                <spring:message code="Button.confirm" var="label"/>
                <input type="submit" value="${label}" style="margin-top: 10%;" class="btn confirm-btn"/>
            </div>
        </div>
    </form:form>
</div>
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
    form{
        min-width: 30%;
    }
    .container img{display: block}

    .container:hover img{
        filter: blur(1.5px);
    }

    img{
        border-radius: 16px;
        width: 100%;
        height: 100%;
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
        min-height: 20%;
        min-width: 100%;
    }

</style>
