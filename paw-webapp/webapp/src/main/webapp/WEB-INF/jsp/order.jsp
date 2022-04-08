<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

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
    <table class="table table-hover table-bordered">
        <thead>
        <tr>
            <th class="name" scope="col">Nombre</th>
            <th class="price" scope="col">Precio</th>
            <th class="price" scope="col">Unidades</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="dish" items="${dish}">
                <tr>
                    <td class="name"><c:out value="${dish.dishName}"/></td>
                    <td class="price">$<c:out value="${dish.price}"/></td>
                    <td class="price"><select class="form-select">
                        <option selected>0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                    </select></td>
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

