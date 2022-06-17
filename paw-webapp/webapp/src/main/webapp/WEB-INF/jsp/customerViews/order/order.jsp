<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<!doctype html>
<html lang="en">
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
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
<%@ include file="../../components/navbar.jsp" %>

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
                <span class="title2 text center"><spring:message code="Order.title"/></span>
            </div>
            <div class="summary">
                <div class="titles">
                    <div class="dishname">
                        <span class="title2 text"><spring:message code="Order.dish"/></span>
                    </div>
                    <div>
                        <span class="title2 text"><spring:message code="Order.qty"/></span>
                    </div>
                    <div>
                        <span class="title2 text"><spring:message code="Order.price"/></span>
                    </div>
                    <div>
                        <span class="title2 text"><spring:message code="Order.total"/></span>
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
                        <p class="price"><spring:message code="Order.total"/></p>
                    </div>
                    <div>
                        <p class="price right "><c:out value="${total}"/></p>
                    </div>
                </div>

                <div>
                    <a class="waves-effect waves-light btn confirm-btn already-reserved-btn" href="menu?reservationSecurityCode=${reservation.securityCode}"><spring:message code="Button.back"/></a>

                    <a class="waves-effect waves-light btn confirm-btn already-reserved-btn green" href=""><spring:message code="Button.continue"/></a>
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


</style>

