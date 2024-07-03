package ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes;

import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.EstadoHeladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Visita;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioTecnicos;

import ar.edu.utn.frba.dds.utils.manejoDistancias.ManejoDistancias;
import com.fasterxml.jackson.databind.deser.BasicDeserializerFactory;
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

    public Incidente(IRepositorioTecnicos repositorioTecnicos, LocalDateTime fecha) {
        this.repositorioTecnicos = repositorioTecnicos;
        this.solucionado = false;
        this.visitas = new ArrayList<>();
    }

    public void registrarVisita(Visita visita, boolean solucionado) {
        this.visitas.add(visita);
        if (solucionado) {
            heladera.cambiarEstado(EstadoHeladera.ACTIVA);
            this.solucionado = solucionado;
        }
    }

    public void asignarTecnico(Heladera heladera) throws MessagingException, UnsupportedEncodingException {
        double distanciaMasCorta = 0;
        Tecnico tecnicoElegido = null;
        List <Tecnico> tecnicos = repositorioTecnicos.listar();
        for (Tecnico tecnico : tecnicos) {
            double distanciaActual = ManejoDistancias.distanciaHaversineConCoordenadas(
                heladera.getDireccion().getCoordenada(),
                tecnico.getArea().getCoordenada()
            );
            if (distanciaActual < distanciaMasCorta) {
                distanciaMasCorta = distanciaActual;
                tecnicoElegido = tecnico;
            }
        }
        
        tecnicoElegido.getContacto().enviarMensaje(new Mensaje(
            "SMAACVS: Aviso para revisar una heladera",
            "Estimado tecnico,\n"
                + "Fue elegido para revisar la heladera " + heladera.getNombre()
                + "en la direccion " + heladera.getDireccion().direccionSegunGeoRef() + ". \n"
                + tipoIncidente.obtenerDescripcionIncidente() + "\n"
                + "Saludos, "
                + "Sistema para la Mejora del Acceso Alimentario en Contextos de Vulnerabilidad Socioeconómica",
            LocalDateTime.now()
        ));
    }
}
