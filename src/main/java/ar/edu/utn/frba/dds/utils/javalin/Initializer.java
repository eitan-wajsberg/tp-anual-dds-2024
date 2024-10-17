package ar.edu.utn.frba.dds.utils.javalin;


import static ar.edu.utn.frba.dds.domain.entities.personasHumanas.FormasContribucionHumanas.DONACION_DINERO;
import static ar.edu.utn.frba.dds.domain.entities.personasHumanas.FormasContribucionHumanas.DONACION_VIANDAS;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.domain.entities.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.entities.donacionesDinero.UnidadFrecuencia;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.Rubro;
import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioPersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioRubro;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.util.Set;

public class Initializer implements WithSimplePersistenceUnit {

  public static void init() {
    // Initializer initializer = new Initializer();
    // initializer.iniciar();
  }

  public void iniciar() {
    withTransaction(this::iniciarRubros);
  }

  private void iniciarRubros() {
    RepositorioRubro repositorioRubro = ServiceLocator.instanceOf(RepositorioRubro.class);

    Rubro gastronomia = new Rubro("gastronomia");
    Rubro turismo = new Rubro ("turismo");
    Rubro electrodomesticos = new Rubro ("electrodomesticos");

    repositorioRubro.guardar(gastronomia);
    repositorioRubro.guardar(turismo);
    repositorioRubro.guardar(electrodomesticos);
  }
}