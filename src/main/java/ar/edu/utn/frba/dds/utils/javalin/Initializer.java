package ar.edu.utn.frba.dds.utils.javalin;


import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.domain.entities.oferta.Oferta;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.Rubro;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioOferta;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioRubro;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;

public class Initializer implements WithSimplePersistenceUnit {

  public static void init() {
    Initializer initializer = new Initializer();
    initializer.iniciar();
  }
  public void iniciar() {
    // TODO: agregar elementos de pacotilla
    withTransaction(()->{iniciarRubros();});
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