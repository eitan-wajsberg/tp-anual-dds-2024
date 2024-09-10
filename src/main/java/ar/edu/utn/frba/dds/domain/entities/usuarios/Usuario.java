package ar.edu.utn.frba.dds.domain.entities.usuarios;

import ar.edu.utn.frba.dds.domain.entities.validador.ValidadorDeClave;
import java.util.Objects;
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
  @Column(name="nombre", nullable = false)
  private String nombre;
  @Column(name="clave", nullable = false)
  private String clave;
  @ManyToOne
  @JoinColumn(name="rol_id", referencedColumnName = "id", nullable = false)
  private Rol rol;

  public Usuario(String nombre) {
    this.nombre = nombre;
  }

  public void cambiarClave(String clave, ValidadorDeClave validador) throws RuntimeException {
    String hashOriginal = this.clave;
    String claveAuxiliarB = clave;
    this.clave = clave; // = funcionHash(this.clave)
    if (!validador.validar(clave)) {
      this.clave = hashOriginal; // = funcionHash(this.clave)
    }
  }

  public void cambiarClave(String clave){
    this.clave = clave;
  }

  public boolean verificarClave(String claveIngresada){
    return Objects.equals(claveIngresada, this.clave);
  }

}
