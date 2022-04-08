<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<%--<html>--%>
<%--    <body>--%>
<%--        <h2>Hello <c:out value="${user.username}"/>!</h2>--%>
<%--        <h4>The users id is <c:out value="${user.id}"/></h4>--%>
<%--    </body>--%>
<%--</html>--%>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Materialize CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <title>Hello, world!</title>
</head>
<body>
<%@ include file="components/navbar.jsp" %>
<div class="container">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th class="name" scope="col">Nombre</th>
            <th scope="col" class="price">Precio</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="dish" items="${dish}">
            <tr>
                <td class="name"><c:out value="${dish.dishName}"/></td>
                <td class="price">$<c:out value="${dish.price}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>

<style>
    .container{
        position: absolute;
        top: 20%;
        left: 10%;
        margin: -25px 0 0 -25px;
    }
    .price{
        width: 20px;
        text-align: center;
    }
    .name{
        width: 150px;
        text-align: center;
    }
</style>
