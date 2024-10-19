package ar.edu.utn.frba.dds.domain.entities.oferta;

import ar.edu.utn.frba.dds.domain.entities.personasHumanas.PersonaHumana;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name="oferta_canjeada")
@NoArgsConstructor
public class OfertaCanjeada {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="oferta_id", referencedColumnName = "id", nullable = false)
    private Oferta oferta;

    @Column(name="fechaCanje", nullable = false)
    private LocalDateTime fechaCanje;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="canjeador_id", referencedColumnName = "id", nullable = false)
    private PersonaHumana canjeador;

    public OfertaCanjeada(Oferta oferta, LocalDateTime fechaCanje, PersonaHumana canjeador) {
        this.oferta = oferta;
        this.fechaCanje = fechaCanje;
        this.canjeador = canjeador;
    }
}
