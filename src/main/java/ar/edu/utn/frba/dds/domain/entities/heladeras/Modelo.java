package ar.edu.utn.frba.dds.domain.entities.heladeras;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name = "modelo")
@NoArgsConstructor
public class Modelo {
  @Id @GeneratedValue
  private Long id;

  @Column(name="modelo", nullable = false)
  private String modelo;

  @Column(name="temperaturaMinima", nullable = false)
  private float temperaturaMinima;

  @Column(name="temperaturaMaxima", nullable = false)
  private float temperaturaMaxima;

  public Modelo(String modelo, float temperaturaMinima, float temperaturaMaxima) {
    this.modelo = modelo;
    this.temperaturaMinima = temperaturaMinima;
    this.temperaturaMaxima = temperaturaMaxima;
  }
}
