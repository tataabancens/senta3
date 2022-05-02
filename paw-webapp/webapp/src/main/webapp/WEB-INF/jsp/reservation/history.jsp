<%--
  Created by IntelliJ IDEA.
  User: gonza
  Date: 01/05/2022
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <title>Historial</title>
</head>
<body>
<%@ include file="../components/navbar.jsp" %>
<div class="header">
    <h1 class="presentation-text header-title">Historial</h1>
</div>
<div class="contentContainer">
    <div class="points">
        <div class="reservations-header">
            <h3 class="presentation-text">Puntaje:</h3>
        </div>
        <div class="progress-bar" style="--width:${progressBarNumber}"></div>
        <span class="presentation-text">${progressBarNumber}%</span>
    </div>
    <div class="reservations">
        <div class="reservations-header">
            <h3 class="presentation-text">Ultimas reservas:</h3>
        </div>
        <div class="reservationList">
            <c:forEach var="item" items="${listToDisplay}">
                <div class="card horizontal">
                    <div class="card-image">
                        <img src="<c:url value="/resources_/images/${item.imageId}"/>">
                    </div>
                    <div class="card-stacked">
                        <div class="card-content">
                            <p>${item.dishDescription}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>

<style>
    .contentContainer{
        display: flex;
        flex-direction: column;
        padding: 0 2% 0 2%;
    }
    .points{
        width: 100%;
        height: 30%;
    }
    .reservations{
        width: 100%;
        height: 70%;
    }
    .reservationList{
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        justify-content: center;
    }
    .card.horizontal{
        margin: 4%;
        min-width: 20%;
        max-width: 30%;
        border-radius: 10px;
        font-family: "Segoe UI", Lato, sans-serif;
    }
    .reservations-header{
        justify-content: center;
    }
    .presentation-text{
        color: white;
    }
    .progress-bar{
        position: relative;
        width: 40%;
        height: 3em;
        background-color: white;
        border-radius: 1.5em;
        color: white;
    }
    .progress-bar::before{
        content: attr(data-label);
        display: flex;
        align-items: center;
        position: absolute;
        left: .5em;
        top: .5em;
        bottom: .5em;
        width: calc(var(--width,0) * 1%);
        min-width: 2rem;
        max-width: calc(100% - 1em);
        background-color: green;
        border-radius: 1em;
        padding: 1em;
    }
</style>
<script type="text/javascript">


</script>
