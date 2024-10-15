package ar.edu.utn.frba.dds.utils.manejos;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.TipoDocumento;

public class ManejoDocumentos {
  public static boolean validarDocumento(String nroDocumento, TipoDocumento tipoDocumento) {
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

  private static boolean validarDNI(String nroDocumento) {
    return nroDocumento.matches("^\\d{7,8}$");
  }

  private static boolean validarLibretaCivica(String nroDocumento) {
    return nroDocumento.matches("^\\d{7,8}$");
  }

  private static boolean validarLibretaEnrolamiento(String nroDocumento) {
    return nroDocumento.matches("^\\d{7,8}$");
  }

  private static boolean validarPasaporte(String nroDocumento) {
    return nroDocumento.matches("^[a-zA-Z0-9]{6,9}$");
  }
}
