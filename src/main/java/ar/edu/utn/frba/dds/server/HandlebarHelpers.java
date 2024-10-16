package ar.edu.utn.frba.dds.server;

import com.github.jknack.handlebars.Handlebars;

public class HandlebarHelpers {

  public static void registerHelpers(Handlebars handlebars) {

    handlebars.registerHelper("ifEqual", (arg1, options) -> {
      String arg2 = options.param(0);
      return arg1 != null && arg1.equals(arg2) ? options.fn() : options.inverse();
    });
  }
}

