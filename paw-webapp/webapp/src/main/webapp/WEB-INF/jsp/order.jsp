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

<div class="row">
    <div class="col s3">
        <div class="card restaurant-card">
            <div class="card-content white-text">
                <span class="card-title text"><c:out value="${restaurant.restaurantName}"/></span>
                <span class="text"><c:out value="${restaurant.phone}"/></span>
            </div>
        </div>
    </div>

    <div class="col s6">
        <div class="card order-card">
            <div class="card-content white-text">
                <div class="row">
                    <span class="card-title title text">Resumen de tu pedido:</span>
                </div>

                <div class="row">
                    <!-- acá va un for de la tabla orderItem -->
                    <div class="col">
                       <span class="card-title title text">Plato</span>
                    </div>
                    <div class="col center">
                        <span class="card-title title text">Cantidad</span>
                    </div>
                    <div class="col center">
                        <!-- acá va  -->
                        <span class="card-title title text">Precio x U</span>
                    </div>
                    <div class="col center">
                        <span class="card-title title text">Precio total</span>
                    </div>
                </div>
                <c:forEach var="orderItem" items="${orderItems}">
                    <div class="row">
                        <div class="col">
                            <span class="card-title title text"><c:out value="${orderItem.dishName}"/></span>
                        </div>
                        <div class="col center">
                            <span class="card-title title text"><c:out value="${orderItem.quantity}"/></span>
                        </div>
                        <div class="col center">
                            <!-- acá va  -->
                            <span class="card-title title text">$<c:out value="${orderItem.unitPrice}"/></span>
                        </div>
                        <div class="col center">
                            <span class="card-title title text"><c:out value="${orderItem.unitPrice * orderItem.quantity}"/></span>
                        </div>
                    </div>
                </c:forEach>

                <div class="row ">
                    <div class="col">
                        <p class="price">Total</p>
                    </div>
                    <div class="col offset-s8">
                        <p class="price"><c:out value="${total}"/></p>
                    </div>
                </div>

                <div class="col s12 btns">
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


    .card{
        border-radius: 16px;
        display: grid;
    }

    .restaurant-card{
    }

    .order-card{
        width: 100%;
    }



    .price{
        font-size: 25px;
        font-weight: bold;
        color: black;
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

    .btns{
        display: flex;
        align-items: center;
        flex-grow: 1;
        justify-content: space-between;
        margin-left: 30%;
        margin-right: 30%;
    }

    .smaller{
        width: 100%;
        margin-bottom: 0;
        margin-top: 0;
    }

    .already-reserved-btn{
    }

</style>

