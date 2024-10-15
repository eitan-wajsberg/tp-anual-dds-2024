package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;

public class ControladorInicio {

  private static final Map<String, String> RUTAS = new HashMap<>();

  static {
    RUTAS.put(TipoRol.PERSONA_HUMANA.name(), "pantallaInicioHumana.hbs");
    RUTAS.put(TipoRol.PERSONA_JURIDICA.name(), "pantallaInicioJuridica.hbs");
    RUTAS.put(TipoRol.ADMIN.name(), "admin/adminInicio.hbs");
  }

  public void create(Context context) {
    String tipoCuenta = context.sessionAttribute("tipoCuenta");
    String rutaHbs = RUTAS.getOrDefault(tipoCuenta, "pantallaInicio.hbs");
    context.render(rutaHbs);
  }
}
