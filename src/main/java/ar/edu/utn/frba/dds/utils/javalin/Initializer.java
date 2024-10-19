package ar.edu.utn.frba.dds.utils.javalin;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

public class Initializer implements WithSimplePersistenceUnit {
  public void init() {
    // Este método asegura que la base de datos esté correctamente inicializada.
    // Si la base de datos está vacía, se realiza la hidratación inicial ejecutando el script.
    // Si la base ya contiene datos, el script no se vuelve a ejecutar.
    hidratarBaseConArchivo();
  }

  private void hidratarBaseConArchivo() {
    EntityTransaction transaction = entityManager().getTransaction();
    try {
      transaction.begin();

      crearTablaMetadata();
      boolean datosExistentes = verificarDatosExistentes();

      if (!datosExistentes) {
        ejecutarScriptSQL();
        marcarScriptEjecutado();
        System.out.println("\u001B[32mDatos insertados correctamente.\u001B[0m");
      } else {
        System.out.println("\u001B[32mLos datos ya han sido insertados previamente.\u001B[0m");
      }

      transaction.commit();
    } catch (Exception e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    } finally {
      entityManager().close();
    }
  }

  private void crearTablaMetadata() {
    entityManager().createNativeQuery(
        "CREATE TABLE IF NOT EXISTS metadata (" +
            "id INT PRIMARY KEY, " +
            "script_executado BOOLEAN DEFAULT FALSE)"
    ).executeUpdate();
  }

  private boolean verificarDatosExistentes() {
    try {
      Boolean scriptEjecutado = (Boolean) entityManager().createNativeQuery(
          "SELECT script_executado FROM metadata WHERE id = 1"
      ).getSingleResult();
      return scriptEjecutado != null && scriptEjecutado; // Devuelve true si ya se ejecutó
    } catch (NoResultException e) {
      return false; // No hay registro, por lo tanto no se ha ejecutado
    }
  }

  private void ejecutarScriptSQL() throws Exception {
    String sql = new String(Files.readAllBytes(Paths.get("sql/hidratacion.sql")));
    String[] statements = sql.split(";");

    for (String statement : statements) {
      if (!statement.trim().isEmpty()) {
        try {
          entityManager().createNativeQuery(statement.trim()).executeUpdate();
        } catch (Exception e) {
          throw new RuntimeException("Error al ejecutar la sentencia SQL: " + statement, e);
        }
      }
    }
  }

  private void marcarScriptEjecutado() {
    entityManager().createNativeQuery(
        "INSERT INTO metadata (id, script_executado) " +
            "VALUES (1, TRUE)"
    ).executeUpdate();
  }
}
