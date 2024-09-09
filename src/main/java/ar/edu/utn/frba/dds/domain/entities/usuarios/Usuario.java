package ar.edu.utn.frba.dds.domain.entities.usuarios;

import ar.edu.utn.frba.dds.domain.entities.validador.ValidadorDeClave;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name = "usuario")
@NoArgsConstructor
public class Usuario {
  @Id @GeneratedValue
  private Long id;
  @Column(name="nombre")
  private String nombre;
  @Column(name="clave")
  private String clave;
  @ManyToOne
  @JoinColumn(name="rol_id", referencedColumnName = "id")
  private Rol rol;

  public Usuario(String nombre) {
    this.nombre = nombre;
  }

  public void cambiarClave(String clave, ValidadorDeClave validador) throws RuntimeException {
    String claveAuxiliar = this.clave;
    this.clave = clave;
    if (!validador.validar(clave)) {
      this.clave = claveAuxiliar;
    }
  }

  public void cambiarClave(String clave){
    this.clave = clave;
  }

}
