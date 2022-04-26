<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
    <div class="row">
        <%@ include file="components/navbar.jsp" %>
    </div>

    <div class="content-container">

        <div class="restaurant-card">
            <div class="card">
                <div class="card-content white-text">
                    <span class="main-title text"><c:out value="${restaurant.restaurantName}"/></span>
                    <span class="text title2"><c:out value="${restaurant.phone}"/></span>
                </div>
                <div>
                    <div class="presentation-text title restaurant-title">
                        <span><c:out value="${restaurant.restaurantName}"/></span>
                    </div>
                    <div class="presentation-text restaurant-description">
                        <span>Telefono: </span>
                        <span><c:out value="${restaurant.phone}"/></span>
                    </div>
                </div>
            </div>
        </div>

        <div class="confirm-card card">
            <div class="card-content white-text">
                <span class="main-title text center">Estas por cancelar tu reserva</span>
                    <div class="center-btn">
                        <c:url value="${pageContext.request.contextPath}/reservation-cancel?reservationId=${reservationId}" var="postUrl"/>
                        <form:form action="${postUrl}" method="post">
                            <input type="submit" value="Confirmar" class="waves-effect waves-light btn reservation-btn red center">
                        </form:form>
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
    .content-container{
        padding-left: 20px;
        padding-right: 20px;
        display: flex;
        flex-direction: column;
    }
    .restaurant-header{
        background: rgb(55,166,230);
        background: linear-gradient(70deg, rgba(55,166,230,1) 7%, rgba(240,240,240,1) 88%);
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        min-height: 150px;
        max-height: 300px;
        border-radius: 20px;
        align-items: center;
        padding: 15px;
    }
    .restaurant-info{
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: flex-start;
        min-height: 150px;
        max-height: 300px;
    }
    i{
        color: white;
        margin-right: 25px;
    }
    .presentation-text.restaurant-title{
        font-size: 30px;
        color:white;
    }
    .presentation-text.restaurant-description{
        color: white;
        font-size: 21px;
    }
    .card{
        border-radius: 16px;
        margin: 20px;
        padding: 20px;
        display: flex;
    }


    .reservation-btn{
        display: flex;
        border-radius: 16px;
        background-color: #37A6E6;
        margin-top: 5%;
        opacity: 57%;
        width: 35%;
        min-width: 10%;
        font-size: 1vw;
        text-align: center;
    }

    .reservation-btn:hover{
        background-color: #37A6E6;
        color: white;
        opacity: 100%;
    }



    .page-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
    }


    .confirm-card{
        display:flex;
        justify-content: center;
        width: 40%;
        max-width: 60%;
        margin-left: 5%;
        margin-right: 5%;
    }



</style>
