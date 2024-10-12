package ar.edu.utn.frba.dds.controllers;

import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import java.io.File;
import java.util.ArrayList;

public class ControladorReportes {
  private final String rutaReportes = "admin/adminReportes.hbs";
  private final String rutaBaseReportes = "src/main/resources/public/reportes";


  public void index(Context context) {
    List<Reporte> reportes = new ArrayList<>();

    // Lee las carpetas en la ruta base de reportes
    File baseDir = new File(rutaBaseReportes);
    File[] carpetas = baseDir.listFiles(File::isDirectory);

    if (carpetas != null) {
      for (File carpeta : carpetas) {
        // Asegúrate de que los nombres de los archivos sean correctos
        Reporte reporte = new Reporte();
        reporte.setFecha(carpeta.getName()); // O usa un formato de fecha según sea necesario

        // Asigna las rutas a cada archivo
        reporte.setRutaFallas("/reportes/" + carpeta.getName() + "/fallas.pdf");
        reporte.setRutaViandasRetiradas("/reportes/" + carpeta.getName() + "/viandasRetiradas.pdf");
        reporte.setRutaViandasDonadas("/reportes/" + carpeta.getName() + "/viandasDonadas.pdf");

        reportes.add(reporte);
      }
    }

    Map<String, Object> model = new HashMap<>();
    model.put("reportes", reportes);

    context.render(rutaReportes, model); // Asegúrate de pasar el modelo aquí
  }

  @Data
  public static class Reporte {
    private String fecha;
    private String rutaFallas;
    private String rutaViandasRetiradas;
    private String rutaViandasDonadas;
  }
}
