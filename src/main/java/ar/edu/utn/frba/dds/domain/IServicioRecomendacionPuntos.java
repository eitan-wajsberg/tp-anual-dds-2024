package ar.edu.utn.frba.dds.domain;

import ar.edu.utn.frba.dds.domain.ubicacion.Coordenada;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IServicioRecomendacionPuntos {
  @GET("puntos")
  Call<Coordenada> puntos();
}
