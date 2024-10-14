package ar.edu.utn.frba.dds.utils.javalin;


import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.domain.entities.oferta.Oferta;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.Rubro;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioOferta;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioRubro;
import java.util.Optional;

public class Initializer {

  public static void init() {
    // TODO: agregar elementos de pacotilla
    iniciarRubros();
    //iniciarOfertas();

  }

  public static void iniciarRubros() {
    RepositorioRubro repositorioRubro = ServiceLocator.instanceOf(RepositorioRubro.class);

    Rubro gastronomia = new Rubro("gastronomia");
    Rubro turismo = new Rubro ("turismo");
    Rubro electrodomesticos = new Rubro ("electrodomesticos");

    repositorioRubro.guardar(gastronomia);
    repositorioRubro.guardar(turismo);
    repositorioRubro.guardar(electrodomesticos);
  }
  public static void iniciarOfertas() {
    RepositorioOferta repositorioOferta = ServiceLocator.instanceOf(RepositorioOferta.class);
    RepositorioRubro repositorioRubro = ServiceLocator.instanceOf(RepositorioRubro.class);

    // Obtener los rubros que ya has inicializado
    Rubro gastronomia = repositorioRubro.buscarPorNombre("gastronomia");
    Rubro turismo = repositorioRubro.buscarPorNombre("turismo");

    Oferta oferta1 = Oferta.builder()
        .nombre("Set de utensilios")
        .rubro(gastronomia)
        .cantidadPuntosNecesarios(50f)
        .build();

    Oferta oferta2 = Oferta.builder()
        .nombre("Escapada de fin de semana")
        .rubro(turismo)
        .cantidadPuntosNecesarios(20f)
        .build();

    repositorioOferta.guardar(oferta1);
    repositorioOferta.guardar(oferta2);
  }

}