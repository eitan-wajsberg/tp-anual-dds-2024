package ar.edu.utn.frba.dds.domain.entities.personasVulnerables;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.HeladeraInactivaException;
import ar.edu.utn.frba.dds.domain.entities.heladeras.HeladeraVirtualmenteVaciaException;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.Documento;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.UsoDeTarjeta;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.UsoMaximoDeTarjetasPorDiaExcedidoException;
import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
  private List<Tarjeta> tarjetas;
  private Tarjeta tarjetaEnUso;

  public PersonaVulnerable(String nombre, LocalDate fechaDeNacimiento, LocalDate fechaDeRegistro, String direccion, Integer menoresAcargo, Documento documento, PersonaHumana donanteQueLoRegistro) {
    this.nombre = nombre;
    this.fechaDeNacimiento = fechaDeNacimiento;
    this.fechaDeRegistro = fechaDeRegistro;
    this.direccion = direccion;
    this.menoresAcargo = menoresAcargo;
    this.documento = documento;
    this.donanteQueLoRegistro = donanteQueLoRegistro;
  }

  public void usarTarjeta(Heladera heladera, Vianda vianda) throws UsoMaximoDeTarjetasPorDiaExcedidoException, HeladeraInactivaException {
    // FIXME: Revisar si es correcto que este metodo reciba la vianda
    if (!heladera.estaActiva()) {
      throw new HeladeraInactivaException();
    }

    LocalDate hoy = LocalDate.now();
    int usosHoy = this.tarjetaEnUso.cantidadDeUsos(hoy);
    int maxUsosPermitidos = 4 + (2 * menoresAcargo);

    if (usosHoy < maxUsosPermitidos) {
      heladera.quitarVianda(vianda);
      tarjetaEnUso.agregarUso(new UsoDeTarjeta(LocalDateTime.now(), heladera));
    } else {
      throw new UsoMaximoDeTarjetasPorDiaExcedidoException();
    }
  }

  public void asignarTarjeta(Tarjeta tarjeta){
    darTarjetaDeBaja();
    this.tarjetas.add(tarjeta);
    this.tarjetaEnUso = tarjeta;
  }

  public void darTarjetaDeBaja(){
    if (this.tarjetaEnUso != null) {
      this.tarjetaEnUso.setFechaBaja(LocalDate.now());
    }
    this.tarjetaEnUso = null;
  }
}
