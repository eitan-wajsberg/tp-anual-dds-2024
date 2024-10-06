package ar.edu.utn.frba.dds.domain.entities.ubicacion;

import ar.edu.utn.frba.dds.domain.entities.ubicacion.geoRef.GeoRefServicio;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.manejoDistancias.ManejoDistancias;
import java.io.IOException;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Embeddable
@NoArgsConstructor
public class Direccion {
  @Column(name = "direccion")
  private String nomenclatura;
  @Embedded
  private Coordenada coordenada;

  public boolean estaCercaDe(Direccion direccion) {
    int umbralKm = UmbralDistanciaEnKm.getInstance().getUmbralDistanciaEnKm();
    return ManejoDistancias.distanciaHaversineConCoordenadasEnKm(direccion.getCoordenada(), this.coordenada) <= umbralKm;
  }

  public String getDireccion() {
    return nomenclatura.split(",")[0];
  }

  public String getCalle() {
    return getDireccion().replaceAll("\\d+", "").trim();
  }

  public String getAltura() {
    return getDireccion().replaceAll("\\D+", "").trim();
  }

  public String getMunicipio() {
    String[] partes = nomenclatura.split(",");
    if (partes.length > 1) {
      return partes[1].trim();
    }
    return "";
  }

  public String getProvincia() {
    String[] partes = nomenclatura.split(",");
    if (partes.length > 2) {
      return partes[2].trim();
    }
    return "";
  }
}
