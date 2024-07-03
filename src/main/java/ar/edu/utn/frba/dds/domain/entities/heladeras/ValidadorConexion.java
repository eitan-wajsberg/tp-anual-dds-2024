package ar.edu.utn.frba.dds.domain.entities.heladeras;

import ar.edu.utn.frba.dds.domain.repositories.IRepositorioHeladera;
import java.util.List;

public class ValidadorConexion {
  IRepositorioHeladera repositorioHeladera;
  public ValidadorConexion(IRepositorioHeladera repositorioHeladera){this.repositorioHeladera = repositorioHeladera;}
  public void validarConexiones(){
    List<Heladera> heladeras = repositorioHeladera.listar();
    for(Heladera heladera: heladeras){
      heladera.detectarFallaDeConexion();
    }
  }
}
