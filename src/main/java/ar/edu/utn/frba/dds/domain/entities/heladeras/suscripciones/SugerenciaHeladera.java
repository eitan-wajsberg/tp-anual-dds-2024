package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import java.time.LocalDate;
import java.util.List;

public class SugerenciaHeladera {
  private LocalDate fechaRealizacion;
  private List<Heladera> heladerasSugeridas;
  private List<Heladera> heladerasEscogidas;
}
