<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "java.io.*,java.util.*" %>
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

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<div class="row">
    <%@ include file="../../components/navbar.jsp" %>
</div>
<div class="page-container">
    <div class="confirm-card">
        <div class="card">
            <div class="card-content white-text center">
                <span class="presentation-text" style="color: black;">Tu orden fue procesada con exito</span>
                <div class="with-margin">
                    <i class="large material-icons" style="color: forestgreen">check_circle</i>
                </div>
                <c:if test="${reservation.reservationStatus.name != 'SEATED'}">
                    <div>
                        <span class="text description">Recuerda que estar presente en el restaurant para que la cocina comienze a preparar tu pedido</span>
                    </div>
                </c:if>
                <div>
                    <a class="waves-effect waves-light btn confirm-btn"  style="margin-top: 5%;" href="<c:url value="/menu?reservationId=${reservation.id}"/>">Volver al menu</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<style>
    i{
        color: #171616;
        margin-right: 25px;
    }
    body{
        background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }

    .card{
        border-radius: 16px;
        display: grid;
    }

    .text.description{
        font-size: 1.4em;
    }
    .center{
        justify-content: center;
    }

    .with-margin{
        margin-top: 10%;
        margin-bottom: 10%;
    }
    .btn-row{
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        margin-top: 5%;
    }
    .btn.confirm-btn{
        margin: 2%;
    }
    .page-container {
        display: flex;
        width: 100%;
        flex-wrap: wrap;
        justify-content: center;
        padding-top: 2%;
    }
    .confirm-card{
        display:flex;
        justify-content: center;
        min-width: 25%;
        width: 30%;
        max-width: 100%;
        margin-left: 5%;
        margin-right: 5%;
    }
</style>
