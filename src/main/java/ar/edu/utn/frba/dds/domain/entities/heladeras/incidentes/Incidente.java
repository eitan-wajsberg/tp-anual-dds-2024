package ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes;

import ar.edu.utn.frba.dds.domain.converters.TipoAlertaConverter;
import ar.edu.utn.frba.dds.domain.converters.TipoIncidenteConverter;
import ar.edu.utn.frba.dds.domain.entities.contacto.Mensaje;
import ar.edu.utn.frba.dds.domain.entities.heladeras.EstadoHeladera;
import ar.edu.utn.frba.dds.domain.entities.heladeras.Heladera;
import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Visita;

import ar.edu.utn.frba.dds.domain.repositories.imp.RepositorioTecnicos;
import ar.edu.utn.frba.dds.utils.manejoDistancias.ManejoDistancias;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="incidente")
@Getter
@NoArgsConstructor
public class Incidente {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Column(name="fecha", columnDefinition = "DATE", nullable = false)
    private LocalDateTime fecha;

    @Setter
    @ManyToOne
    @JoinColumn(name="id_tecnico", referencedColumnName = "id", nullable = false)
    private Tecnico tecnicoSeleccionado;

    @OneToMany
    @JoinColumn(name = "id_incidente", nullable = false)
    private List<Visita> visitas;

    @Setter
    @Column(name="solucionado", columnDefinition = "BIT(1)", nullable = false)
    private boolean solucionado;

    @Setter
    @ManyToOne
    @JoinColumn(name="id_heladera", referencedColumnName = "id", nullable = false)
    private Heladera heladera;

    @Setter
    @Convert(converter= TipoIncidenteConverter.class)
    @Column(name="tipo_incidente", nullable = false)
    private TipoIncidente tipoIncidente;

    @Transient
    private RepositorioTecnicos repositorioTecnicos;

    @ManyToOne
    @JoinColumn(name="id_personaHumana", referencedColumnName = "id")
    private PersonaHumana colaborador;

    @Column(name="descripcion_del_colaborador")
    private String descripcionDelColaborador;

    @Column(name="ruta_foto")
    private String foto;

    @Convert(converter= TipoAlertaConverter.class)
    @Column(name="tipo_alerta")
    private TipoAlerta tipoAlerta;

    public Incidente(RepositorioTecnicos repositorioTecnicos, Heladera heladera) throws MessagingException, UnsupportedEncodingException {
        this.repositorioTecnicos = repositorioTecnicos;
        this.solucionado = false;
        this.visitas = new ArrayList<>();
        this.heladera = heladera;
        asignarTecnico(heladera); // FIXME: Verificar esto
    }

    public void registrarVisita(Visita visita, boolean solucionado) {
        this.visitas.add(visita);
        if (solucionado) {
            heladera.cambiarEstado(EstadoHeladera.ACTIVA);
            this.solucionado = solucionado;
        }
    }

    public void asignarTecnico(Heladera heladera) throws MessagingException, UnsupportedEncodingException {
        // FIXME: Hace falta parametrizar eso? yo creeria que si
        double distanciaMaximaConsideradaParaLlamarTecnico = 50;
        for (Tecnico tecnico : repositorioTecnicos.buscarTodos(Tecnico.class)) {
            double distanciaActualEnKm = ManejoDistancias.distanciaHaversineConCoordenadasEnKm(
                heladera.getDireccion().getCoordenada(),
                tecnico.getCoordenada()
            );
            if (distanciaActualEnKm < distanciaMaximaConsideradaParaLlamarTecnico
                && distanciaActualEnKm <= tecnico.getDistanciaMaximaEnKmParaSerAvisado()) {
                distanciaMaximaConsideradaParaLlamarTecnico = distanciaActualEnKm;
                tecnicoSeleccionado = tecnico;
            }
        }
        
        tecnicoSeleccionado.getContacto().enviarMensaje(new Mensaje(
            "SMAACVS: Aviso para revisar una heladera",
            "Estimado tecnico,\n"
                + "Fue elegido para revisar la heladera " + heladera.getNombre()
                + "en la direccion " + heladera.getDireccion().getNomenclatura() + ".\n"
                + tipoIncidente.obtenerDescripcionIncidente(this) + "\n"
                + "Saludos, "
                + "Sistema para la Mejora del Acceso Alimentario en Contextos de Vulnerabilidad Socioeconómica",
            LocalDateTime.now()
        ));
    }
}
