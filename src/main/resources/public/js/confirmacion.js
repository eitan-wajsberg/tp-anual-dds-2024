function crearModal(idEntidad, contexto, mensaje, urlEliminacion) {
    const modalExistente = document.getElementById('deleteConfirmationModal');
    if (modalExistente) {
        modalExistente.remove();
    }

    const modalHtml = `
        <div class="modal fade" id="deleteConfirmationModal" role="dialog" aria-labelledby="deleteConfirmationModalLabel" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="deleteConfirmationModalLabel">Confirmación de ${contexto}</h5>
                <button type="button" class="modal-close position-absolute top-0 end-0 me-2 close" data-dismiss="modal" aria-label="Close">
                  <span class="iconify" data-icon="mdi:remove" data-width="18" data-height="18"></span>
                </button>
              </div>
              <div class="modal-body">
                ${mensaje}
              </div>
              <div class="modal-footer">
                <button type="button" class="modal-button" data-dismiss="modal">Cancelar</button>
                <form method="POST" action="${urlEliminacion}">
                  <button type="submit" class="modal-button">Sí, eliminar</button>
                </form>
              </div>
            </div>
          </div>
        </div>
    `;
    document.body.insertAdjacentHTML('beforeend', modalHtml);
    $('#deleteConfirmationModal').modal('show');
}

function openModal(id, contexto, tipoEntidad) {
    let mensaje = '';
    let urlEliminacion = '';

    if (tipoEntidad === 'personaVulnerable') {
        mensaje = '¿Estás seguro de que deseas eliminar esta persona?';
        urlEliminacion = `/personasVulnerables/${id}/eliminacion`;
    } else if (tipoEntidad === 'tecnico') {
        mensaje = '¿Estás seguro de que deseas eliminar este técnico?';
        urlEliminacion = `/tecnicos/${id}/eliminacion`;
    }

    crearModal(id, contexto, mensaje, urlEliminacion);
}