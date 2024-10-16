searchInput.addEventListener('input', function() {
    var searchTerm = searchInput.value.toLowerCase();
    var filteredHeladeras = heladeras.filter(function(heladera) {
        return heladera.nombre.toLowerCase().includes(searchTerm) ||
            heladera.direccion.nomenclatura.toLowerCase().includes(searchTerm);
    });

    // Actualizar la lista lateral
    renderHeladeras(filteredHeladeras);

    // Eliminar los marcadores actuales del mapa y agregar los filtrados
    clearMarkers();
    addMarkers(filteredHeladeras, function() {
        window.location.href = `/mapaHeladeras/${this.heladera.id}/HeladeraParticular`;
    });
});
