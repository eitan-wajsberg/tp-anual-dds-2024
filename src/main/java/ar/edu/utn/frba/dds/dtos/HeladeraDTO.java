package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class HeladeraDTO {
  private Long id;
  private String nombre;
  private DireccionDTO direccion;
  private int capacidadMaximaViandas;
  private String modelo;
  private Float temperaturaMaxima;
  private Float temperaturaMinima;
  private Float temperaturaEsperada;

  public HeladeraDTO(Heladera heladera) {
    this.id = heladera.getId();
    this.nombre = heladera.getNombre();
    this.direccion = new DireccionDTO(heladera.getDireccion());
    this.capacidadMaximaViandas = heladera.getCapacidadMaximaViandas();
    this.modelo = heladera.getModelo().getModelo();
    this.temperaturaMaxima = heladera.getModelo().getTemperaturaMaxima();
    this.temperaturaMinima = heladera.getModelo().getTemperaturaMinima();
    this.temperaturaEsperada = heladera.getTemperaturaEsperada();
  }

  public void obtenerFormulario(Context context) {
    this.nombre = context.formParam("nombre");
    String capacidadParam = context.formParam("capacidadMaximaViandas");
    this.capacidadMaximaViandas = (capacidadParam != null && !capacidadParam.isEmpty())
        ? Integer.parseInt(capacidadParam)
        : 0;

    this.modelo = context.formParam("modelo"); // Agregado
    String temperaturaMaximaParam = context.formParam("temperaturaMaxima");
    this.temperaturaMaxima = (temperaturaMaximaParam != null && !temperaturaMaximaParam.isEmpty())
        ? Float.parseFloat(temperaturaMaximaParam)
        : null;

    String temperaturaMinimaParam = context.formParam("temperaturaMinima");
    this.temperaturaMinima = (temperaturaMinimaParam != null && !temperaturaMinimaParam.isEmpty())
        ? Float.parseFloat(temperaturaMinimaParam)
        : null;

    String temperaturaEsperadaParam = context.formParam("temperaturaEsperada");
    this.temperaturaEsperada = (temperaturaEsperadaParam != null && !temperaturaEsperadaParam.isEmpty())
        ? Float.parseFloat(temperaturaEsperadaParam)
        : null;
  }
}

