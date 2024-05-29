package ar.edu.utn.frba.dds.domain.personasVulnerables;

public class UsoMaximoDeTarjetasPorDiaExcedidoException extends RuntimeException {
  public UsoMaximoDeTarjetasPorDiaExcedidoException() {
    super("Se ha excedido el uso máximo de la tarjeta por día.");
  }
}
