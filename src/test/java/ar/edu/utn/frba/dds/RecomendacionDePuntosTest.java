package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.puntosRecomendados.ListadoPuntosRecomendados;
import ar.edu.utn.frba.dds.domain.puntosRecomendados.ServicioRecomendacionPuntos;
import ar.edu.utn.frba.dds.domain.ubicacion.Coordenada;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecomendacionDePuntosTest {
  ServicioRecomendacionPuntos servicioPuntos = ServicioRecomendacionPuntos.getInstancia();
  List<Coordenada> puntosEsperados = new ArrayList<>();

  @BeforeEach
  public void antesDeTestear() {
    puntosEsperados.add(new Coordenada("-10.62015666394059","10.44137831038357"));
    puntosEsperados.add(new Coordenada("-20.62015666394059","20.44137831038357"));
    puntosEsperados.add(new Coordenada("-30.62015666394059","30.44137831038357"));
    puntosEsperados.add(new Coordenada("-40.62015666394059","40.44137831038357"));
    puntosEsperados.add(new Coordenada("-50.62015666394059","50.44137831038357"));
  }

  @Test
  @DisplayName("La recomendacion de puntos mockeada arroja valores correctos")
  public void recomendacionDePuntosMockeadaArrojaValoresCorrectos() throws IOException {
    ListadoPuntosRecomendados listadoPuntos = servicioPuntos.listadoPuntosRecomendados();
    Assertions.assertEquals(puntosEsperados, listadoPuntos.puntos);
  }
}
