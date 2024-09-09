package ar.edu.utn.frba.dds.domain.entities.heladeras.suscripciones;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="sugerencia_heladeras")
public class SugerenciaHeladera {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name="fechaRealizacion")
  private LocalDate fechaRealizacion;

  @ManyToMany
  @JoinTable(
      name = "sugerencia_distribucion",
      joinColumns = @JoinColumn(name = "id_sugerencia_heladeras",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "id_heladera", referencedColumnName = "id")
  )
  private List<Heladera> heladerasSugeridas;
  //private List<Heladera> heladerasEscogidas;
}
