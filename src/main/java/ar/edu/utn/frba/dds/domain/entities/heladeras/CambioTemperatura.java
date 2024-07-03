package ar.edu.utn.frba.dds.domain.entities.heladeras;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CambioTemperatura {
  private LocalDateTime fecha;
  private float temperaturaCelsius;

  public CambioTemperatura(LocalDateTime fecha, float temperaturaCelsius) {
    this.fecha = fecha;
    this.temperaturaCelsius = temperaturaCelsius;
  }
}
