package ar.edu.utn.frba.dds.repositories.imp;

import ar.edu.utn.frba.dds.domain.oferta.Oferta;
import ar.edu.utn.frba.dds.repositories.IRepositorioOferta;
import java.util.ArrayList;
import java.util.List;


public class RepositorioOferta implements IRepositorioOferta {
    private List<Oferta> ofertas;

    public RepositorioOferta(){
        this.ofertas = new ArrayList<>();
    }
    @Override
    public void guardar(Oferta oferta) {
        oferta.setId((long) (this.ofertas.size() + 1));
        this.ofertas.add(oferta);
    }

    @Override
    public void actualizar(Oferta oferta) {
        for(int i=0; i < ofertas.size(); i++){
            if(oferta.getId().equals(ofertas.get(i).getId())){
                ofertas.set(i, oferta);
                break;
            }
        }
    }

}
