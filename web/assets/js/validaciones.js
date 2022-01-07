
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

function validarPeceraForm(form){
    let valido = true;
    let {nombre, tipoAgua, largo, alto, ancho, capacidad, fechaInicio, fechaFin, iluminacion, filtrado, sump, dosificadora, foto} = form;
    
    //El nombre es obligatorio y debe tener entre 1-50 caracteres
    validarLargo(nombre, 1, 50) ? valido : valido = false;
    //Valido que las dimensiones sean numeros positivos distintos de cero
    validarDimension(largo) ? valido : valido = false;
    validarDimension(ancho) ? valido : valido = false;
    validarDimension(alto) ? valido : valido = false;
    validarDimension(capacidad) ? valido : valido = false;
    //Valido que la fecha de inicio no sea mayor al dia actual
    validarFechaMenorActual(fechaInicio) ? valido : valido = false;
    //Valido que la fecha de fin sea mayor a la fecha de inicio
    validarFechaFinMayorInicio(fechaInicio, fechaFin) ? valido : valido = false;
    //Valido que las tecnologias tengan como maximo 200 caracteres
    validarLargo(iluminacion,0,200) ? valido : valido = false;
    validarLargo(filtrado,0,200) ? valido : valido = false;
    validarLargo(sump,0,200) ? valido : valido = false;
    validarLargo(dosificadora,0,200) ? valido : valido = false;
    //Valido que el formato del archivo sea ico, png, jpg o jpeg
    validarFormatoArchivo(foto.value) ? valido : valido = false;
 
    return valido;
    
}

function validarFormatoArchivo(url){
    let extens = [".png",".ico",".jpg",".jpeg",".PNG",".ICO",".JPG",".JPEG"];
    let valido = false;
    extens.forEach(e => {
        if(url.endsWith(e)){
            $("#fileInputError").html("");
            valido = true;
        }
    });
    
    if(!valido){
        $("#fileInputError").html("Formatos permitidos: .ico, .png, .jpg, .jpeg");
    }
    return valido;
}

function validarFechaFinMayorInicio(inicio, fin){
    let valorInicio = inicio.value;
    let partesInicio = valorInicio.split('-');
    let fechaInicio = new Date(partesInicio[0], partesInicio[1] - 1, partesInicio[2]); 
    let valorFin = fin.value;
    let partesFin = valorFin.split('-');
    let fechaFin = new Date(partesFin[0], partesFin[1] - 1, partesFin[2]); 
    
    if(valorFin !="" && valorInicio == ""){
        mostrarError(fin, "Ingrese una fecha de inicio");
        return false;
    }
    else{
        
        if(fechaInicio < fechaFin || valorFin==""){
            ocultarError(fin);
            return true;
        }
        mostrarError(fin,"La fecha de fin debe ser mayor a la de inicio");
        return false;
    }
   
}

function validarFechaMenorActual(fecha){
    let valor = fecha.value;
    if(valor != ""){
        let partes = valor.split('-');
        let fechaDate = new Date(partes[0], partes[1] - 1, partes[2]); 
        if(fechaDate <= new Date()){
            ocultarError(fecha);
            return true;
        }
        mostrarError(fecha, "La fecha debe ser menor al dia actual");
        return false;
    }
    mostrarError(fecha, "El campo es obligatorio");
        return false;
}

function validarDimension(dim){
    let valor = dim.value;
    if(valor != ""){
        if(esNumeroPositivo(valor)){
            ocultarError(dim);
            return true;
        }
        else{
            mostrarError(dim, "Debe ser un numero positivo");
            return false;
        }
    }
    ocultarError(dim);
    return true;
    
}

function esNumeroPositivo(valor){
    let signo = Math.sign(valor);
    if(!isNaN(signo) && signo == 1){
        return true;
    }
    
    return false;
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
    mostrarError(campo, "El campo es obligatorio y debe tener como maximo "+max+" caracteres");
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