package ar.edu.utn.frba.dds.domain.personasVulnerables;

import ar.edu.utn.frba.dds.domain.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.personasVulnerables.UsoDeTarjeta;
import java.util.List;

public class Tarjeta {

  private String codigo;
  private List<UsoDeTarjeta> historialUsos;
  private PersonaVulnerable titular;
}
