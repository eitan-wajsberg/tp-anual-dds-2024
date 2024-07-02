package ar.edu.utn.frba.dds.domain.repositories;

import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import java.util.List;
import java.util.Optional;

public interface IRepositorioMensajes {
    void guardar(Mensaje mensaje);
    List<Mensaje> listar();
}
