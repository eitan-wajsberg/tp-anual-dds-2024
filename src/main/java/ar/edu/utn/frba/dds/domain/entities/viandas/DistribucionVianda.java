package ar.edu.utn.frba.dds.domain.entities.viandas;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name="distribucion_vianda")
public class DistribucionVianda implements Contribucion {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_heladeraOrigen", referencedColumnName = "id")
  private Heladera heladeraOrigen;

  @ManyToOne
  @JoinColumn(name = "id_heladeraDestino", referencedColumnName = "id")
  private Heladera heladeraDestino;

  @Column(name="cantidadViandas")
  private int cantidadViandas;

  @Column(name="motivo", columnDefinition = "TEXT")
  private String motivo;

  @Column(name="fecha", columnDefinition = "DATE")
  private LocalDate fecha;

  @Column(name="fecha", columnDefinition = "BIT(1)")
  private boolean terminada;

  @ManyToMany
  @JoinTable(
      name = "vianda_por_distribucion",
      joinColumns = @JoinColumn(name = "id_distribucion_vianda",
          referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "id_vianda", referencedColumnName = "id")
  )
  private List<Vianda> viandasAMover;

  public DistribucionVianda(Heladera heladeraOrigen, Heladera heladeraDestino, int cantidadViandas, String motivo, LocalDate fecha) {
    this.heladeraOrigen = heladeraOrigen;
    this.heladeraDestino = heladeraDestino;
    this.cantidadViandas = cantidadViandas;
    this.motivo = motivo;
    this.fecha = fecha;
  }

  public DistribucionVianda(LocalDate fecha, int cantidadViandas){
    this.fecha = fecha;
    this.cantidadViandas = cantidadViandas;
  }

  public void distribuir(List<Vianda> viandas) {
    heladeraOrigen.quitarViandas(viandas);
    heladeraDestino.ingresarViandas(viandas);
    // FIXME: Esto sigue siendo asi? Los metodos estos de quitar viandas en plural no deberiamos cambiarlos?
  }

  public float calcularPuntaje() {
    float coeficiente = ReconocimientoTrabajoRealizado.obtenerCoeficientes("coeficienteViandasDistribuidas");
    return cantidadViandas * coeficiente;
  }

  public TipoContribucion obtenerTipoContribucion() {
    return TipoContribucion.DISTRIBUCION_VIANDAS;
  }

  public LocalDate obtenerFechaRegistro() {
    return this.fecha;
  }

  @Override
  public boolean equals(Object o){
    if (o == this) {
      return true;
    }

    if (!(o instanceof DistribucionVianda)) {
      return false;
    }

    DistribucionVianda distribucion = (DistribucionVianda) o;

    return this.fecha.equals(distribucion.fecha)
        && this.cantidadViandas == distribucion.cantidadViandas;
  }
}
