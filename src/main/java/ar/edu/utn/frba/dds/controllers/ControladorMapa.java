package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.GsonFactory;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.usuarios.TipoRol;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioHeladera;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioSuscripcion;
import com.google.gson.Gson;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControladorMapa {
  private RepositorioHeladera repositorioHeladera;
  private RepositorioSuscripcion repositorioSuscripcion;
  private Repositorio repositorioPersonaJuridica;
  private final String rutaListadoHbs = "heladeras/mapa.hbs";
  private final Gson gson = GsonFactory.createGson();

  public ControladorMapa(RepositorioHeladera repositorioHeladera, Repositorio repositorioPersonaJuridica, RepositorioSuscripcion repositorioSuscripcion) {
    this.repositorioHeladera = repositorioHeladera;
    this.repositorioPersonaJuridica = repositorioPersonaJuridica;
    this.repositorioSuscripcion = repositorioSuscripcion;
  }

  public void index(Context context) {
    try {
      String terminoBusqueda = context.queryParam("q");  // El parámetro de búsqueda se espera en la query string
      List<Heladera> heladeras;
      String rol = context.sessionAttribute("rol");

      if (terminoBusqueda != null && !terminoBusqueda.isEmpty()) {
        // Si hay un término de búsqueda, realizar la búsqueda
        heladeras = this.repositorioHeladera.buscarPorNombreODireccion(terminoBusqueda);
      } else {
        // Si no hay búsqueda, obtener todas las heladeras
        heladeras = this.repositorioHeladera.buscarTodos(Heladera.class);
      }

      String jsonHeladeras = gson.toJson(heladeras);
      Map<String, Object> model = new HashMap<>();
      model.put("heladeras", heladeras);
      model.put("jsonHeladeras", jsonHeladeras);
      model.put("buscadorMapa", true);

      if (TipoRol.valueOf(rol).equals(TipoRol.PERSONA_HUMANA)) {
        model.put("mostrarPersonaHumana", true);
      } else {
        model.put("mostrarPersonaJuridica", true);
      }
      context.render(rutaListadoHbs, model);
    } catch (Exception e) {
      e.printStackTrace();
      context.status(500).result("Error interno del servidor");
    }
  }
}
