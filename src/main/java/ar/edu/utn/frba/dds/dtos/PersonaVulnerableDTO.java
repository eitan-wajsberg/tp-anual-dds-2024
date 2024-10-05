package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Data;

@Data
public class PersonaVulnerableDTO implements DTO {
  private String nombre;
  private String apellido;
  private LocalDate fechaDeNacimiento;
  private Integer menoresAcargo;
  private String tipoDocumento;
  private String nroDocumento;
  private String calle;
  private Integer altura;
  private String provincia;
  private String municipio;
  private String rutaHbs;

  @Override
  public void obtenerFormulario(Context context, String rutaHbs) {
    this.setNombre(context.formParam("nombre"));
    this.setApellido(context.formParam("apellido"));
    this.setFechaDeNacimiento(LocalDate.parse(Objects.requireNonNull(context.formParam("fecha"))));
    this.setMenoresAcargo(Integer.parseInt(Objects.requireNonNull(context.formParam("cantidadMenores"))));
    this.setNroDocumento(context.formParam("nroDocumento"));
    this.setTipoDocumento(context.formParam("tipoDocumento"));
    this.setCalle(context.formParam("calle"));
    this.setAltura(Integer.parseInt(Objects.requireNonNull(context.formParam("altura"))));
    this.setProvincia(context.formParam("provincia"));
    this.setMunicipio(context.formParam("municipio"));
    this.setRutaHbs(rutaHbs);
  }

  @Override
  public Object convertirAEntidad() {
    if (this.nombre.isEmpty() || this.apellido.isEmpty() || this.fechaDeNacimiento == null) {
      throw new ValidacionFormularioException("Ciertos campos que son obligatorios se encuentran vacíos", rutaHbs);
    }

    if (this.menoresAcargo < 0) {
      throw new ValidacionFormularioException("La cantidad de menores a cargo no puede ser negativa", rutaHbs);
    }

    if (this.nroDocumento.matches("^[a-zA-Z0-9-]{5,50}$")) {
      throw new ValidacionFormularioException("Numero de documento inválido", rutaHbs);
    }

    if (this.fechaDeNacimiento.isBefore(LocalDate.now())) {
      throw new ValidacionFormularioException("Fecha de nacimiento inválida", rutaHbs);
    }

    return PersonaVulnerable.builder()
        .nombre(this.nombre)
        .apellido(this.apellido)
        .fechaDeNacimiento(fechaDeNacimiento)
        .menoresAcargo(this.menoresAcargo)
        .tipoDocumento(TipoDocumento.valueOf(this.tipoDocumento))
        .nroDocumento(this.nroDocumento)
        .build();
  }
}