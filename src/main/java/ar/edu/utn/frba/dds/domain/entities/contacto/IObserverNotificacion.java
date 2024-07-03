package ar.edu.utn.frba.dds.domain.entities.contacto;

import ar.edu.utn.frba.dds.domain.entities.ubicacion.Direccion;

public interface IObserverNotificacion {
 public void serNotificadoPor(Mensaje mensaje);
 public Direccion getDireccion();
}
