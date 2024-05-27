package ar.edu.utn.frba.dds.domain.heladeras;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CambioEstado {
  private EstadoHeladera estado;
  private LocalDate fechaCambio;

  public CambioEstado(EstadoHeladera estado, LocalDate fechaCambio) {
    this.estado = estado;
    this.fechaCambio = fechaCambio;
  }
}
