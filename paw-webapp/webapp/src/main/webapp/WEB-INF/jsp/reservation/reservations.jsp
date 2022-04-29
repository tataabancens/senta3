<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: uriel
  Date: 29-Apr-22
  Time: 11:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

        <c:forEach var="reservation" items="${reservations}">

            <div class="notification-item">
                <span class="title2">Hora: <c:out value="${reservation.reservationHour}"/>:00</span>
                <div>
                    <span class="title2">Id: <c:out value="${reservation.reservationId}"/></span>
                </div>
                <span class="title2">Estado: <c:out value="${reservation.reservationStatus}"/>
                    <a class="waves-effect waves-light btn red confirm-btn" href="cancelReservationConfirmation/id=${reservation.reservationId}">Cancelar</a>
                    <a class="waves-effect waves-light btn red confirm-btn" href="seatCustomer=${reservation.reservationId}">Sentar</a>
            </div>
        </c:forEach>



</head>
<body>

</body>
</html>
