<%@ page import="java.util.LinkedList" %>
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
    <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">

    <title>Reserva</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

<body>
<%@ include file="../../components/navbar.jsp" %>

<div class="content">
    <div class="content-container">
        <div class="card register-card">
            <c:if test="${hours.size() > 0}">
                <span class="presentation-text"><spring:message code="Createreservation.today"/></span>
                <span class="presentation-text"><spring:message code="Createreservation.hour.title"/></span>
                <c:url value="/createReservation-2/${reservationId}" var="postPath"/>
                <form:form modelAttribute="hourForm" action="${postPath}" method="post">
                    <div class="input-field">
                        <form:select  path="number">
                            <form:options items="${hours}"></form:options>
                        </form:select>
                    </div>
                    <spring:message code="Button.continue" var="label"/>
                    <input type="submit" value="${label}" class="btn confirm-btn center"/>
                </form:form>
            </c:if>
            <c:if test="${hours.size() == 0}">
                <span class="presentation-text"><spring:message code="Createreservation.hour.error"/></span>
                <a class="waves-effect waves-light btn confirm-btn text description center" href="<c:url value="/"/>"><spring:message code="Button.back"/></a>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>

<style>

    .card{
        border-radius: 16px;
        display: flex;
        flex-wrap: wrap;
        margin: 10px;
        justify-content: center;
        align-content: center;
        flex-direction: column;
        min-height: 300px;
        height: 100%;
        min-width: 250px;
        width: 100%;
        padding: 20px;
    }

    .content{
        display: flex;
        justify-content: center;
        padding: 20px;
        align-content: center;
    }
    span{
        font-family: "Segoe UI", Lato, sans-serif;
        font-size: 23px;
    }
    input{
        font-family: "Segoe UI", Lato, sans-serif;
        font-size: 20px;
    }
    select{
        display: flex;
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
<script>
    function validateSelect()
    {
        const sel = document.getElementById('hourSelect');
        window.location.href = 'people=' + <c:out value="${people}"/> + "/hour=" + sel.value;
    }
</script>
