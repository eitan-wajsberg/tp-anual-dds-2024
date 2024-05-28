package ar.edu.utn.frba.dds.domain.oferta;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OfertaCanjeada {
    private Oferta oferta;
    private LocalDateTime fechaCanje;
    public OfertaCanjeada(Oferta oferta, LocalDateTime fechaCanje) {
        this.oferta = oferta;
        this.fechaCanje = fechaCanje;
    }
}
