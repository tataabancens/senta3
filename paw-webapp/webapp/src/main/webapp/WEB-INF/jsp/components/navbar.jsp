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

    <link href="<c:url value="https://fonts.googleapis.com/icon?family=Material+Icons"/>" rel="stylesheet">

    <title>Hello, world!</title>

</head>
<body>
<button class="mobile-nav-toggle" aria-controls="primary-navigation" aria-expanded="false"></button>
<nav>
    <ul id="primary-navigation" data-visible="false" class="primary-navigation flex">
        <div class="left-side">
            <li>
                <a href="${pageContext.request.contextPath}/">
                    <span class="logo">Senta3</span>
                </a>
            </li>
            <sec:authorize access="hasRole('RESTAURANT')">
                <li>
                    <a class="options" href="${pageContext.request.contextPath}/restaurant=1/menu" >
                        Menú
                    </a>
                </li>
            </sec:authorize>
            <sec:authorize access="hasRole('RESTAURANT')">
                <li>
                    <a class="options" href="${pageContext.request.contextPath}/restaurant=1/orders">
                        Ordenes
                    </a>
                </li>
            </sec:authorize>
            <sec:authorize access="hasRole('RESTAURANT')">
                <li>
                    <a class="options" href="${pageContext.request.contextPath}/restaurant=1/reservations">
                        Reservas
                    </a>
                </li>
            </sec:authorize>
        </div>
        <div class="right-side">
            <sec:authorize access="!hasRole('RESTAURANT')">
                <li>
                    <a class="options" href="${pageContext.request.contextPath}/login">
                        Iniciar sesion
                    </a>
                </li>
            </sec:authorize>
            <sec:authorize access="hasRole('RESTAURANT')">
                <li>
                    <a class="options" href="${pageContext.request.contextPath}/restaurant=1/profile" >
                        Perfil
                    </a>
                </li>
            </sec:authorize>
            <sec:authorize access="hasRole('RESTAURANT')">
                <li>
                    <a class="options" href="${pageContext.request.contextPath}/logout" >
                        Cerrar sesion
                    </a>
                </li>
            </sec:authorize>
        </div>

    </ul>

</nav>
</body>
</html>

<style>
    body{
        overflow-x: hidden;
    }
    nav{
        background-color: #171616;
    }
    .flex{
        display: flex;
        gap: var(--gap,1rem);
    }
    .primary-navigation{
        list-style: none;
        padding: 0;
        margin: 0;
        justify-content: space-between;
    }
    .mobile-nav-toggle{
        display: none;
    }
    .logo{
        font-family: "Goldplay", sans-serif;
        font-size: 1.9rem;
        font-weight: bold;
        color: #37A6E6;
    }
    .primary-navigation a{
        color: whitesmoke;
        font-family: "Goldplay", sans-serif;
        font-size: 1.3rem;
        font-weight: bold;
        text-decoration: none;
    }
    .primary-navigation a > [aria-hidden="true"]{
        font-weight: 700;
        margin-inline-end: 0.5em;
    }
    .primary-navigation a.options:hover{
        background-color: white;
        color: #171616;
    }
    @media (max-width: 35em){
        .primary-navigation{
            position: fixed;
            --gap: 2em;
            inset: 0 0 0 55%;
            z-index: 1000;
            flex-direction: column;
            padding: min(30vh,10rem) 2rem;
            background: hsl(0 0% 100% / 0.1);
            backdrop-filter: blur(1em);
            transform: translateX(100%);
            transition: transform 350ms ease-out;
        }
        .primary-navigation[data-visible="true"]{
            transform: translateX(0%);
        }
        .mobile-nav-toggle{
            display: block;
            position: absolute;
            z-index: 9999;
            background: url("/resources/images/hamburger.png");
            width: 3rem;
            aspect-ratio: 1;
            border: 0;
            top: 2rem;
            right: 2rem;
        }
    }
</style>
<script type="text/javascript">
    const primaryNav = document.querySelector(".primary-navigation");
    const navToggle = document.querySelector(".mobile-nav-toggle");
    const buttons = document.querySelector("a");
    navToggle.addEventListener('click',() => {
        const visibility = primaryNav.getAttribute('data-visible');
        if (visibility=== "false"){
            primaryNav.setAttribute('data-visible',true);
            navToggle.setAttribute('aria-expanded',true);
        }else if(visibility==="true"){
            primaryNav.setAttribute('data-visible',false);
            navToggle.setAttribute('aria-expanded',false);
        }
    })
</script>
