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

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

<body>
<%@ include file="../../components/navbar.jsp" %>

<div style="display: flex;margin-top: 5%;justify-content: center">
    <c:if test="${hours.size() > 0}">
        <div class="card register-card">
            <span class="presentation-text"><spring:message code="Createreservation.hour.title"/></span>
            <span class="presentation-text"><spring:message code="Createreservation.disclaimer"/></span>
            <c:url value="/createReservation-3/${reservationSecurityCode}" var="postPath"/>
            <form:form modelAttribute="hourForm" action="${postPath}" method="post">
                <div class="input-field">
                    <form:select  path="number">
                        <c:forEach var="item" items="${hours}">
                            <form:option value="${item}" label="${item}:00"></form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <spring:message code="Button.continue" var="label"/>
                <input type="submit" value="${label}" class="btn confirm-btn center"/>
            </form:form>
        </div>
    </c:if>
    <c:if test="${hours.size() == 0}">
        <div class="card register-card" style="justify-content: flex-start;align-items: center;min-height: 200px;">
            <span class="presentation-text" style="margin-bottom: 13%;"><spring:message code="Createreservation.hour.error"/></span>
            <a class="waves-effect waves-light btn confirm-btn text description center" style="color: white;" href="<c:url value="/createReservation-2/${reservationSecurityCode}"/>">
                <spring:message code="Button.back"/>
            </a>
        </div>
    </c:if>
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
    .card{
        border-radius: .8rem;
        display: flex;
        flex-wrap: wrap;
        margin: 10px;
        justify-content: center;
        align-content: center;
        flex-direction: column;
        min-height: 300px;
        min-width: 500px;
        padding: 20px;
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
