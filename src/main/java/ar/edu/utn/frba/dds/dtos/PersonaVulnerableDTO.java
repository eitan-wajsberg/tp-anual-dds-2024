package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.personasVulnerables.PersonaVulnerable;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import io.javalin.http.Context;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PersonaVulnerableDTO implements DTO {
  private String nombre;
  private String apellido;
  private String fechaDeNacimiento;
  private String menoresAcargo;
  private String rutaHbs;
  private DocumentoDTO documentoDTO;
  private DireccionDTO direccionDTO;

  @Override
  public void obtenerFormulario(Context context, String rutaHbs) {
    this.setNombre(context.formParam("nombre"));
    this.setApellido(context.formParam("apellido"));
    this.setFechaDeNacimiento(context.formParam("fecha"));
    this.setMenoresAcargo(context.formParam("cantidadMenores"));
    this.setRutaHbs(rutaHbs);
    this.documentoDTO = new DocumentoDTO();
    this.documentoDTO.obtenerFormulario(context, rutaHbs);
    this.direccionDTO = new DireccionDTO();
    this.direccionDTO.obtenerFormulario(context, rutaHbs);
  }

  @Override
  public Object convertirAEntidad() {
    if (this.nombre.isEmpty() || this.apellido.isEmpty() || this.fechaDeNacimiento == null) {
      throw new ValidacionFormularioException("Ciertos campos que son obligatorios se encuentran vacíos", rutaHbs);
    }

    if (!this.fechaDeNacimiento.isEmpty()) {
      if (LocalDate.parse(this.fechaDeNacimiento).isAfter(LocalDate.now())) {
        throw new ValidacionFormularioException("Fecha de nacimiento inválida", rutaHbs);
      }
    }

    Direccion direccion = (Direccion) direccionDTO.convertirAEntidad();
    DocumentoDTO documento = (DocumentoDTO) documentoDTO.convertirAEntidad();

    return PersonaVulnerable.builder()
        .nombre(this.nombre)
        .apellido(this.apellido)
        .fechaDeNacimiento(LocalDate.parse(this.fechaDeNacimiento))
        .menoresAcargo(obtenerMenoresACargo())
        .tipoDocumento(TipoDocumento.valueOf(documento.getTipoDocumento()))
        .nroDocumento(documento.getNroDocumento())
        .direccion(direccion)
        .build();
  }

  private int obtenerMenoresACargo() {
    if (this.menoresAcargo.isEmpty()) {
      return 0;
    }

    int menores;
    try {
      menores = Integer.parseInt(this.menoresAcargo);
    } catch (NumberFormatException e) {
      throw new ValidacionFormularioException("El campo de menores a cargo debe ser un número", rutaHbs);
    }

    if (menores < 0) {
      throw new ValidacionFormularioException("La cantidad de menores a cargo no puede ser negativa", rutaHbs);
    }

    return menores;
  }
}

