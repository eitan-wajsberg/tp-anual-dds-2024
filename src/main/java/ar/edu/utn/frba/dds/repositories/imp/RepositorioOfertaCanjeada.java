package ar.edu.utn.frba.dds.repositories.imp;

import ar.edu.utn.frba.dds.domain.oferta.OfertaCanjeada;
import ar.edu.utn.frba.dds.repositories.IRepositorioOfertaCanjeada;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioOfertaCanjeada implements IRepositorioOfertaCanjeada {
    private List<OfertaCanjeada> ofertasCanjeadas;

    public RepositorioOfertaCanjeada() {
        this.ofertasCanjeadas = new ArrayList<>();
    }

    @Override
    public void guardar(OfertaCanjeada ofertaCanjeada) {
        this.ofertasCanjeadas.add(ofertaCanjeada);
    }

    @Override
    public List<OfertaCanjeada> buscarPorFecha(LocalDateTime fecha) {
        return this.ofertasCanjeadas.stream()
                .filter(oferta -> oferta.getFechaCanje().toLocalDate().equals(fecha.toLocalDate()))
                .collect(Collectors.toList());
    }

    // Otros métodos según necesidad
}
