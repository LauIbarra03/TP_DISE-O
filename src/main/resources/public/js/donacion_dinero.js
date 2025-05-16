function toggleFrecuenciaField(select) {
    const frecuenciaField = document.getElementById('frecuencia_donacion');
    const frecuenia = document.getElementById("frecuencia");
    if (select.value === 'si') {
        frecuenciaField.style.display = 'block';
        frecuenia.setAttribute("required", "");
    } else {
        frecuenciaField.style.display = 'none';
        frecuenia.removeAttribute("required");
    }
}