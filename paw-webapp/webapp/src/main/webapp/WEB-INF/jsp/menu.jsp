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

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>Hello, world!</title>
</head>
<body id="menu">
    <%@ include file="components/navbar.jsp" %>

    <!-- mockeado por ahora! TODO cambiar por lo que viene de la db -->
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Nombre</th>
            <th scope="col">Descripción</th>
            <th scope="col">Precio</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Milanesa</td>
            <td>Milanesa de ternera</td>
            <td>$500</td>
        </tr>
        <tr>
            <td>Empanadas</td>
            <td>Carne, verdura, jamon y queso</td>
            <td>$200</td>
        </tr>
        </tbody>
    </table>
</body>
