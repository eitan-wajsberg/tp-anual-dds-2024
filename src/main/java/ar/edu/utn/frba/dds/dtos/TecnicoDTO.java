package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.contacto.Contacto;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.manejos.ManejoDocumentos;
import io.javalin.http.Context;
import java.util.Objects;
import lombok.Data;

@Data
public class TecnicoDTO implements DTO {
  private String nombre;
  private String apellido;
  private String cuil;
  private Integer radioMaximoParaSerAvisado;
  private String nroDocumento;
  private String tipoDocumento;
  private String rutaHbs;
  private DireccionDTO direccionDTO;
  private ContactoDTO contactoDTO;

  @Override
  public void obtenerFormulario(Context context, String rutaHbs) {
    this.setNombre(context.formParam("nombre"));
    this.setApellido(context.formParam("apellido"));
    this.setCuil(context.formParam("cuil"));
    this.setRadioMaximoParaSerAvisado(Integer.parseInt(Objects.requireNonNull(context.formParam("radio"))));
    this.setNroDocumento(context.formParam("nroDocumento"));
    this.setTipoDocumento(context.formParam("tipoDocumento"));
    this.setRutaHbs(rutaHbs);
    this.direccionDTO = new DireccionDTO();
    this.direccionDTO.obtenerFormulario(context, rutaHbs);
    this.contactoDTO = new ContactoDTO();
    this.contactoDTO.obtenerFormulario(context, rutaHbs);
  }

  @Override
  public Object convertirAEntidad() {
    if (this.nombre == null || this.nombre.isEmpty()) {
      throw new ValidacionFormularioException("El nombre es obligatorio.", rutaHbs);
    }
    if (this.apellido == null || this.apellido.isEmpty()) {
      throw new ValidacionFormularioException("El apellido es obligatorio.", rutaHbs);
    }
    if (this.cuil == null || this.cuil.isEmpty()) {
      throw new ValidacionFormularioException("El CUIL es obligatorio.", rutaHbs);
    }
    if (!ManejoDocumentos.validarDocumento(this.nroDocumento, TipoDocumento.valueOf(this.tipoDocumento))) {
      throw new ValidacionFormularioException("Numero de documento inválido", rutaHbs);
    }

    if (this.nombre.length() < 2 || this.nombre.length() > 50) {
      throw new ValidacionFormularioException("El nombre debe tener entre 2 y 50 caracteres.", rutaHbs);
    }
    if (this.apellido.length() < 2 || this.apellido.length() > 50) {
      throw new ValidacionFormularioException("El apellido debe tener entre 2 y 50 caracteres.", rutaHbs);
    }

    if (!this.cuil.matches("^\\d{2}-\\d{8}-\\d{1}$")) {
      throw new ValidacionFormularioException("El formato del CUIL es incorrecto. Debe ser XX-XXXXXXXX-X.", rutaHbs);
    }

    if (this.radioMaximoParaSerAvisado == null || this.radioMaximoParaSerAvisado <= 0) {
      throw new ValidacionFormularioException("El radio máximo debe ser un número positivo.", rutaHbs);
    }

    Direccion direccion = (Direccion) direccionDTO.convertirAEntidad();
    Contacto contacto = (Contacto) contactoDTO.convertirAEntidad();

    // TODO: Otorgarle un usuario
    return Tecnico.builder()
        .nombre(this.nombre)
        .apellido(this.apellido)
        .cuil(this.cuil)
        .nroDocumento(nroDocumento)
        .tipoDocumento(TipoDocumento.valueOf(tipoDocumento))
        .distanciaMaximaEnKmParaSerAvisado(this.radioMaximoParaSerAvisado)
        .direccion(direccion)
        .contacto(contacto)
        .build();
  }
}

