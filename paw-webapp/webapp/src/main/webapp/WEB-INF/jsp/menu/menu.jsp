<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
        <link rel="stylesheet" href=" <c:url value="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css"/>">
        <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">

        <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">


        <title>Senta3</title>
        <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" type="image/x-icon">
    </head>
    <body>
        <div class="row">
            <%@ include file="../components/navbar.jsp" %>
        </div>
        <div class="restaurant-header">
            <div class="restaurant-info" style="background-color: rgb(255, 242, 229);">
                <div>
                    <i class="medium material-icons restaurant-icon">restaurant</i>
                </div>
                <div>
                    <div class="presentation-text title restaurant-title">
                        <span class="presentation-text header-title"><c:out value="${restaurant.restaurantName}"/></span>
                    </div>
                    <div class="presentation-text restaurant-description">
                        <span><spring:message code="Restaurant.phone"/> </span>
                        <span><c:out value="${restaurant.phone}"/></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="page-container">
            <div class="restaurant-content">
                <div class="left-section">
                    <div class="card client-actions center">
                        <span class="presentation-text box-comments"><spring:message code="Menu.reservation.new.title"/></span>
                        <sec:authorize access="!hasRole('RESTAURANT')">
                            <div class="reservation-action-btn">
                                <c:url value="/createReservation-0" var="postUrl"/>
                                <form:form action="${postUrl}" method="post">
                                    <input type="submit" value="Reservar" class="waves-effect waves-light btn confirm-btn">
                                </form:form>
                            </div>
                        </sec:authorize>
                        <sec:authorize access="hasRole('RESTAURANT')">
                            <div class="reservation-action-btn">
                                <a disabled class="waves-effect waves-light btn confirm-btn" href=""><spring:message code="Button.reserve"/></a>
                            </div>
                        </sec:authorize>
                        <span class="presentation-text box-comments">Si ya tenes una:</span>
                        <sec:authorize access="!hasRole('RESTAURANT')">
                            <div class="enter-confirm-btn">
                                <a class="waves-effect waves-light btn confirm-btn" href="findReservation?restaurantId=${restaurant.id}"><spring:message code="Button.login"/></a>
                            </div>
                        </sec:authorize>
                        <sec:authorize access="hasRole('RESTAURANT')">
                            <div class="enter-confirm-btn">
                                <a disabled class="waves-effect waves-light btn confirm-btn" href=""><spring:message code="Button.login"/></a>
                            </div>
                        </sec:authorize>
                    </div>
                    <div class="card filter-box">
                        <span class="presentation-text"><spring:message code="FilterBox.filter"/></span>
                        <ul>
                            <li class="filter-option">
                                <form action="">
                                    <label>
                                        <input type="checkbox" class="filled-in"  />
                                        <span class="text description"><spring:message code="FilterBox.option5"/></span>
                                    </label>
                                </form>
                            </li>
                            <li class="filter-option">
                                <form action="">
                                    <label>
                                        <input type="checkbox" class="filled-in"  />
                                        <span class="text description"><spring:message code="FilterBox.option2"/></span>
                                    </label>
                                </form>
                            </li>
                            <li class="filter-option">
                                <form action="">
                                    <label>
                                        <input type="checkbox" class="filled-in"  />
                                        <span class="text description"><spring:message code="FilterBox.option6"/></span>
                                    </label>
                                </form>
                            </li>
                            <li class="filter-option">
                                <form action="">
                                    <label>
                                        <input type="checkbox" class="filled-in"  />
                                        <span class="text description"><spring:message code="FilterBox.option1"/></span>
                                    </label>
                                </form>
                            </li>
                        </ul>
                    </div>
                    <div class="input-field col s12">
                        <select multiple>
                            <option value="" disabled selected>Choose your option</option>
                            <option value="1">Option 1</option>
                            <option value="2">Option 2</option>
                            <option value="3">Option 3</option>
                        </select>
                        <label>Materialize Multiple Select</label>
                    </div>
                </div>
                <div class="dishList">
                    <c:forEach var="dish" items="${restaurant.dishes}">
                        <div class="dish-card">
                            <div class="dish-img">
                                <c:if test="${dish.imageId > 0}">
                                    <img src="<c:url value="/resources_/images/${dish.imageId}"/>" alt="La foto del plato"/>
                                </c:if>
                                <c:if test="${dish.imageId == 0}">
                                    <img src="<c:url value="/resources/images/fotoDefault.png"/>" alt="Es una foto default"/>
                                </c:if>
                            </div>
                            <div class="card-info">
                                <span class="presentation-text"><c:out value="${dish.dishName}"/></span>
                                <p class="text description"><c:out value="${dish.dishDescription}"/></p>
                                <span class="text price">$<c:out value="${dish.price}"/></span>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>

<style>

    .page-container{
        padding-left: 20px;
        padding-right: 20px;
    }
    .restaurant-info{
        width: 100%;
        background-color: rgb(255, 242, 229);
    }
    .dish-card{
        max-width: 38%;
    }
    .restaurant-content{
        margin-top: 30px;
        display: flex;
        width: 100%;
        justify-content: flex-start;
        flex-wrap: wrap;
    }
    .presentation-text{
        font-family:'Nunito',sans-serif;
        font-weight: 700;
        font-size: 1.3rem;
    }
    .text.description{
        font-family: 'Quicksand',sans-serif;
        font-weight: 600;
        font-size: 1rem;
    }
    .text.price{
        font-weight: 600;
        font-size: 1.4rem;
    }
    @media screen and (max-width: 1068px){
        .dish-card{
            max-width: 80rem;
        }
        .dish-img{
            min-width: 20rem;
            max-height: 20rem;
        }
    }
    @media screen and (max-width: 868px){
        .dish-card{
            padding: 2.5rem;
            flex-direction: column;
        }
        .dish-img{
            min-width: 100%;
            max-width: 100%;
        }
    }

    @media screen and (max-width: 768px){
        .dish-card{
            padding: 2.5rem;
            flex-direction: column;
        }
        .dish-img{
            min-width: 90%;
            max-width: 90%;
        }
    }
    .presentation-text.box-comments{
        color: #171616;
    }
    .restaurant-icon{
        color: rgb(255, 68, 31);
        margin-right: 25px;
    }
    .reservation-action-btn{
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
    }
    a{
        padding: 5px;
        justify-content: center;
        max-width: 200px;
    }
    .card.client-actions{
        flex-direction: column;
        justify-content: space-evenly;
        background-color: whitesmoke;
        padding: 10px;
        min-height: 150px;
        max-height: 250px;
        width: 100%;
    }
    .left-section{
        display: flex;
        flex-direction: column;
        width: 15%;
    }
    .dishList{
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
        width: 85%;
    }
    


</style>
<script type="text/javascript" src="js/materialize.min.js">
    function myFunction() {
        document.getElementById("myDropdown").classList.toggle("show");
    }
    window.onclick = function(event) {
        if (!event.target.matches('.dropbtn')) {
            var dropdowns = document.getElementsByClassName("dropdown-content");
            var i;
            for (i = 0; i < dropdowns.length; i++) {
                var openDropdown = dropdowns[i];
                if (openDropdown.classList.contains('show')) {
                    openDropdown.classList.remove('show');
                }
            }
        }
    }
</script>
