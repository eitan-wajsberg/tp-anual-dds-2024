package ar.edu.utn.frba.dds.dtos;

import ar.edu.utn.frba.dds.domain.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.geoRef.GeoRefDirecciones;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.geoRef.GeoRefServicio;
import ar.edu.utn.frba.dds.exceptions.ValidacionFormularioException;
import io.javalin.http.Context;
import java.io.IOException;
import java.util.stream.Stream;
import lombok.Data;

@Data
public class DireccionDTO implements DTO {
  private String calle;
  private String altura;
  private String provincia;
  private String municipio;
  private String rutaHbs;

  @Override
  public void obtenerFormulario(Context context, String rutaHbs) {
    this.setCalle(context.formParam("calle"));
    this.setAltura(context.formParam("altura"));
    this.setProvincia(context.formParam("provincia"));
    this.setMunicipio(context.formParam("municipio"));
    this.setRutaHbs(rutaHbs);
  }

  @Override
  public Object convertirAEntidad() {
    if (Stream.of(this.calle, this.altura, this.provincia, this.municipio).allMatch(String::isEmpty)) {
      return null;
    }

    if (Stream.of(this.calle, this.altura, this.provincia, this.municipio).anyMatch(String::isEmpty)) {
      throw new ValidacionFormularioException("La dirección no es obligatoria, pero si se indica una, todos los campos son necesarios.", rutaHbs);
    }

    try {
      GeoRefDirecciones body = GeoRefServicio.getInstancia().coordenadaDeDireccion(this);
      if (body.cantidad < 1) {
        throw new ValidacionFormularioException("Dirección inexistente. Por favor, revise los datos.", rutaHbs);
      }

      GeoRefDirecciones.GeoRefUbicacion ubicacion = body.direcciones.get(0).ubicacion;
      Coordenada coordenada = new Coordenada(ubicacion.lat, ubicacion.lon);
      Direccion direccion = new Direccion();
      direccion.setNomenclatura(body.direcciones.get(0).nomenclatura);
      direccion.setCoordenada(coordenada);
      return direccion;
    } catch (IOException e) {
      throw new ValidacionFormularioException("Ocurrió un error en la búsqueda de la dirección, revise los datos.", rutaHbs);
    }
  }
}
