package ar.edu.utn.frba.dds.domain.definirPackages;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

public class Mensaje {

  private String asunto;
  private String cuerpo;
  private LocalDateTime fecha;

}
