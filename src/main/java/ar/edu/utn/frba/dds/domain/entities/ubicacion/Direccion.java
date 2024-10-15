package ar.edu.utn.frba.dds.domain.entities.ubicacion;

import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import ar.edu.utn.frba.dds.utils.manejos.ManejoDistancias;
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
  @Column(name = "direccion")
  private String nomenclatura;
  @Embedded
  private Coordenada coordenada;

  public boolean estaCercaDe(Direccion direccion) {
    int umbralKm = Integer.parseInt(PrettyProperties.getInstance().propertyFromName("umbral_de_cercania_en_km"));
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
