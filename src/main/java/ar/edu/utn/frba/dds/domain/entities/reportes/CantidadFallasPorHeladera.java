package ar.edu.utn.frba.dds.domain.entities.reportes;

import ar.edu.utn.frba.dds.domain.entities.heladeras.EstadoHeladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioHeladera;
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
    for (Heladera heladera : repositorioHeladera.listar()) {
      int cantidadFallas = (int) heladera.getHistorialEstados().stream().filter(estado ->
          estado.esUnaFalla() && fechaEnRango(estado.getFechaCambio(), fechaInicio, fechaFin)
      ).count();
      String parrafo = "Heladera " + heladera.getNombre() + "Id: " + heladera.getId() + ": " + cantidadFallas;
      parrafos.add(parrafo);
    }
    return parrafos;
  }

  private static boolean fechaEnRango(LocalDate fecha, LocalDate fechaInicio, LocalDate fechaFin) {
    return (fecha.isEqual(fechaInicio) || fecha.isAfter(fechaFin))
        && (fecha.isEqual(fechaFin) || fecha.isBefore(fechaFin));
  }

  public String titulo() {
    return "Cantidad de fallas por heladera";
  }
}
