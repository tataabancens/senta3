<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

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
</head>
<body>
<%@ include file="components/navbar.jsp" %>

<div class="row">

    <div class="row">
        <div class="col s3">
            <div class="card restaurant-card">
                <p>INFO DEL RESTO</p>
            </div>
            <div class="center">
                <a class="waves-effect waves-light btn reservation-btn ">Pedir comida</a>
            </div>
            <div class="center">
                <a class="waves-effect waves-light btn reservation-btn ">Pedir cuenta</a>
            </div>
        </div>





    <div class="col offset-s1 s4">
        <c:forEach var="dish" items="${dish}">
            <div class="card dish-card">
                <div class="card-content white-text">
                        <div class="block">
                            <span class="card-title title text "><c:out value="${dish.dishName}"/></span>
                        </div>
                        <div class ="block right">
                            <a class="btn-floating btn-large waves-effect waves-light plus-btn "><i class="material-icons">+</i></a>
                        </div>
                    <p class="description">I am a very simple card. I am good at containing small bits of information.
                        I am convenient because I require little markup to use effectively.</p>
                    <p class="price">$<c:out value="${dish.price}"/></p>
                </div>
            </div>
        </c:forEach>
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


    .plus-btn{
        background-color: #37A6E6;
        opacity: 57%
    }

    .plus-btn:hover{
        background-color: #37A6E6;
        opacity: 100%
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

    .price{
        font-size: 25px;
        font-weight: bold;
        color: black;
    }

    .block-parent{
        justify-content: space-between;
    }

    .block{
        display: inline-block;
    }

    .reservation-btn{
        border-radius: 16px;
        background-color: #37A6E6;
        margin-top: 5%;
        opacity: 57%;
    }

    .reservation-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }

    .center{
        justify-content: center;
    }

    .smaller{
        width: 100%;
        margin-bottom: 0;
        margin-top: 0;
    }



</style>

