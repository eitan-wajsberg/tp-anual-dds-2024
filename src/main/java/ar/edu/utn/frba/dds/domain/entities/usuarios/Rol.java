package ar.edu.utn.frba.dds.domain.entities.usuarios;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Getter
@Entity
@Table(name ="rol")
public class Rol {
  @Id @GeneratedValue
  private Long id;
  @Setter
  @Column(name="nombre", nullable = false)
  private String nombre;
  @ManyToMany
  @JoinTable(
      name = "rol_permiso",
      joinColumns = @JoinColumn(name = "rol_id",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "permiso_id", referencedColumnName = "id")
  )
  private Set<Permiso> permisos;

  public Rol() {
    this.permisos = new HashSet<>();
  }

  public boolean tenesPermiso(Permiso permiso){
    return permisos.contains(permiso);
  }

  public void agregarPermiso(Permiso permiso){
    permisos.add(permiso);
  }

  public void quitarPermiso(Permiso permiso){
    permisos.remove(permiso);
  }
}
