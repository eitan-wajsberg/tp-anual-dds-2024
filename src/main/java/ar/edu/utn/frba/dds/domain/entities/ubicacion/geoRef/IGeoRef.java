package ar.edu.utn.frba.dds.domain.entities.ubicacion.geoRef;

import ar.edu.utn.frba.dds.domain.entities.puntosRecomendados.ListadoPuntosRecomendados;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Coordenada;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGeoRef {
  @GET("direcciones")
  Call<GeoRefDirecciones> coordenadaSegunDireccion(@Query("direccion") String direccion, @Query("provincia") String provincia, @Query("departamento") String departamento, @Query("max") int max);
}