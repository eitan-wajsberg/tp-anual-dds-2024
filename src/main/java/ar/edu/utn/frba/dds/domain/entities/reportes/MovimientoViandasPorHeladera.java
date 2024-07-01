package ar.edu.utn.frba.dds.domain.entities.reportes;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes.AccionApertura;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioHeladera;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioPersonaHumana;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovimientoViandasPorHeladera implements Reporte {
  IRepositorioHeladera repositorioHeladera;

  public List<String> generarReporte(LocalDate fechaInicio, LocalDate fechaFin) {
    List<String> parrafos = new ArrayList<>();
    for (Heladera heladera : this.repositorioHeladera.listar()) {
      // como para meter o sacar cosas en la heladera primero hay que solicitarlas
      int cantidadViandasRetiradas = cantidadViandasSegunAccion(heladera, AccionApertura.QUITAR_VIANDA);
      int cantidadViandasColocadas = cantidadViandasSegunAccion(heladera, AccionApertura.INGRESAR_VIANDA);
      String parrafo = heladera.getNombre() + ": "
          + "\n   - Viandas retiradas: " + cantidadViandasRetiradas
          + "\n   - Viandas colocadas: " + cantidadViandasColocadas;
      parrafos.add(parrafo);
    }

    return parrafos;
  }

  private int cantidadViandasSegunAccion(Heladera heladera, AccionApertura accion) {
    return (int) heladera.getSolicitudesDeApertura().stream().filter(sol ->
        sol.isAperturaConcretada() && sol.getAccion().equals(accion)
    ).count();
  }

  public String titulo() {
    return "Movimiento viandas por heladera";
  }
}
