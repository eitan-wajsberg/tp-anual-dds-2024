package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.heladeras.solicitudes.SolicitudApertura;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.util.List;

public class RepositorioSolicitudApertura extends Repositorio {
  public List listarRecientes(Long distribucionId) {
    return entityManager()
        .createQuery("SELECT d FROM " + SolicitudApertura.class.getName() + " d WHERE d.distribucion.id = :distribucionViandaId "
            +"order by d.fechaSolicitud desc")
        .setParameter("distribucionViandaId", distribucionId)
        .getResultList();
  }
}
