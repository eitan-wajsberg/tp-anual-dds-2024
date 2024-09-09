package ar.edu.utn.frba.dds.domain.entities.heladeras;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CambioEstado {
  @Id @GeneratedValue
  private long id;
  @Enumerated (EnumType.STRING)
  @Column(name="estado",nullable = false)
  private EstadoHeladera estado;
  private LocalDate fechaCambio;

  public CambioEstado(EstadoHeladera estado, LocalDate fechaCambio) {
    this.estado = estado;
    this.fechaCambio = fechaCambio;
  }

  public boolean esUnaFalla() {
    return !this.estado.equals(EstadoHeladera.ACTIVA);
  }
}
