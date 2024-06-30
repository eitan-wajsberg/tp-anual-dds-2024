package ar.edu.utn.frba.dds.domain.entities.personasHumanas;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Documento {
  private Long id;
  private TipoDocumento tipo;
  private String nroDocumento;

  public Documento(TipoDocumento tipo, String nroDocumento){
    this.tipo = tipo;
    this.nroDocumento = nroDocumento;
  }
}
