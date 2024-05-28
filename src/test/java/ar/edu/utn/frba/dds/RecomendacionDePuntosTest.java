package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.IServicioRecomendacionPuntos;
import ar.edu.utn.frba.dds.domain.ListadoPuntosRecomendados;
import ar.edu.utn.frba.dds.domain.ServicioRecomendacionPuntos;
import ar.edu.utn.frba.dds.domain.ubicacion.PuntoRecomendado;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecomendacionDePuntosTest {

  ServicioRecomendacionPuntos servicioPuntos = ServicioRecomendacionPuntos.getInstancia();


  public RecomendacionDePuntosTest() throws IOException {
  }

  @BeforeEach
  public void antesDeTestear(){

  }

  @Test
  @DisplayName("Prueba de recomendacion de puntos arroja valores correctos")
  public void recomendacionDePuntosArrojaValoresCorrectos() throws IOException {
    ListadoPuntosRecomendados listadoPuntos = servicioPuntos.listadoPuntosRecomendados();
    for(PuntoRecomendado punto: listadoPuntos.puntos) {
      System.out.println(punto.getRadio());
    }
  }
}
