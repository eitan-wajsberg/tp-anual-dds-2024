package ar.edu.utn.frba.dds.domain.personasHumanas;

import lombok.Getter;

@Getter
public class Documento {
  private Long id;
  private TipoDocumento tipo;
  private String nroDocumento;

}
