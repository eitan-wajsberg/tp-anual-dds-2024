package ar.edu.utn.frba.dds.domain.entities.oferta;

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
    @JoinColumn(name="oferta_id", referencedColumnName = "id")
    private Oferta oferta;
    @Column(name="fecha_chanje")
    private LocalDateTime fechaCanje;
    public OfertaCanjeada(Oferta oferta, LocalDateTime fechaCanje) {
        this.oferta = oferta;
        this.fechaCanje = fechaCanje;
    }
}
