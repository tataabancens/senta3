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
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<%@ include file="components/navbar.jsp" %>

<div class="page-container">
    <div class="card restaurant-card">
        <div class="card-content white-text">
            <span class="text center main-title"><c:out value="${restaurant.restaurantName}"/></span>
            <span class="text center title2"><c:out value="${restaurant.phone}"/></span>
        </div>
    </div>

    <div class="card confirm-card">
        <div class="card-content wider-content center">
            <div class="center">
                <span class="title2 text center">Resumen de tu pedido:</span>
            </div>
            <div class="summary">
                <div class="titles">
                    <div class="dishname">
                        <span class="title2 text">Plato</span>
                    </div>
                    <div>
                        <span class="title2 text">Cantidad</span>
                    </div>
                    <div>
                        <span class="title2 text">Precio x U</span>
                    </div>
                    <div>
                        <span class="title2 text">Total</span>
                    </div>
                </div>
                <c:forEach var="orderItem" items="${orderItems}">
                    <div class="titles">
                        <div class="dishname-div">
                            <span class="items-title text dishname"><c:out value="${orderItem.dishName}"/></span>
                        </div>
                        <div>
                            <span class="items-title text"><c:out value="${orderItem.quantity}"/></span>
                        </div>
                        <div>
                            <span class="items-title text">$<c:out value="${orderItem.unitPrice}"/></span>
                        </div>
                        <div>
                            <span class="items-title text"><c:out value="${orderItem.unitPrice * orderItem.quantity}"/></span>
                        </div>
                    </div>
                </c:forEach>

                <hr/>

                <div class="titles">
                    <div >
                        <p class="price">Total</p>
                    </div>
                    <div>
                        <p class="price right "><c:out value="${total}"/></p>
                    </div>
                </div>

                <div>
                    <a class="waves-effect waves-light btn reservation-btn already-reserved-btn" href="menu?reservationId=${reservationId}">Volver</a>

                    <a class="waves-effect waves-light btn reservation-btn already-reserved-btn green" href="">Continuar</a>
                </div>

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

    .summary{
        margin-top: 20px;
        width: 100%;
    }


    .card{
        border-radius: 16px;
    }

    .restaurant-card{
        display:flex;
        margin-left: 5%;
        width: 20%;
        max-width: 40%;
        margin-right: 5%;
        height: 20%;
    }


    .items-title{
        color:  #707070;
        font-size: 1.25vw;
        margin-left: 20%;
    }

    .title2{
        justify-content: center;
        color:  #707070;
        font-size: 1.5vw;
    }

    .price{
        font-size: 2vw;
        font-weight: bold;
        color: black;
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

    .center{
        justify-content: center;
    }

    .with-margin{
        margin-top: 10%;
        margin-bottom: 10%;
    }

    .page-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
    }

    .dishname{
        width: 120px;
        display: block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .titles{
        display: flex;
        justify-content: space-between;
        margin-right: 10px;
    }

    .confirm-card{
        display:flex;
        justify-content: center;
        width: 40%;
        max-width: 60%;
        margin-left: 5%;
        margin-right: 5%;
    }

    .wider-content{
        width: 100%;
    }

    .main-title{
        font-size: 2vw;
        margin: 0 auto;
    }

</style>

