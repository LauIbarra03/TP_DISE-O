function toggleNumeroViandas() {
    const select = document.getElementById("tipo_suscripcion_id");
    const numeroViandasGroup = document.getElementById("numero-viandas-group");
    const numeroViandasInput = document.getElementById("numero_viandas");

    if (select.value === "pv" || select.value === "mv") {
        numeroViandasGroup.style.display = "block";
        numeroViandasInput.setAttribute("required", "required");
    } else {
        numeroViandasGroup.style.display = "none";
        numeroViandasInput.removeAttribute("required");
    }
}

