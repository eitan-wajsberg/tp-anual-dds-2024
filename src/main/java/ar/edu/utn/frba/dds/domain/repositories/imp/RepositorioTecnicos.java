package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.repositories.Repositorio;
import java.util.Optional;

public class RepositorioTecnicos extends Repositorio {
    public Optional<Tecnico> buscarPorDocumento(String documentoId) {
        return entityManager().createQuery("from" + Tecnico.class.getName() + "where nroDocumento=" + documentoId, Tecnico.class)
            .getResultList().stream().findFirst();
    }
}
