package ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes;

import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.EstadoHeladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Visita;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioTecnicos;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import lombok.Setter;

public class Incidente {
    private LocalDateTime fecha;
    private Tecnico tecnico;
    private List<Visita> visitas;
    @Setter
    private boolean solucionado;
    private Heladera heladera;
    private TipoIncidente tipoIncidente;
    private IRepositorioTecnicos repositorioTecnicos;

    public Incidente(IRepositorioTecnicos repositorioTecnicos, LocalDateTime fecha){
        this.repositorioTecnicos = repositorioTecnicos;
        this.solucionado = false;
        this.visitas = new ArrayList<>();
    }

    public void registrarVisita(Visita visita, boolean solucionado) {
        this.visitas.add(visita);
        if(solucionado){
            heladera.cambiarEstado(EstadoHeladera.ACTIVA);
            this.solucionado = solucionado;
        }
    }

    public void asignarTecnico(Heladera heladera) throws MessagingException, UnsupportedEncodingException {
        float distanciaMasCorta;
        float distanciaActual;
        Tecnico tecnicoElegido;
        List <Tecnico> tecnicos = repositorioTecnicos.listar();
        for(Tecnico tecnico: tecnicos){
            //FIXME
            /*
            String distanciaActual = heladera.getDireccion().getCoordenada();//.distanciaCon(tecnico.getArea())
            if(distanciaActual < distanciaMasCorta) {
                distanciaMasCorta = distanciaActual;
                tecnicoElegido = tecnico;
            }
             */
        }
        tecnico.getContacto().enviarMensaje(new Mensaje(
            "Fuiste elegido para revisar una heladera",
            "Heladera " + heladera.getNombre() + " " + tipoIncidente.obtenerDescripcionIncidente(),
            LocalDateTime.now()
        ));
    }

}
