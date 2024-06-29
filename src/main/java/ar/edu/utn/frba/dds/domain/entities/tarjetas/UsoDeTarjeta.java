package ar.edu.utn.frba.dds.domain.entities.tarjetas;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsoDeTarjeta {

  private LocalDateTime fecha;
  private Heladera heladera;

  public UsoDeTarjeta(LocalDateTime fecha, Heladera heladera) {
    this.fecha = fecha;
    this.heladera = heladera;
  }

  public LocalDate getFecha(){
    return fecha.toLocalDate();
  }
}
