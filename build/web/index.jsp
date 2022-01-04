
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FishApp</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>

    <nav class="navbar">
        <div class="container-fluid px-4">
            <a id="title" class="navbar-brand font-weight-bold" href="#">
                <i id="logo" class="fas fa-fish"></i>
            FISHAPP
            </a>
        </div>
    </nav>

    <div id="container" class="container p-5">
        <h3>Ingrese a su cuenta</h3>

        <form action="SvUsuario" method="post" class="form" novalidate autocomplete="off" onsubmit="return validarLoginForm(this)">
            <div class="form-group">
                <label class="form-label">Email</label>
                <input type="text" class="form-control mb-2" name="email" placeholder="ejemplo@hotmail.com">
                <span class="text-danger validationMsg"></span>
            </div>
            <div class="form-group  mt-4">
                <label class="form-label">Contraseña</label>
                <input type="password" class="form-control mb-2" name="password">
                <span class="text-danger validationMsg"></span>
            </div>
            <p>No tienes una cuenta? <u id="register">Registrate</u></p>
            <button type="submit" class="btn btn-primary">Iniciar Sesion</button>
        </form>
        <br>
        
        <div id="errorContainer" class="text-danger">
            <input type="text" id="error" value="${error}" hidden>
            <p>${msg}</p>
        </div>
        <div id="successContainer" class="text-success">
            <input type="text" id="success" value="${success}" hidden>
            <p>${msgSuccess}</p>
        </div>
        
        <form class="d-none" id="registerForm" novalidate autocomplete="off" action="SvAltaUsuario" method="post" onsubmit="return validarRegisterForm(this)">
            <hr>
            <br>
            <h3>Crear una cuenta</h3>
            <p class="h5 mb-4">Ingrese sus datos</p>
            <div class="row mb-2">
                <div class="form-group col-md-6">
                    <label class="form-label">Nombre</label>
                    <input type="text" name="nombre" class="form-control mb-2">
                    <span class="text-danger validationMsg"></span>
                </div>
                <div class="form-group col-md-6">
                    <label class="form-label">Apellido</label>
                    <input type="text" name="apellido" class="form-control mb-2">
                    <span class="text-danger validationMsg"></span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <label class="form-label">Email</label>
                    <input type="text" name="email" class="form-control mb-2" placeholder="ejemplo@hotmail.com">
                    <span class="text-danger validationMsg"></span>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Contraseña</label>
                    <input id="passInput" type="password" name="password" class="form-control mb-2">
                    <span class="text-danger validationMsg"></span>
                    <div class="progress mt-2" style="height: 8px;">
                        <div class="progress-bar" role="progressbar" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                    <p class="mt-1"></p>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">Crear cuenta</button>
        </form>
        
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="assets/js/main.js"></script>
</body>

</html>