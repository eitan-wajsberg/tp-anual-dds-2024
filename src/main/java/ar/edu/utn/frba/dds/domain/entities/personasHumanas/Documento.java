package ar.edu.utn.frba.dds.domain.entities.personasHumanas;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Documento {
  @Setter
  private Long id;
  @Setter
  private TipoDocumento tipo;
  @Setter
  private String nroDocumento;

  public Documento(TipoDocumento tipo, String nroDocumento){
    this.tipo = tipo;
    this.nroDocumento = nroDocumento;
  }
}
