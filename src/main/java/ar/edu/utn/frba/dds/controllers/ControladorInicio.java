package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;

public class ControladorInicio {

  private static final Map<String, String> RUTAS = new HashMap<>();

  static {
    RUTAS.put(TipoRol.PERSONA_HUMANA.name(), PrettyProperties.getInstance().propertyFromName("hbs_inicio_humano"));
    RUTAS.put(TipoRol.PERSONA_JURIDICA.name(), PrettyProperties.getInstance().propertyFromName("hbs_inicio_juridico"));
    RUTAS.put(TipoRol.ADMIN.name(), PrettyProperties.getInstance().propertyFromName("hbs_inicio_admin"));
  }

  public void create(Context context) {
    String tipoCuenta = context.sessionAttribute("tipoCuenta");
    String rutaHbs = RUTAS.getOrDefault(tipoCuenta, PrettyProperties.getInstance().propertyFromName("hbs_inicio_default"));
    context.render(rutaHbs);
  }
}
