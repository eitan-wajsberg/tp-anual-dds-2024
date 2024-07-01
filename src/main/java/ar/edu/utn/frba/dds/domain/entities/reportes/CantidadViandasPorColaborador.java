package ar.edu.utn.frba.dds.domain.entities.reportes;

import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioPersonaHumana;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class CantidadViandasPorColaborador implements Reporte {
  @Getter @Setter
  private IRepositorioPersonaHumana repositorioColaboradores;

  public List<String> generarReporte(LocalDate fechaInicio, LocalDate fechaFin) {
    List<String> parrafos = new ArrayList<>();
    for (PersonaHumana persona : repositorioColaboradores.listar()) {
      int cantidadViandas = (int) persona.getContribuciones().stream().filter(con ->
          con.obtenerTipoContribucion() == TipoContribucion.DONACION_VIANDA
          && fechaEnRango(con.obtenerFechaRegistro(), fechaInicio, fechaFin)
      ).count();
      String parrafo = "Cantidad de viandas donadas de " + persona.getNombre() + ": " + cantidadViandas;
      parrafos.add(parrafo);
    }
    return parrafos;
  }

  private static boolean fechaEnRango(LocalDate fecha, LocalDate fechaInicio, LocalDate fechaFin) {
    return (fecha.isEqual(fechaInicio) || fecha.isAfter(fechaFin))
        && (fecha.isEqual(fechaFin) || fecha.isBefore(fechaFin));
  }

  public String titulo() {
    return "Cantidad de viandas por colaborador";
  }
}
