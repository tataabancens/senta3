<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">
    <%--    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">--%>

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <title>Senta3</title>
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
</head>
<body>
<div class="row">
    <%@ include file="../../components/navbar.jsp" %>
</div>
    <div class="page-container">
            <div class="card confirm-card">
                <h3 class="main-title text center"><spring:message code="Dishconfirm.title"/></h3>
                <div class="img-visualizer">
                    <div class="card visualizer">
                        <c:if test="${imageId > 0}">
                            <img src="<c:url value="/resources_/images/${imageId}"/>" alt="La foto del plato"/>
                        </c:if>
                        <c:if test="${imageId == 0}">
                            <img src="<c:url value="/resources/images/fotoDefault.png"/>" alt="Es una foto default"/>
                        </c:if>
                    </div>
                </div>
                <c:url value="/restaurant=${restaurantId}/menu/dish=${dishId}/edit-photo" var="postPath"/>
                <form action="${postPath}" method="post" enctype="multipart/form-data">
                    <div class="img-row">
                        <input type="file" name="photo"/>
                        <div class="col s12 center">
                            <spring:message code="Button.load" var="label"/>
                            <input type="submit" value="${label}" class="confirm-btn"/>
                        </div>
                    </div>
                </form>
                <div class="btn-row">
                    <a class="waves-effect waves-light btn confirm-btn green " href="<c:url value="/restaurant=${restaurantId}/menu"/>" onclick="this.disabled=true;this.value='procesando'; this.form.submit();"><spring:message code="Button.confirm" var="label"/><spring:message code="Button.confirm"/></a>
                </div>
            </div>
    </div>


</body>
</html>

<style>

    .card{
        border-radius: 16px;
    }
    .visualizer{
        background-color:#37A6E6;
        padding: 8px;
    }
    img{
        border-radius: 16px;
    }
    .confirm-card{
        display: flex;
        flex-direction: column;
        align-content: center;
        padding: 20px;
        justify-content: center;
    }
    .img-visualizer{
        width: 100%;
        display: flex;
        margin: 20px;
        justify-content: center;
    }
    .img-row{
        width: 100%;
        display: flex;
        margin: 20px;
        justify-content: center;
    }
    .btn-row{
        width: 100%;
        display: flex;
        margin: 20px;
        justify-content: center;
    }


    .center{
        justify-content: center;
    }


    .page-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
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