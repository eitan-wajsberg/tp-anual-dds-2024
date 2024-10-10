package ar.edu.utn.frba.dds.domain.entities.puntosRecomendados.servicioRecomendacionDonacion;

import lombok.Setter;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class RecomendacionDonacion {
    @Setter
    private static RecomendacionDonacion instancia = null;
    private static final String urlAPI = "https://servicio-apiv2-production.up.railway.app/api/";
    private Retrofit retrofit;

    private RecomendacionDonacion() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RecomendacionDonacion getInstancia() {
        if (instancia == null) {
            instancia = new RecomendacionDonacion();
        }
        return instancia;
    }

    public RecomendacionHeladeras recomendarHeladeras(float radio_max, int stock_minimo, String calle, String altura, String provincia) throws IOException {
        IRecomendacionDonacion georefService = this.retrofit.create(IRecomendacionDonacion.class);
        Call<RecomendacionHeladeras> request = georefService.recomendacionHeladeras(radio_max, stock_minimo, calle, altura, provincia);
        Response<RecomendacionHeladeras> response = request.execute();
        return response.body();
    }

    public RecomendacionPersonasVulnerables recomendarPersonasVulnerables(float radio_max, String calle, String altura, String provincia) throws IOException {
        IRecomendacionDonacion georefService = this.retrofit.create(IRecomendacionDonacion.class);
        Call<RecomendacionPersonasVulnerables> request = georefService.recomendacionPersonasVulnerables(radio_max, calle, altura, provincia);
        Response<RecomendacionPersonasVulnerables> response = request.execute();
        return response.body();
    }

    public List<PersonaGrabada> guardarPersonas(Personas personas) throws IOException {
        IRecomendacionDonacion georefService = this.retrofit.create(IRecomendacionDonacion.class);
        Call<List<PersonaGrabada>> request = georefService.grabarPersonas(personas);
        Response<List<PersonaGrabada>> response = request.execute();
        return response.body();
    }

    public List<HeladeraGrabada> guardarHeladeras(Heladeras heladeras) throws IOException {
        IRecomendacionDonacion georefService = this.retrofit.create(IRecomendacionDonacion.class);
        Call<List<HeladeraGrabada>> request = georefService.grabarHeladeras(heladeras);
        Response<List<HeladeraGrabada>> response = request.execute();
        return response.body();
    }
}
