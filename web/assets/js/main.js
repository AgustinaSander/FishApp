$("#register").click(() => {
    $("#registerForm").removeClass("d-none");
})

// ---------------------------------------------------------------------
//         EventListener para el campo de la contrasena
// ---------------------------------------------------------------------
//4-6 : Nivel de seguridad debil -> Amarillo
//7-15 : Nivel de seguridad media -> Naranja
//16-30 : Nivel de seguridad alta -> Roja

$("#passInput").on('propertychange input', () => {
    //Cada vez que cambia el valor del input
    verNivelSeguridad($("#passInput")[0]);
});

function verNivelSeguridad(valor) {
    let largo = valor.value.length;
    let barraContainer = $(valor).siblings()[2];
    let mensaje = $(valor).siblings()[3];
    let barra = $(barraContainer).children()[0];

    if (largo == 0) {
        $(barra).css("display", "none");
        $(mensaje).css("display", "none");
    }
    else if (largo < 4) {
        $(barra).css("display", "block");
        $(mensaje).css("display", "block");
        $(mensaje).html("Nivel de seguridad muy debil");
        $(barra).css("width", "25%");
        $(barra).css("background-color", "yellow");
        $(barra).attr("aria-valuenow", "25%");
    } else if (largo >= 4 && largo <= 6) {
        $(barra).css("display", "block");
        $(mensaje).css("display", "block");
        $(mensaje).html("Nivel de seguridad debil");
        $(barra).css("width", "50%");
        $(barra).css("background-color", "orange");
        $(barra).attr("aria-valuenow", "50%");
    } else if (largo >= 7 && largo <= 15) {
        $(barra).css("display", "block");
        $(mensaje).css("display", "block");
        $(mensaje).html("Nivel de seguridad medio");
        $(barra).css("width", "75%");
        $(barra).css("background-color", "red");
        $(barra).attr("aria-valuenow", "75%");
    } else {
        $(barra).css("display", "block");
        $(mensaje).css("display", "block");
        $(mensaje).html("Nivel de seguridad alto");
        $(barra).css("width", "100%");
        $(barra).css("background-color", "#8F2525");
        $(barra).attr("aria-valuenow", "100%");
    }
}



function validarLoginForm(form) {
    let valido = true;

    //Valido que el email y la contrasena no esten vacias y que no superen los 100 caracteres
    let { email, password } = form;

    validarLargo(email, 1, 100) ? valido : valido = false;
    validarLargo(password, 1, 100) ? valido : valido = false;

    return valido;
}

function validarRegisterForm(form) {
    let valido = true;
    let { nombre, apellido, email, password } = form;

    //Valido que el nombre tenga entre 1-50 caracteres
    validarLargo(nombre, 1, 50) ? valido : valido = false;

    //Valido que el apellido tenga entre 1-50 caracteres
    validarLargo(apellido, 1, 50) ? valido : valido = false;

    //Valido que el email tenga formato valido
    validarEmail(email) ? valido : valido = false;

    //Se valida que tenga como minimo 4 y maximo 30 caracteres
    //4-6 : Nivel de seguridad debil -> Amarillo
    //7-15 : Nivel de seguridad media -> Naranja
    //16-30 : Nivel de seguridad alta -> Roja
    validarContrasena(password, 1, 100) ? valido : valido = false;

    return valido;
}

function validarContrasena(passUsuario) {
    if (passUsuario.value == "") {
        mostrarError(passUsuario, `El campo es obligatorio.`);
        return false;
    }
    else if (!validarExpresionContrasena(passUsuario.value)) {
        mostrarError(passUsuario, `Debe tener entre 4 y 30 caracteres.`);
        return false;
    }
    ocultarError(passUsuario);
    return true;
}

function validarExpresionContrasena(pass) {
    //Se valida que tenga como minimo 4 y maximo 30 caracteres
    let largo = pass.length;

    if (largo < 4 || largo > 30) {
        return false;
    }
    return true;
}

function validarLargo(campo, min, max) {
    if (campo.value.length >= min && campo.value.length <= max) {
        ocultarError(campo);
        return true;
    }
    mostrarError(campo, "El campo es obligatorio");
    return false;
}


function validarEmail(email) {
    if (email.value == "") {
        mostrarError(email, `El campo es obligatorio.`);
        return false;
    }
    else if (!validarFormatoEmail(email.value)) {
        mostrarError(email, `Formato de email incorrecto. Ej: xxxx@xxxxx.xxx`);
        return false;
    }

    ocultarError(email);
    return true;
}

function validarFormatoEmail(email) {
    let regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/;
    let res = regex.test(email);
    return res;
}

function mostrarError(campo, msg) {
    let span = $(campo).siblings()[1];
    $(campo).addClass("border-danger");
    $(span).html(msg);
}

function ocultarError(campo) {
    let span = $(campo).siblings()[1];
    $(campo).removeClass("border-danger");
    $(span).html("");
}

$("#errorContainer").click(()=>{
    $("#errorContainer p").html("");
});

$("#successContainer").click(()=>{
    $("#successContainer p").html("");
});