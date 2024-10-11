const tipoDocumentoGuardado = localStorage.getItem('tipoDocumento') || 'DNI';
document.addEventListener("DOMContentLoaded", function() {
    document.querySelector('#documentos').value = tipoDocumentoGuardado;
    document.querySelector('#documentos').addEventListener('change', function() {
        localStorage.setItem('tipoDocumento', this.value);
    });
});