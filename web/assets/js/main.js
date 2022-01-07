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

