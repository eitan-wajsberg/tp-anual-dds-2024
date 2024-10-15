package ar.edu.utn.frba.dds.domain.entities.donacionesDinero;

import ar.edu.utn.frba.dds.domain.entities.Contribucion;
import ar.edu.utn.frba.dds.domain.entities.ReconocimientoTrabajoRealizado;
import ar.edu.utn.frba.dds.domain.entities.TipoContribucion;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.PersonaJuridica;
import ar.edu.utn.frba.dds.dtos.DonacionDineroDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.manejos.CamposObligatoriosVacios;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donacion_dinero")
public class DonacionDinero implements Contribucion {

  @Id  @GeneratedValue
  private Long id;

  @Column(name = "monto", nullable = false)
  private float monto;

  @Enumerated(EnumType.STRING)
  @Column(name = "unidadFrecuencia", nullable = false)
  private UnidadFrecuencia unidadFrecuencia;

  @Column(name = "fecha", columnDefinition = "DATE", nullable = false)
  private LocalDate fecha;

  @ManyToOne
  @JoinColumn(name = "id_personaHumana", referencedColumnName = "id")
  private PersonaHumana personaHumana;

  @ManyToOne
  @JoinColumn(name = "id_personaJuridica", referencedColumnName = "id")
  private PersonaJuridica personaJuridica;

  public float calcularPuntaje() {
    float coeficiente = ReconocimientoTrabajoRealizado
        .obtenerCoeficientes("coeficientePesosDonados");
    return monto * coeficiente;
  }

  public TipoContribucion obtenerTipoContribucion() {
    return TipoContribucion.DONACION_DINERO;
  }

  public LocalDate obtenerFechaRegistro() {
    return this.fecha;
  }

  public static DonacionDinero fromDTO(DonacionDineroDTO dto) {
    validarCamposObligatorios(dto);
    validarMonto(dto);
    validarFecha(dto);

    return DonacionDinero.builder()
        .monto(dto.getMonto())
        .unidadFrecuencia(UnidadFrecuencia.valueOf(dto.getUnidadFrecuencia().toUpperCase()))
        .fecha(LocalDate.parse(dto.getFecha()))
        .build();
  }

  public void actualizarFromDto(DonacionDineroDTO dto) {
    validarCamposObligatorios(dto);
    validarMonto(dto);
    validarFecha(dto);

    this.monto = dto.getMonto();
    this.unidadFrecuencia = UnidadFrecuencia.valueOf(dto.getUnidadFrecuencia());
    this.fecha = LocalDate.parse(dto.getFecha());

  }

  private static void validarCamposObligatorios(DonacionDineroDTO dto) {
    CamposObligatoriosVacios.validarCampos(
        Pair.of("monto", String.valueOf(dto.getMonto())),
        Pair.of("unidad de frecuencia", dto.getUnidadFrecuencia()),
        Pair.of("fecha", dto.getFecha())
    );
  }

  private static void validarMonto(DonacionDineroDTO dto) {
    if (dto.getMonto() <= 0) {
      throw new ValidacionFormularioException("El monto debe ser mayor que cero.");
    }
  }

  private static void validarFecha(DonacionDineroDTO dto) {
    try {
      LocalDate.parse(dto.getFecha());
    } catch (DateTimeParseException e) {
      throw new ValidacionFormularioException("Formato de fecha incorrecto. Debe ser yyyy-MM-dd.");
    }
  }
}
