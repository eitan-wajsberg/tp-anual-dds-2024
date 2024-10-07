package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import io.javalin.http.Context;
import lombok.Data;

@Data
public class DocumentoDTO implements DTO {
  private String tipoDocumento;
  private String nroDocumento;
  private String rutaHbs;

  @Override
  public void obtenerFormulario(Context context, String rutaHbs) {
    this.setNroDocumento(context.formParam("nroDocumento"));
    this.setTipoDocumento(context.formParam("tipoDocumento"));
    this.setRutaHbs(rutaHbs);
  }

  @Override
  public Object convertirAEntidad() {
    if (!this.validarDocumento(this.nroDocumento, TipoDocumento.valueOf(this.tipoDocumento))) {
      throw new ValidacionFormularioException("Numero de documento inválido", rutaHbs);
    }

    return this;
  }

  public boolean validarDocumento(String nroDocumento, TipoDocumento tipoDocumento) {
    if (nroDocumento == null || nroDocumento.isEmpty()) {
      return false;
    }

    switch (tipoDocumento) {
      case DNI:
        return validarDNI(nroDocumento);
      case LC:
        return validarLibretaCivica(nroDocumento);
      case LE:
        return validarLibretaEnrolamiento(nroDocumento);
      case PASAPORTE:
        return validarPasaporte(nroDocumento);
      default:
        throw new IllegalArgumentException("Tipo de documento no soportado");
    }
  }

  private boolean validarDNI(String nroDocumento) {
    return nroDocumento.matches("^\\d{7,8}$");
  }

  private boolean validarLibretaCivica(String nroDocumento) {
    return nroDocumento.matches("^\\d{7,8}$");
  }

  private boolean validarLibretaEnrolamiento(String nroDocumento) {
    return nroDocumento.matches("^\\d{7,8}$");
  }

  private boolean validarPasaporte(String nroDocumento) {
    return nroDocumento.matches("^[a-zA-Z0-9]{6,9}$");
  }
}
