<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@700&family=Quicksand:wght@600&display=swap" rel="stylesheet">
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">

    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">

    <title>Senta3</title>
<body>
    <%@ include file="../../components/navbar.jsp" %>

    <div class="form-container">
        <c:url value="/restaurant=${restaurantId}/menu/edit/dishId=${dishId}" var="postPath"/>
        <form:form modelAttribute="editDishForm" action="${postPath}" method="post">
            <div class="card card-content">
                    <h3 class="presentation-text"><spring:message code="Editdish.title"/></h3>
                    <a href="<c:url value="/restaurant=${restaurantId}/confirmDish=${dishId}"/>" class="img-visualizer">
                        <c:if test="${dish.imageId > 0}">
                            <div class="container">
                                <img src="<c:url value="/resources_/images/${dish.imageId}"/>" alt="La foto del plato"/>
                                <i class="small material-icons" style="color: #E63737">edit</i>
                            </div>
                        </c:if>
                        <c:if test="${dish.imageId == 0}">
                            <div class="container">
                                <img src="<c:url value="/resources/images/fotoDefault.png"/>" alt="Es una foto default"/>
                                <i class="small material-icons" style="color: #E63737">edit</i>
                            </div>
                        </c:if>
                    </a>
                    <span class="text description"><c:out value="${dish.dishName}"/></span>
                <div style="margin: 30px;">
                    <div class="disName">
                        <form:errors path="dishName" element="p" cssStyle="color:red"/>
                        <form:label path="dishName" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createdish.form.name"/></form:label>
                        <form:input path="dishName" type="text"/>
                    </div>
                    <div class="dishPrice">
                        <form:errors path="dishPrice" element="p" cssStyle="color: red"/>
                        <form:label path="dishPrice" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createdish.form.price"/></form:label>
                        <form:input path="dishPrice" type="text"/>
                    </div>
                    <div class="dishDesc">
                        <form:errors path="dishDesc" element="p" cssStyle="color:red"/>
                        <form:label path="dishDesc" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Createdish.form.description"/></form:label>
                        <form:textarea path="dishDesc" type="text" style="height:8em;"/>
                    </div>
                </div>
                <div class="input-field">
                    <form:select  path="category">
                        <form:options items="${categories}"></form:options>
                    </form:select>
                </div>
                <div class="submit center">
                    <spring:message code="Button.confirm" var="label"/>
                    <spring:message code="Button.loading" var="label2"/>
                    <input type="submit" value="${label}" class="btn confirm-btn" onclick="this.form.submit(); this.disabled=true;this.value=${label2}; "/>
                </div>
            </div>
        </form:form>
    </div>
</body>
</html>

<style>

    select{
        display: flex;
    }
    form{
        min-width: 30%;
    }
    .container { position: relative; }
    .container img{display: block}
    .container .material-icons{
        position: absolute;
        bottom: 45px;
        left: 65px;
    }
    .img-visualizer{
        align-self: center;
        width: 50%;
    }
    .container:hover img{
        filter: blur(1.5px);
    }
    .container:hover .material-icons{
        display: flex;
        justify-content: center;
        align-items: center;
    }
    img{
        border-radius: 16px;
        width: 100%;
        height: 100%;
    }
    .material-icons{
        display: none;
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
        min-height: 20%;
        min-width: 100%;
    }
    body{
        background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }
</style>
