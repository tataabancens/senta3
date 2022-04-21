<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <!-- <link rel="stylesheet" href="home"> -->



    <title>Hello, world!</title>

</head>
<body>
<nav>
    <div class="nav-wrapper navbar">
        <div class="row">
            <div class="col">
                <sec:authorize access="hasRole('RESTAURANT')">
                <a href="${pageContext.request.contextPath}/restaurant=1/menu">
                    <span class="logo">Senta3</span>
                </a>
                </sec:authorize>

                <sec:authorize access="!hasRole('RESTAURANT')">
                <a href="${pageContext.request.contextPath}/">
                    <span class="logo">Senta3</span>
                </a>
                </sec:authorize>

            </div>
            <sec:authorize access="hasRole('RESTAURANT')">
                <div class="col right">
                    <a href="${pageContext.request.contextPath}/logout">
                        <p class="logo smaller">log out</p>
                    </a>
                </div>
            </sec:authorize>
            <sec:authorize access="!hasRole('RESTAURANT')">
                <div class="col right">
                    <a href="${pageContext.request.contextPath}/login">
                        <p class="logo smaller">log in</p>
                    </a>
                </div>
            </sec:authorize>

        </div>
    </div>
</nav>
</body>
</html>

<style>
    .navbar{
        background-color: white;
    }
    .logo{
        color:  #37A6E6;
        margin-left: 5%;
        font-family: "Segoe UI", Arial, sans-serif;
        font-weight: bold;
        font-style: italic ;
        font-size:36px;
    }

    .smaller{
        font-size: 25px;
    }
</style>
