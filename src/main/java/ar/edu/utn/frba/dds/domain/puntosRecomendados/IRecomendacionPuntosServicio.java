package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.domain.puntosRecomendados.ListadoPuntosRecomendados;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRecomendacionPuntosServices {
  @GET("puntos")
  Call<ListadoPuntosRecomendados> puntos(@Query("radio") float radio, @Query("latitud") String latitud, @Query("longitud") String longitud);
}
