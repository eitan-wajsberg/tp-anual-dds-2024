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
    // FIXME: No se asigna persona humana responsable a las contribuciones y por ende falla la persistencia 😁
    // inicializarPersonasHumanas();
    // inicializarDonacionesViandas();
    // inicializarDonacionesDinero();
    Initializer initializer = new Initializer();
    initializer.iniciar();
  }

  public void iniciar() {
    // TODO: agregar elementos de pacotilla
    withTransaction(()->{iniciarRubros();});
  }

  private static void inicializarDonacionesViandas() {
    Repositorio repositorioGenerico = ServiceLocator.instanceOf(Repositorio.class);
    RepositorioPersonaHumana repositorioPersonaHumana = ServiceLocator.instanceOf(RepositorioPersonaHumana.class);

    PersonaHumana personaHumana = buildPersonaHumana();
    repositorioPersonaHumana.guardar(personaHumana);

    Vianda vianda1 = Vianda
        .builder()
        .comida("Noodles")
        .fechaDonacion(LocalDate.now())
        .entregada(true)
        .personaHumana(personaHumana)
        .build();

    Vianda vianda2 = Vianda
        .builder()
        .comida("Pizza")
        .fechaDonacion(LocalDate.now())
        .entregada(false)
        .personaHumana(personaHumana)
        .build();

      repositorioGenerico.guardar(vianda1);
      repositorioGenerico.guardar(vianda2);


  }

  private static void inicializarDonacionesDinero() {

    Repositorio repositorioGenerico = ServiceLocator.instanceOf(Repositorio.class);

    DonacionDinero donacionDinero1 = DonacionDinero
        .builder()
        .monto(100F)
        .unidadFrecuencia(UnidadFrecuencia.SEMANAL)
        .fecha(LocalDate.now())
        .build();

    DonacionDinero donacionDinero2 = DonacionDinero
        .builder()
        .monto(50F)
        .unidadFrecuencia(UnidadFrecuencia.MENSUAL)
        .fecha(LocalDate.now())
        .build();

    repositorioGenerico.guardar(donacionDinero1);
    repositorioGenerico.guardar(donacionDinero2);
  }

  private static void inicializarPersonasHumanas() {
    RepositorioPersonaHumana repositorioPersonaHumana = ServiceLocator.instanceOf(RepositorioPersonaHumana.class);

    PersonaHumana personaHumana1 = buildPersonaHumana();

    repositorioPersonaHumana.guardar(personaHumana1);
  }

  private static PersonaHumana buildPersonaHumana() {

    return PersonaHumana
        .builder()
        .nombre("Juan")
        .apellido("Perez")
        .contribucionesElegidas(Set.of(DONACION_DINERO, DONACION_VIANDAS))
        .build();

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