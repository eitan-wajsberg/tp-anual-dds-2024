package ar.edu.utn.frba.dds.domain;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IServicioRecomendacionPuntos {
  @GET("puntos")
  Call<ListadoPuntosRecomendados> puntos();
}
