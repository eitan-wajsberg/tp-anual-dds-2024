package ar.edu.utn.frba.dds.domain.entities.ubicacion;

import ar.edu.utn.frba.dds.domain.entities.tecnicos.Area;
import ar.edu.utn.frba.dds.utils.manejoDistancias.ManejoDistancias;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Direccion {
  private Calle calle;
  private String altura;
  private Municipio municipio;
  private Provincia provincia;
  private Coordenada coordenada;

  public Direccion(Calle calle, String altura, Municipio municipio, Provincia provincia) throws IOException {
    this.calle = calle;
    this.altura = altura;
    this.municipio = municipio;
    this.provincia = provincia;
    this.coordenada = obtenerCoordenada();
  }

  public boolean estaCercaDe(Direccion direccion) {
    int umbralKm = UmbralDistanciaEnKm.getInstance().getUmbralDistanciaEnKm();

    double lat1 = Double.parseDouble(direccion.coordenada.getLatitud());
    double lon1 = Double.parseDouble(direccion.coordenada.getLongitud());
    double lat2 = Double.parseDouble(this.coordenada.getLatitud());
    double lon2 = Double.parseDouble(this.coordenada.getLongitud());

    return ManejoDistancias.distanciaHaversine(lat1, lon1, lat2, lon2) <= umbralKm;
  }

  public String direccionSegunGeoRef() {
    return calle.getCalle() + altura;
  }

  private Coordenada obtenerCoordenada() throws IOException {
    return GeoRefServicio.getInstancia().coordenadaDeDireccion(this);
  }
}
