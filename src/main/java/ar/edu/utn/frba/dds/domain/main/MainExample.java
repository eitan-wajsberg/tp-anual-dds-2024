package ar.edu.utn.frba.dds.domain.main;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class MainExample implements WithSimplePersistenceUnit {
  public static void main(String[] arg) {
    MainExample instance = new MainExample();
    instance.inicializar();
  }

  private void inicializar() {
    withTransaction(() -> {
    });
  }
}
