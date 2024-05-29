package ar.edu.utn.frba.dds.domain.puntosRecomendados;

import ar.edu.utn.frba.dds.domain.adapters.AdapterRecomendacionPuntosHeladera;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioRecomendacionPuntos {
  private static ServicioRecomendacionPuntos instancia = null;
  private static final String urlAPI = "https://52d49f8e-4e4d-4233-8610-c727a6cc27e9.mock.pstmn.io/api/";
  private Retrofit retrofit;

  private ServicioRecomendacionPuntos() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlAPI)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static ServicioRecomendacionPuntos getInstancia() {
    if (instancia == null) {
      instancia = new ServicioRecomendacionPuntos();
    }
    return instancia;
  }

  public ListadoPuntosRecomendados listadoPuntosRecomendados(float radio, String latitud, String longitud) throws IOException {
    IServicioRecomendacionPuntos servicioPuntos = this.retrofit.create(IServicioRecomendacionPuntos.class);
    Call<ListadoPuntosRecomendados> requestPuntos = servicioPuntos.puntos(radio, latitud, longitud);
    Response<ListadoPuntosRecomendados> responsePuntos = requestPuntos.execute();
    return responsePuntos.body();
  }
}
