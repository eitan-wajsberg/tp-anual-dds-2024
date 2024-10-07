package ar.edu.utn.frba.dds.utils.javalin;


import ar.edu.utn.frba.dds.domain.entities.oferta.Oferta;

public class Initializer {

  public static void init() {
    // TODO: agregar elementos de pacotilla
    Oferta oferta = Oferta
        .builder()
        .nombre("Set de utensillos")
        .cantidadPuntosNecesarios(20F)
        .rubro()
  }
}