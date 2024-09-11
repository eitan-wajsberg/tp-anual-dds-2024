package ar.edu.utn.frba.dds.domain.entities.personasVulnerables;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.HeladeraInactivaException;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.Tarjeta;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.UsoDeTarjeta;
import ar.edu.utn.frba.dds.domain.entities.tarjetas.UsoMaximoDeTarjetasPorDiaExcedidoException;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.entities.viandas.Vianda;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "persona_vulnerable")
public class PersonaVulnerable {

  @Id @GeneratedValue
  private long id;

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "fechaDeNacimiento", columnDefinition = "DATE", nullable = false)
  private LocalDate fechaDeNacimiento;

  @Column(name = "fechaDeRegistro", columnDefinition = "DATE", nullable = false)
  private LocalDate fechaDeRegistro;

  @Embedded
  private Direccion direccion; //Si posee

  @Column(name="menoresAcargo")
  private Integer menoresAcargo; //Si posee

  @Enumerated(EnumType.STRING)
  @Column(name="tipoDocumento") //Si posee
  private TipoDocumento tipoDocumento;

  @Column(name="nroDocumento")
  private String nroDocumento;

  @ManyToOne
  @JoinColumn(name = "personaQueLoRegistro_id", referencedColumnName = "id", nullable = false)
  private PersonaHumana personaQueLoRegistro;

  @OneToMany
  @JoinColumn(name = "personaVulnerable_id", referencedColumnName = "id")
  private List<Tarjeta> tarjetas;

  @OneToOne
  @JoinColumn(name = "tarjeta_id", referencedColumnName = "id")
  private Tarjeta tarjetaEnUso;

  public PersonaVulnerable(String nombre, LocalDate fechaDeNacimiento, LocalDate fechaDeRegistro, Direccion direccion, Integer menoresAcargo, String nroDocumento, TipoDocumento tipoDocumento, PersonaHumana personaQueLoRegistro) {
    this.nombre = nombre;
    this.fechaDeNacimiento = fechaDeNacimiento;
    this.fechaDeRegistro = fechaDeRegistro;
    this.direccion = direccion;
    this.menoresAcargo = menoresAcargo;
    this.tipoDocumento = tipoDocumento;
    this.nroDocumento = nroDocumento;
    this.personaQueLoRegistro = personaQueLoRegistro;
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
