src="mapaDeHeladeras.js";
mapboxgl.accessToken = 'pk.eyJ1IjoiZmdhdW5hc29tYSIsImEiOiJjbHY0NjdlcTcwNXN1Mmpsc2twNHJ4NWs2In0.1rFiIv1sJZ16-xbd4zWnyw';

var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    center: [-58.42011700459251, -34.5984903697883],
    zoom: 11
});
map.addControl(new mapboxgl.NavigationControl());
console.log('Mapbox loaded');

// Custom marker image URL
var customMarkerImage = '/img/refrigerator.png';


// Función para agregar marcadores en el mapa
function addMarkers(heladeras, onMarkerClick) {
    heladeras.forEach(function (heladera) { addMarker(heladera, onMarkerClick) });
}

// Función para agregar marcadores en el mapa
function addMarker(heladera, onMarkerClick) {

        var lat = parseFloat(heladera.direccion.coordenada.latitud);
        var lng = parseFloat(heladera.direccion.coordenada.longitud);

        if (!isNaN(lat) && !isNaN(lng)) {
            // Crear el marcador
            var marker = new mapboxgl.Marker({
                element: createCustomMarker(customMarkerImage),
                draggable: false
            })
                .setLngLat([lng, lat])
                .addTo(map);

            // Añadir evento 'click' al marcador para redirigir a la página de la heladera
            marker.getElement().addEventListener('click', onMarkerClick.bind({"heladera":heladera}));

            // Opción de centrar el mapa en el marcador cuando se haga click en el lateral (opcional)
            if(document.querySelector(`[data-id="${heladera.id}"]`) != undefined){
                document.querySelector(`[data-id="${heladera.id}"]`).addEventListener('click', function () {
                    map.flyTo({
                        center: [lng, lat],
                        zoom: 15
                    });
                });
            }
        } else {
            console.log(`Coordenadas inválidas para heladera con id: ${heladera.id}`);
        }
    currentMarkers.push(marker);  // Guardar el marcador
}


// Función para crear un marcador personalizado
function createCustomMarker(markerImage) {
    var img = document.createElement('img');
    img.src = markerImage;
    img.style.width = '34px'; // Tamaño del marcador
    img.style.height = '34px';

    var customMarker = document.createElement('div');
    customMarker.appendChild(img);

    return customMarker;
}

var currentMarkers = [];

function clearMarkers() {
    currentMarkers.forEach(marker => marker.remove());
    currentMarkers = [];
}



