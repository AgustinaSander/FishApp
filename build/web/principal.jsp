<%@page import="Logica.Pecera"%>
<%@page import="java.util.List"%>
<%@page import="Logica.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FishApp</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body id="bodyPrincipal">
    <% 
        HttpSession miSession = request.getSession();
        Usuario usu = (Usuario) miSession.getAttribute("usuario");
        if(usu == null){
            response.sendRedirect("index.jsp");
        }
        else{
            String nombreUsuario = usu.getNombre();
            String apellidoUsuario = usu.getApellido();
    %>
    <nav class="navbar">
        <div class="px-4">
            <a id="title" class="navbar-brand font-weight-bold" href="#">
                <i id="logo" class="fas fa-fish"></i>
            FISHAPP
            </a>
        </div>
        <div class="d-flex">
            <div class="d-flex align-items-center pr-4">
                <div>
                    <i class="fas fa-user text-light mx-3"></i>
                </div>
                <div class="text-light pt-3">
                    <p><%= nombreUsuario%>, <%= apellidoUsuario%></p>
                </div>
            </div>
            <div class="d-flex align-items-center pl-4">
                <div>
                    <i class="fas fa-sign-out-alt text-light mr-2"></i>
                </div>
                <div>
                    <form action="SvUsuario" method="get">
                        <button type="submit" class="text-light" style="border:none; outline: none; background-color: #014580;">Cerrar Sesion</button>
                    </form>
                </div>
            </div>
        </div>
    </nav>

    <div class="pecerasContainer">
        <h3>TUS PECERAS</h3>
        <div class="pecerasList">
            <%    
                List<Pecera> listaPeceras = usu.getListaPeceras();
                for(Pecera p: listaPeceras){
                    String nombrePecera = p.getNombre();
                    int idPecera = p.getIdPecera();
                    String urlPecera = "";
            %>
            <div class="pecera card bg-light border-dark">
                <h5 class="card-title"><%= nombrePecera %></h5>
                <%
                    if(p.getFotoPrincipal() != null){
                        urlPecera = p.getFotoPrincipal().getUrl();
                %>    
                        <img class="card-img" src="files/<%= urlPecera %>" alt="">
                <% }else{ %>
                        <img class="card-img" src="assets/img/noimage.png" alt="">
                <% } %>
                <form action="pecera.jsp" method="get">
                    <input type="text" name="idPecera" hidden value="<%= idPecera %>"/>
                    <button type="submit" class="btn btn-primary">VER PECERA</button>
                </form>
            </div>
            <% } %>
            <div class="agregarContainer">
                <div id="agregarPeceraBtn">
                    <a href="agregarPecera.jsp"><i class="fas fa-plus text-light"></i></a>
                </div>
                <p>Agregar pecera</p>
            </div>
        </div>
    </div>

    <footer>
        <p>&#169 Diseñada por Agustina Sander </p>
    </footer>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <% } %>
</body>
</html>
