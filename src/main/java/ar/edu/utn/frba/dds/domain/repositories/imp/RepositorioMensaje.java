package ar.edu.utn.frba.dds.domain.repositories.imp;

import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioMensajes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioMensaje implements IRepositorioMensajes {
    List<Mensaje> mensajes;

    public RepositorioMensaje(){
        this.mensajes = new ArrayList<>();
    }

    public void guardar(Mensaje mensaje) {
        this.mensajes.add(mensaje);
    }

    public List<Mensaje> listar() {
        return this.mensajes;
    }
}
