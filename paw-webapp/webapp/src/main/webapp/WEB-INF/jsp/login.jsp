<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<c:url value="/login" var="loginUrl" />
<%@ include file="components/navbar.jsp" %>

<div class="row">


    <div class="col s4 offset-s4">
        <div class="card dish-card">
            <div class="card-content white-text">
                <form action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
                    <div>
                        <label for="username">Username: </label>
                        <input id="username" name="username" type="text"/>
                    </div>
                    <div>
                        <label for="password">Password: </label>
                        <input id="password" name="password" type="password"/>
                    </div>
                    <div class="rememberme">
                        <label>
                            <input type="checkbox" class="filled-in blue" name="rememberme"/>
                            <span>Remember me</span>
                        </label>
                    </div>
                    <div class="row center smaller">
                        <input class="submit-btn" type="submit" value="Login!"/>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>

</body>
</html>


<style>

    body{
        background-color: #F0F0F0;
    }
    .text{
        color:  #707070
    }


    .card{
        border-radius: 16px;
        display: grid;
    }

    .restaurant-card{
    }

    .dish-card{
        width: 100%;
    }

    .description{
        color:  #707070;
        font-size: 17px;
    }

    .title2{
        justify-content: center;
        color:  #707070;
        font-size: 20px;
    }

    .price{
        font-size: 25px;
        font-weight: bold;
        color: black;
    }

    .submit-btn{
        border-radius: 10px;
        background-color: #37A6E6;
        margin-top: 5%;
        opacity: 57%;
        padding: 2%;
        color: black;

    }

    .submit-btn:hover{
        background-color: #37A6E6;
        color: black;
        opacity: 100%;
    }

    .rememberme{
        margin-top: 20px;
    }

    .blue{
        background-color: #37A6E6;
    }

    .smaller{
        width: 100%;
        margin-bottom: 0;
        margin-top: 0;
    }

</style>