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

  <title>Senta3</title>

  <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
<body>
<%@ include file="../components/navbar.jsp" %>

<div style="display: flex;justify-content: center;margin-top: 2.5%;">
  <c:url value="/restaurant=${restaurantId}/editTables" var="postPath"/>
  <div class="card">
    <form:form modelAttribute="editTablesForm" action="${postPath}" method="post">
      <div style="display: flex; justify-content: center;">
        <span class="presentation-text" style="color: #171616;"><spring:message code="Restaurant.tables"/></span>
      </div>
      <div class="row">
        <div class="input-field col s12 input">
          <form:errors path="" element="p" cssStyle="color:red"/>
          <form:errors path="openHour" element="p" cssStyle="color:red"/>
          <form:label path="openHour" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Restaurant.hour.open"/></form:label>
          <form:input path="openHour" type="text"/>
        </div>
        <div class="input-field col s12 input">
          <form:errors path="" element="p" cssStyle="color:red"/>
          <form:errors path="closeHour" element="p" cssStyle="color:red"/>
          <form:label path="closeHour" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Restaurant.hour.close"/></form:label>
          <form:input path="closeHour" type="text"/>
        </div>
        <div class="input-field col s12 input">
          <form:errors path="tableQty" element="p" cssStyle="color:red"/>
          <form:label path="tableQty" class="helper-text" data-error="wrong" data-success="right"><spring:message code="Restaurant.chairs"/></form:label>
          <form:input path="tableQty" type="text"/>
        </div>
      </div>
      <div class="col s12 center" style="margin-top: 2%;">
        <!-- acá va un href -->
        <spring:message code="Button.continue" var="label"/>
        <input type="submit" value="${label}" class="btn confirm-btn"/>
      </div>
    </form:form>
  </div>
</div>
</body>
</html>

<style>

  .card{
    border-radius: .8rem;
    display: flex;
    flex-direction: column;
    padding: 1.5%;
    align-items: center;
  }

  body{
    background: url("${pageContext.request.contextPath}/resources/images/form-background.svg") no-repeat center center fixed;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
  }

  .input{
    margin-bottom: 1px;
  }

  .center{
    align-items: center;
  }

</style>
