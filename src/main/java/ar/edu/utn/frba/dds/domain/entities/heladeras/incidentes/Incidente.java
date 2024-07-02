package ar.edu.utn.frba.dds.domain.entities.heladeras.incidentes;

import ar.edu.utn.frba.dds.domain.entities.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.domain.entities.tecnicos.Visita;
import ar.edu.utn.frba.dds.domain.repositories.IRepositorioTecnicos;

import java.time.LocalDateTime;
import java.util.List;

public class Incidente {
    private LocalDateTime fechaCambio;
    private Tecnico tecnico;
    private List<Visita> visitas;
    private boolean solucionado;
    private TipoIncidente tipoIncidente;
    private IRepositorioTecnicos repositorioTecnicos;

    public void registrarVisita(Visita visita) {
        this.visitas.add(visita);
    }

    public void asignarTecnico() {
        // TODO
        /*
        float distanciaMasCorta;
        List <Tecnico> tecnicos =repositorioTecnicos.listar();
        float distanciaActual;
        for cada tecnico
        for cada tecnico.areaDeCobertura
                distanciaActual = heladera.getDireccion().getCoordenada().distanciaCon(area.getCoordenada())
        if distanciaActual < distanciaMasCorta{
            distanciaMasCorta = distanciaActual
            tecnico = tecnico
            tecnico.getMedioDeContacto().notificar(new Mensaje("heladera"+...+tipoIncidente.obtenerDescripcionIncidente()))
         */
    }
}
