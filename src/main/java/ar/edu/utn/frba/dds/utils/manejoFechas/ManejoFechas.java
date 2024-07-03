package ar.edu.utn.frba.dds.utils.manejoFechas;

import java.time.LocalDate;

public class ManejoFechas {
  public static boolean fechaEnRango(LocalDate fecha, LocalDate fechaInicio, LocalDate fechaFin) {
    return (fecha.isEqual(fechaInicio) || fecha.isAfter(fechaFin))
        && (fecha.isEqual(fechaFin) || fecha.isBefore(fechaFin));
  }
}