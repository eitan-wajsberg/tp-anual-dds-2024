package ar.edu.utn.frba.dds.domain.entities.personasVulnerables;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonaVulnerable {

  private String nombre;
  private LocalDate fechaDeNacimiento;
  private LocalDate fechaDeRegistro;
  private String direccion;
  private Integer menoresAcargo;
  private Documento documento;
  private PersonaHumana donanteQueLoRegistro;
  private Tarjeta tarjeta;

  public PersonaVulnerable(String nombre, LocalDate fechaDeNacimiento, LocalDate fechaDeRegistro, String direccion, Integer menoresAcargo, Documento documento, PersonaHumana donanteQueLoRegistro) {
    this.nombre = nombre;
    this.fechaDeNacimiento = fechaDeNacimiento;
    this.fechaDeRegistro = fechaDeRegistro;
    this.direccion = direccion;
    this.menoresAcargo = menoresAcargo;
    this.documento = documento;
    this.donanteQueLoRegistro = donanteQueLoRegistro;
  }

  public void usarTarjeta(Heladera heladera, Vianda vianda) throws UsoMaximoDeTarjetasPorDiaExcedidoException {
    // FIXME: Revisar si la es correcto que este metodo reciba la vianda
    int usosHoy = contarUsosHoy();
    int maxUsosPermitidos = 4 + (2 * menoresAcargo);

    if (usosHoy < maxUsosPermitidos) {
      tarjeta.agregarUso(new UsoDeTarjeta(LocalDateTime.now(), heladera));
      heladera.quitarVianda(vianda);
    } else {
      throw new UsoMaximoDeTarjetasPorDiaExcedidoException();
    }
  }

  private int contarUsosHoy() {
    LocalDate hoy = LocalDate.now();
    return (int) tarjeta.getHistorialUsos().stream()
        .filter(uso -> uso.getFecha().toLocalDate().isEqual(hoy))
        .count();
  }
}
