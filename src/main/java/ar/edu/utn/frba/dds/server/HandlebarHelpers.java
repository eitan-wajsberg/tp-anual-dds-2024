package ar.edu.utn.frba.dds.server;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import java.io.IOException;

public class HandlebarHelpers {
  public static void registerHelpers(Handlebars handlebars) {

    // FIXME: No compara correctamente los argumentos, sospecho que el error esta en el return
    handlebars.registerHelper("ifEqual", (Helper<String>) (arg1, options) -> {
      String arg2 = options.param(0);
      System.out.println("Comparando: " + arg1 + " con " + arg2);
      if (arg1 != null && arg1.equals(arg2)) {
        System.out.println("Coinciden!");
        return options.fn(options.context);
      }

      return null;
    });
  }
}
