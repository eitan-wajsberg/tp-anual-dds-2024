package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.domain.puntosRecomendados.ListadoPuntosRecomendados;
import ar.edu.utn.frba.dds.domain.puntosRecomendados.ServicioRecomendacionPuntos;
import ar.edu.utn.frba.dds.domain.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.domain.ubicacion.PuntoRecomendado;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RecomendacionDePuntosTest {
  ServicioRecomendacionPuntos servicioPuntos = ServicioRecomendacionPuntos.getInstancia();
  List<PuntoRecomendado> puntosEsperados = new ArrayList<>();

  @BeforeEach
  public void antesDeTestear() {
    puntosEsperados.add(new PuntoRecomendado(new Coordenada("-10.62015666394059","10.44137831038357"), 1));
    puntosEsperados.add(new PuntoRecomendado(new Coordenada("-20.62015666394059","20.44137831038357"), 2));
    puntosEsperados.add(new PuntoRecomendado(new Coordenada("-30.62015666394059","30.44137831038357"), 3));
    puntosEsperados.add(new PuntoRecomendado(new Coordenada("-40.62015666394059","40.44137831038357"), 4));
    puntosEsperados.add(new PuntoRecomendado(new Coordenada("-50.62015666394059","50.44137831038357"), 5));
  }

  @Test
  @DisplayName("La recomendacion de puntos mockeada arroja valores correctos")
  public void recomendacionDePuntosMockeadaArrojaValoresCorrectos() throws IOException {
    ListadoPuntosRecomendados listadoPuntos = servicioPuntos.listadoPuntosRecomendados();
    Assertions.assertEquals(puntosEsperados, listadoPuntos.puntos);
  }
}
