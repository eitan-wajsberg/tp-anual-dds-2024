package ar.edu.utn.frba.dds.domain.entities.ubicacion;

import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
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
  private Integer altura;
  @Embedded
  private Municipio municipio;
  @Embedded
  private Provincia provincia;
  @Embedded
  private Coordenada coordenada;

  public Direccion(String calle, Integer altura, String municipio, String provincia, String rutaHbs) throws IOException {
    if (!GeoRefServicio.getInstancia().direccionExiste(calle, altura, municipio, provincia)) {
      throw new ValidacionFormularioException("Dirección inexistente, revisar los datos", rutaHbs);
    }

    this.calle = new Calle(calle);
    this.altura = altura;
    this.municipio = new Municipio(municipio);
    this.provincia = new Provincia(provincia);
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
