$(document).ready(function () {
    $('#formulario_colaboradores').on('submit', function (e) {
        e.preventDefault(); // Evita que el formulario se envíe inmediatamente

        // Validación de que al menos un checkbox está marcado
        if (!$('input[name="contribuciones"]:checked').length) {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Debe seleccionar al menos una forma de contribución',
            });
            return; // Evita que continúe si la validación falla
        }

        // Si la validación es exitosa, mostramos el mensaje de éxito
        Swal.fire({
            icon: 'success',
            title: 'Datos cargados con éxito',
            showConfirmButton: false,
            timer: 2000 // El mensaje estará visible durante 2 segundos
        }).then(function () {
            // Después de que el mensaje se haya mostrado, enviamos el formulario
            $('#formulario_colaboradores')[0].submit(); // Enviar el formulario manualmente
        });
    });
});