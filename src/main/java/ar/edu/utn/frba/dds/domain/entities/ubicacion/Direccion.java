package ar.edu.utn.frba.dds.domain.entities.ubicacion;

import ar.edu.utn.frba.dds.utils.manejoDistancias.ManejoDistancias;
import java.io.IOException;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Embeddable
@NoArgsConstructor
public class Direccion {
  @Embedded
  private Calle calle;
  @Column(name="altura")
  private String altura;
  @Embedded
  private Municipio municipio;
  @Embedded
  private Provincia provincia;
  @Embedded
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
    return ManejoDistancias.distanciaHaversineConCoordenadasEnKm(direccion.getCoordenada(), this.coordenada) <= umbralKm;
  }

  public String direccionSegunGeoRef() {
    return calle.getCalle() + altura;
  }

  private Coordenada obtenerCoordenada() throws IOException {
    return GeoRefServicio.getInstancia().coordenadaDeDireccion(this);
  }
}
