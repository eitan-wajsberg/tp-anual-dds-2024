package ar.edu.utn.frba.dds.domain.entities.ubicacion;

import ar.edu.utn.frba.dds.domain.entities.puntosRecomendados.ListadoPuntosRecomendados;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGeoRef {
  @GET("direcciones")
  Call<Coordenada> coordenadaSegunDireccion(@Query("direccion") String direcion, @Query("provincia") String provincia);
}
