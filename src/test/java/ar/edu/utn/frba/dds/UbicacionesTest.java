package ar.edu.utn.frba.dds;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.domain.entities.ubicacion.Calle;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Coordenada;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.GeoRefServicio;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Municipio;
import ar.edu.utn.frba.dds.domain.entities.ubicacion.Provincia;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UbicacionesTest {
  private static GeoRefServicio geoRefMockMuniz;
  private static GeoRefServicio geoRefMockAvLaPlata;
  private static GeoRefServicio geoRefMockLugano;

  @BeforeAll
  public static void antesDeTestear() {
    geoRefMockMuniz = mock(GeoRefServicio.class);
    try {
      when(geoRefMockMuniz.coordenadaDeDireccion(any(Direccion.class)))
          .thenReturn(new Coordenada("-34.61761142858984", "-58.42864174511631"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    geoRefMockAvLaPlata = mock(GeoRefServicio.class);
    try {
      when(geoRefMockAvLaPlata.coordenadaDeDireccion(any(Direccion.class)))
          .thenReturn(new Coordenada("-34.617633504954625", "-58.42737100130617"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    geoRefMockLugano = mock(GeoRefServicio.class);
    try {
      when(geoRefMockLugano.coordenadaDeDireccion(any(Direccion.class)))
          .thenReturn(new Coordenada("-34.65930458474216", "-58.46793479447881"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("Dos calles inmediatamente paralelas estan cerca")
  void dosCallesInmediatamenteParalelasEstanCerca() {
    Calle muniz = new Calle("Muñiz");
    Municipio municipio = new Municipio("Buenos Aires");
    Provincia ciudad = new Provincia("Ciudad Autónoma de Buenos Aires");

    GeoRefServicio.setInstancia(geoRefMockMuniz);
    Direccion direccionUno = null;
    try {
      direccionUno = new Direccion(muniz, "400", municipio, ciudad);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    GeoRefServicio.setInstancia(geoRefMockAvLaPlata);
    Calle avLaPlata = new Calle("Av La Plata");
    Direccion direccionDos = null;
    try {
      direccionDos = new Direccion(avLaPlata, "241", municipio, ciudad);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Assertions.assertTrue(direccionUno.estaCercaDe(direccionDos));
  }

  @Test
  @DisplayName("UTN Lugano no esta cerca de Muñiz al 400")
  void utnLuganoNoEstaCercaDeMunizAl400() {
    Calle muniz = new Calle("Muñiz");
    Municipio municipio = new Municipio("Buenos Aires");
    Provincia ciudad = new Provincia("Ciudad Autónoma de Buenos Aires");

    GeoRefServicio.setInstancia(geoRefMockMuniz);
    Direccion direccionUno = null;
    try {
      direccionUno = new Direccion(muniz, "400", municipio, ciudad);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    GeoRefServicio.setInstancia(geoRefMockLugano);
    Calle avLaPlata = new Calle("Mozart");
    Direccion direccionDos = null;
    try {
      direccionDos = new Direccion(avLaPlata, "2396", municipio, ciudad);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Assertions.assertTrue(direccionUno.estaCercaDe(direccionDos));
  }
}
