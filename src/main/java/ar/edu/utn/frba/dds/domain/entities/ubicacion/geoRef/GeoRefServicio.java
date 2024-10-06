package ar.edu.utn.frba.dds.domain.entities.ubicacion.geoRef;

import ar.edu.utn.frba.dds.domain.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.dtos.DireccionDTO;
import java.io.IOException;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeoRefServicio {
  @Setter
  private static GeoRefServicio instancia = null;
  private static final String urlAPI = "https://apis.datos.gob.ar/georef/api/";
  private Retrofit retrofit;

  private GeoRefServicio() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlAPI)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static GeoRefServicio getInstancia() {
    if (instancia == null) {
      instancia = new GeoRefServicio();
    }
    return instancia;
  }

  public GeoRefDirecciones coordenadaDeDireccion(DireccionDTO direccion) throws IOException {
    IGeoRef georefService = this.retrofit.create(IGeoRef.class);
    Call<GeoRefDirecciones> request = georefService.coordenadaSegunDireccion(
        direccion.getCalle() + direccion.getAltura(),
        direccion.getProvincia(),
        direccion.getMunicipio(),
        1
    );
    Response<GeoRefDirecciones> response = request.execute();
    if (!response.isSuccessful()) {
      throw new IOException();
    }

    return response.body();
  }
}