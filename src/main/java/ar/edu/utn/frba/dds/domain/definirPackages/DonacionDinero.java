package ar.edu.utn.frba.dds.domain.definirPackages;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

public class DonacionDinero {
  private float monto;
  private int frecuencia;
  private String unidadFrecuencia;
  private LocalDate fecha;
}
