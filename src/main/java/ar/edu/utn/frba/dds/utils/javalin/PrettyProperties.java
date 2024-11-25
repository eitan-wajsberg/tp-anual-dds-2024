package ar.edu.utn.frba.dds.utils.javalin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PrettyProperties {
  private static PrettyProperties instance = null;
  private Properties prop;


  public static PrettyProperties getInstance() {
    if(instance == null) {
      instance = new PrettyProperties();
    }
    return instance;
  }

  private PrettyProperties() {
    this.prop = new Properties();
    this.readProperties();
  }
 private void readProperties() {
    try (InputStream input = getClass().getClassLoader().getResourceAsStream("properties/config.properties")) {
        if (input == null) {
            throw new FileNotFoundException("Archivo config.properties no encontrado en el classpath");
        }
        this.prop.load(input);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

/* private void readProperties() {
    try {
      InputStream input = new FileInputStream("src/main/resources/properties/config.properties");
      this.prop.load(input);
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }
*/
  public String propertyFromName(String name) {
    return this.prop.getProperty(name, null);
  }
}
