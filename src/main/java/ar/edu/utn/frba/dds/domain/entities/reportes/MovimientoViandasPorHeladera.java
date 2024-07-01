package ar.edu.utn.frba.dds.domain.entities.reportes;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes.AccionApertura;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioHeladera;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioPersonaHumana;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class MovimientoViandasPorHeladera implements Reporte {
  @Getter @Setter
  IRepositorioHeladera repositorioHeladera;

  public List<String> generarReporte(LocalDate fechaInicio, LocalDate fechaFin) {
    List<String> parrafos = new ArrayList<>();
    for (Heladera heladera : this.repositorioHeladera.listar()) {
      int cantidadViandasRetiradas = cantidadViandasSegunAccion(heladera, AccionApertura.QUITAR_VIANDA, fechaInicio, fechaFin);
      int cantidadViandasColocadas = cantidadViandasSegunAccion(heladera, AccionApertura.INGRESAR_VIANDA, fechaInicio, fechaFin);
      String parrafo = heladera.getNombre() + ": "
          + "\n   - Viandas retiradas: " + cantidadViandasRetiradas
          + "\n   - Viandas colocadas: " + cantidadViandasColocadas;
      parrafos.add(parrafo);
    }

    return parrafos;
  }

  private int cantidadViandasSegunAccion(Heladera heladera, AccionApertura accion, LocalDate fechaInicio, LocalDate fechaFin) {
    // como para meter o sacar cosas en la heladera primero hay que solicitarlas
    return (int) heladera.getSolicitudesDeApertura().stream().filter(sol ->
        sol.isAperturaConcretada() && sol.getAccion().equals(accion)
        && fechaEnRango(sol.getFecha().toLocalDate(), fechaInicio, fechaFin)
    ).count();
  }

  private static boolean fechaEnRango(LocalDate fecha, LocalDate fechaInicio, LocalDate fechaFin) {
    return (fecha.isEqual(fechaInicio) || fecha.isAfter(fechaFin))
        && (fecha.isEqual(fechaFin) || fecha.isBefore(fechaFin));
  }

  public String titulo() {
    return "Movimientos de viandas por heladera";
  }
}
