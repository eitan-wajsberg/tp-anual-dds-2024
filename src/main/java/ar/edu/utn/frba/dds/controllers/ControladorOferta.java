package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.domain.entities.oferta.Oferta;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.PersonaJuridica;
import ar.edu.utn.frba.dds.domain.entities.personasJuridicas.Rubro;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioOferta;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioRubro;
import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioTecnicos;
import ar.edu.utn.frba.dds.dtos.TecnicoDTO;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import ar.edu.utn.frba.dds.utils.javalin.ICrudViewsHandler;
import ar.edu.utn.frba.dds.utils.javalin.PrettyProperties;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.http.HttpStatus;

public class ControladorOferta implements WithSimplePersistenceUnit, ICrudViewsHandler {
  private RepositorioOferta repositorioOferta;
  private RepositorioRubro repositorioRubro;
  private final String rutaListadoOfertas = "colaboraciones/ofertas-personaJuridica.hbs";
  private final String rutaForm = "colaboraciones/ofertas-agregarOferta.hbs";

  public ControladorOferta(RepositorioOferta repositorioOferta, RepositorioRubro repositorioRubro) {
    this.repositorioOferta = repositorioOferta;
    this.repositorioRubro = repositorioRubro;
  }

  @Override
  public void index(Context context) { // juridica busca sus ofertas.
    List<Oferta> ofertas = this.repositorioOferta.buscarTodos(Oferta.class);

    Map<String, Object> model = new HashMap<>();
    model.put("ofertas", ofertas);
    model.put("titulo", "Listado de ofertas");

    context.render(rutaListadoOfertas);
  }

  @Override
  public void show(Context context) {
    // TODO
  }

  @Override
  public void create(Context context) {
    List <Rubro> rubros = this.repositorioRubro.buscarTodos(Rubro.class);
    Map<String, Object> model = new HashMap<>();
    model.put("rubros", rubros);
    context.render(rutaForm, model);

  }

  @Override
  public void save(Context context) {
      String pathImagen = null;

      UploadedFile uploadedFile = context.uploadedFile("imagen");
      if (uploadedFile != null) {
        String fileName = uploadedFile.filename();
        pathImagen = "uploads/" + fileName; // Ruta donde se guardará la imagen

        // Crear la carpeta si no existe
        File uploadsDir = new File("uploads");
        if (!uploadsDir.exists()) {
          uploadsDir.mkdirs();
        }

        // Guardar el archivo
        try (InputStream inputStream = uploadedFile.content();
             FileOutputStream outputStream = new FileOutputStream(new File(uploadsDir, fileName))) {
          byte[] buffer = new byte[1024];
          int bytesRead;
          while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
          }
        } catch (IOException e) {
          context.status(HttpStatus.INTERNAL_SERVER_ERROR_500).result("Error al guardar la imagen");
          return;
        }
      }

      // Crear la oferta y guardar en la base de datos
      Oferta oferta = Oferta
          .builder()
          .nombre(context.formParam("nombre"))
          .rubro(repositorioRubro.buscarPorNombre(context.formParam("categoria")))
          .imagen(pathImagen)
          .cantidadPuntosNecesarios(Float.parseFloat(context.formParam("puntos")))
          .organizacion(new PersonaJuridica()) //FIXME: implementar cuando se defina cómo se guarda la sesion
          .build();

      withTransaction(()-> repositorioOferta.guardar(oferta));


    context.status(HttpStatus.CREATED_201).result("Oferta creada");
    context.redirect("/colaboraciones/ofertas");

  }

  @Override
  public void edit(Context context) {
    // permitir editar una oferta o no?
    // TODO
  }

  @Override
  public void update(Context context) {
    // TODO
  }

  @Override
  public void delete(Context context) {
    // TODO
  }
}
