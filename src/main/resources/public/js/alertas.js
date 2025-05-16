function initializeFormHandler(formId) {
    const form = document.getElementById(formId);

    if (form) {
        form.addEventListener('submit', function (event) {
            // Evitar el comportamiento predeterminado del formulario
            event.preventDefault();

            // Desactivar la validación nativa antes de la validación
            const requiredFields = form.querySelectorAll('[required]');
            requiredFields.forEach(field => field.setCustomValidity(''));

            let isValid = true;

            // Verificar si todos los campos requeridos están llenos
            for (const field of requiredFields) {
                if (!field.value) {
                    isValid = false; // Si hay algún campo requerido vacío
                    field.setCustomValidity('Este campo es requerido.'); // Mensaje personalizado
                    field.reportValidity(); // Mostrar mensaje de validación del navegador
                }
            }

            // Si los campos son válidos, mostramos el mensaje y enviamos el formulario después
            if (isValid) {
                Swal.fire({
                    icon: 'success',
                    title: 'Datos cargados con éxito',
                    showConfirmButton: false,
                    timer: 2000 // El mensaje estará visible durante 2 segundos
                }).then(function () {
                    // Después de que el mensaje se haya mostrado, enviamos el formulario
                    form.submit(); // Enviar el formulario manualmente
                });
            } else {
                // Mostrar alerta si hay campos vacíos
                Swal.fire({
                    icon: "error",
                    title: "Error",
                    text: "Por favor, completa todos los campos requeridos."
                });
            }
        });
    }
}

function initializeFormHandlerForReportes(formId) {
    const form = document.getElementById(formId);

    if (form) {
        form.addEventListener('submit', function (event) {
            // Evitar comportamiento predeterminado
            event.preventDefault();

            // Mostrar alerta y enviar el formulario
            Swal.fire({
                icon: 'success',
                title: '¡Reportes generados con éxito!',
                showConfirmButton: false,
                timer: 2000
            }).then(function () {
                form.submit(); // Enviar formulario manualmente
            });
        });
    }
}

function initializeFormHandlerForSolicitudHeladera(formId) {
    const form = document.getElementById(formId);

    if (form) {
        form.addEventListener('submit', function (event) {
            // Evitar comportamiento predeterminado
            event.preventDefault();

            // Mostrar alerta y enviar el formulario
            Swal.fire({
                icon: 'success',
                title: '¡Solicitud de Apertura Generada con Éxito!',
                showConfirmButton: false,
                timer: 2000
            }).then(function () {
                form.submit(); // Enviar formulario manualmente
            });
        });
    }
}


document.addEventListener('DOMContentLoaded', function () {
    initializeFormHandlerForReportes('generarReportesForm');
    initializeFormHandler('miFormulario');
    initializeFormHandler('formVisitaTecnica');
    initializeFormHandlerForSolicitudHeladera('formSolicitudApertura');
});


