package ar.edu.utn.frba.dds.domain.contacto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Mensaje {
  private String asunto;
  private String cuerpo;
  private LocalDateTime fecha;

  public Mensaje(String asunto, String cuerpo, LocalDateTime fecha) {
    this.asunto = asunto;
    this.cuerpo = cuerpo;
    this.fecha = fecha;
  }
}
