package ar.edu.utn.frba.dds.utils.javalin;


import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.domain.entities.donacionesDinero.DonacionDinero;
import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioDonacionDinero;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioDonacionVianda;
import java.time.LocalDate;

public class Initializer {

  public static void init() {

    inicializarDonacionesViandas();
    inicializarDonacionesDinero();
    

  }

  private static void inicializarDonacionesViandas() {
    RepositorioDonacionVianda repositorioDonacionVianda = ServiceLocator.instanceOf(RepositorioDonacionVianda.class);

    Vianda vianda1 = Vianda
        .builder()
        .comida("Noodles")
        .fechaDonacion(LocalDate.now())
        .entregada(true)
        .build();

    Vianda vianda2 = Vianda
        .builder()
        .comida("Pizza")
        .fechaDonacion(LocalDate.now())
        .entregada(false)
        .build();

    repositorioDonacionVianda.guardar(vianda1);
    repositorioDonacionVianda.guardar(vianda2);
  }

  private static void inicializarDonacionesDinero() {

    RepositorioDonacionDinero repositorioDonacionDinero = ServiceLocator.instanceOf(RepositorioDonacionDinero.class);

    DonacionDinero donacionDinero1 = DonacionDinero
        .builder()
        .monto(100F)
        .unidadFrecuencia("Semanal")
        .fecha(LocalDate.now())
        .build();

    DonacionDinero donacionDinero2 = DonacionDinero
        .builder()
        .monto(50F)
        .unidadFrecuencia("Mensual")
        .fecha(LocalDate.now())
        .build();

    repositorioDonacionDinero.guardar(donacionDinero1);
    repositorioDonacionDinero.guardar(donacionDinero2);
  }
}