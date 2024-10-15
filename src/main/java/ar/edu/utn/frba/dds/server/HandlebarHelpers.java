package ar.edu.utn.frba.dds.server;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import java.io.IOException;

public class HandlebarHelpers {

  public static void registerHelpers(Handlebars handlebars) {

    handlebars.registerHelper("ifEqual", (arg1, options) -> {
      String arg2 = options.param(0);
      return arg1 != null && arg1.equals(arg2) ? options.fn() : options.inverse();
    });

    handlebars.registerHelper("eq", new Helper<Object>() {
      @Override
      public Object apply(Object first, Options options) throws IOException {
        Object second = options.param(0);
        return first != null && first.equals(second) ? options.fn() : options.inverse();
      }
    });
  }
}

