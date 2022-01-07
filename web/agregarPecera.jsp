
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
            <a id="title" class="navbar-brand font-weight-bold" href="principal.jsp">
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

    <div class="container">
        <h3 class="text-center my-4">AGREGAR PECERA</h3>
        <form action="SvAltaPecera" method="post" enctype="multipart/form-data" novalidate autocomplete="off" class="form m-4" onsubmit="return validarPeceraForm(this)" >
            <div class="row form-group">
                <div class="col-md-6">
                    <label class="form-label">Nombre *</label>
                    <input type="text" name="nombre" class="form-control" placeholder="Nombre de la pecera">
                    <span class="text-danger validationMsg"></span>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Tipo de agua *</label>
                    <select name="tipoAgua" class="form-control">
                        <option value="0">Dulce</option>
                        <option value="1">Salada</option>
                    </select>
                    <span class="text-danger validationMsg"></span>
                </div>
            </div>
            <h5>Dimensiones en metros</h5>
            <div class="form-group row">
                <div class="col-md-3">
                    <label class="form-label">Largo</label>
                    <input type="text" name="largo" class="form-control">
                    <span class="text-danger validationMsg"></span>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Alto</label>
                    <input type="text" name="alto" class="form-control">
                    <span class="text-danger validationMsg"></span>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Ancho</label>
                    <input type="text" name="ancho" class="form-control">
                    <span class="text-danger validationMsg"></span>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Capacidad en lts</label>
                    <input type="text" name="capacidad" class="form-control">
                    <span class="text-danger validationMsg"></span>
                </div>
            </div>
            <h5>Fechas</h5>
            <div class="form-group row">
                <div class="col-md-6">
                    <label class="form-label">Fecha de inicio *</label>
                    <input type="date" name="fechaInicio" class="form-control">
                    <span class="text-danger validationMsg"></span>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Fecha de fin</label>
                    <input type="date" name="fechaFin" class="form-control">
                    <span class="text-danger validationMsg"></span>
                </div>
            </div>
            <h5>Tecnologias</h5>
            <div class="form-group row">
                <div class="col-md-3">
                    <label class="form-label">Iluminacion</label>
                    <textarea style="resize: none; height: 100px;" name="iluminacion" class="form-control" placeholder="Tecnologias utilizadas para la iluminacion.."></textarea>
                    <span class="text-danger validationMsg"></span>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Filtrado</label>
                    <textarea style="resize: none; height: 100px;" name="filtrado" class="form-control" placeholder="Tecnologias utilizadas en el filtrado.."></textarea>
                    <span class="text-danger validationMsg"></span>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Dosificadora</label>
                    <textarea style="resize: none; height: 100px;" name="dosificadora" class="form-control" placeholder="Tecnologias utilizadas para la dosificacion.."></textarea>
                    <span class="text-danger validationMsg"></span>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Sump</label>
                    <textarea style="resize: none; height: 100px;" name="sump" class="form-control" placeholder="Tecnologias del sump.."></textarea>
                    <span class="text-danger validationMsg"></span>
                </div>
            </div>
            <h5>Imagen principal</h5>
            <input id="fileInput" type="file" name="foto" class="form-group mb-4">
            <span id="fileInputError" class="text-danger validationMsg"></span>
            <br>
            <button type="submit" class="btn btn-primary w-100 ">AGREGAR PECERA</button>
        </form>
    </div>

    <footer>
        <p>&#169 Disenada por Agustina Sander </p>
    </footer>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="assets/js/validaciones.js"></script>
    <% } %>
</body>
</html>
