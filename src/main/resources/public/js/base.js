// Obtener la ruta actual
const currentPath = window.location.pathname;

// Obtener todos los enlaces de navegación
const links = document.querySelectorAll('.nav-link');

// Iterar sobre los enlaces y agregar la clase 'active' al enlace actual
links.forEach(link => {
    const href = link.getAttribute('href');
    if (href === currentPath
        || (currentPath.startsWith("/colaboraciones") && href === "/colaboraciones")
        || (currentPath.startsWith("/colaboradores") && href === "/colaboradores")
        || (currentPath.startsWith("/heladeras") && href === "/heladeras")
        || (currentPath.startsWith("/persona_vulnerable") && href === "/persona_vulnerable")
        || (currentPath.startsWith("/viandas") && href === "/viandas")) {

        console.log(currentPath); // Imprimir la ruta actual en la consola
        link.classList.remove('text-white');
        link.classList.add('active');
        link.setAttribute('aria-current', 'page');
    }
});

function toggleDireccionField(radio) {
    const direccionFields = document.getElementById('direccionFields');
    const inputs = direccionFields.querySelectorAll('input, select');

    if (radio.value === 'si') {
        direccionFields.style.display = 'block';
        inputs.forEach(input => {
            input.setAttribute('required', '');
        });
    } else {
        direccionFields.style.display = 'none';
        inputs.forEach(input => {
            input.removeAttribute('required');
        });
    }
}

function toggleDocumentoField(radio) {
    const documentoFields = document.getElementById('documentoFields'); // Cambia a 'documentoFields'
    const inputs = documentoFields.querySelectorAll('input, select');

    if (radio.value === 'si') {
        documentoFields.style.display = 'block';
        inputs.forEach(input => {
            input.setAttribute('required', '');
        });
    } else {
        documentoFields.style.display = 'none';
        inputs.forEach(input => {
            input.removeAttribute('required');
        });
    }
}