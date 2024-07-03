package ar.edu.utn.frba.dds.domain.entities.reportes;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioHeladera;
import ar.edu.utn.frba.dds.utils.manejoFechas.ManejoFechas;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class CantidadFallasPorHeladera implements Reporte {
  @Getter @Setter
  IRepositorioHeladera repositorioHeladera;

  public List<String> generarReporte(LocalDate fechaInicio, LocalDate fechaFin) {
    List<String> parrafos = new ArrayList<>();
    int cantidadFallas;
    String parrafo;

    for (Heladera heladera : repositorioHeladera.listar()) {
      cantidadFallas = cantidadFallas(heladera, fechaInicio, fechaFin);
      parrafo = "Heladera " + heladera.getNombre() + "Id: " + heladera.getId() + ": " + cantidadFallas;
      parrafos.add(parrafo);
    }

    return parrafos;
  }

  private int cantidadFallas(Heladera heladera, LocalDate fechaInicio, LocalDate fechaFin){
    return (int) heladera.getHistorialEstados().stream().filter(estado ->
        estado.esUnaFalla() && ManejoFechas.fechaEnRango(estado.getFechaCambio(), fechaInicio, fechaFin)
    ).count();
  }

  public String titulo() {
    return "Cantidad de fallas por heladera";
  }
}
