<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<c:url value="/login" var="loginUrl" />
<%@ include file="components/navbar.jsp" %>

<div class="form-container">
        <form action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
            <div class="card card-content">
                <div class="title">
                    <span class="main-title">Iniciar sesion</span>
                </div>
            <div>
                <label for="username">Usuario: </label>
                <input id="username" name="username" type="text"/>
            </div>
            <div>
                <label for="password">Contrasena: </label>
                <input id="password" name="password" type="password"/>
            </div>
            <div class="rememberme">
                <label>
                    <input type="checkbox" class="filled-in blue" name="rememberme"/>
                    <span>Permanecer conectado</span>
                </label>
            </div>
            <div class="row center smaller">
                <input class="confirm-btn center" type="submit" value="Ingresar"/>
            </div>
            </div>
        </form>
</div>
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
        padding: 20px;
        display: flex;
    }
    .card.card-content{
        justify-content: center;
        flex-direction: column;
        align-content: center;
        font-family: "Segoe UI", Lato, sans-serif;
        min-height: 500px;
        min-width: 400px;
        max-width: 35%;
    }
    .submit-btn{
        border-radius: 10px;
        background-color: #37A6E6;
        margin-top: 5%;
        opacity: 57%;
        padding: 2%;
        color: white;

    }

    .submit-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }

    .rememberme{
        margin-top: 20px;
    }

    .smaller{
        width: 100%;
        margin-bottom: 0;
        margin-top: 0;
    }

</style>