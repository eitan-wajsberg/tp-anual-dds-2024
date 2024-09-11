src="mapaDeHeladeras.js";
mapboxgl.accessToken = 'pk.eyJ1IjoiZmdhdW5hc29tYSIsImEiOiJjbHY0NjdlcTcwNXN1Mmpsc2twNHJ4NWs2In0.1rFiIv1sJZ16-xbd4zWnyw';

var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    center: [-58.42011700459251, -34.5984903697883],
    zoom: 11
});
map.addControl(new mapboxgl.NavigationControl());

// Custom marker image URL
var customMarkerImage = './refrigerator.png';

function addMarker() {
    var lat = parseFloat(document.getElementById('latInput').value);
    var lng = parseFloat(document.getElementById('lngInput').value);

    if (isNaN(lat) || isNaN(lng)) {
        alert('Por favor, ingresar una latitud válida.');
        return;
    }

    new mapboxgl.Marker({
        // Set the custom marker image
        element: createCustomMarker(customMarkerImage),
        draggable: true
    })
        .setLngLat([lng, lat])
        .addTo(map);

    map.flyTo({
        center: [lng, lat],
        zoom: 15
    });
}

// Function to create a custom marker element with the specified image
function createCustomMarker(markerImage) {
    var img = document.createElement('img');
    img.src = markerImage;
    img.style.width = '34px'; // Adjust the size of the marker image as needed
    img.style.height = '34px'; // Adjust the size of the marker image as needed

    var customMarker = document.createElement('div');
    customMarker.appendChild(img);

    return customMarker;
}