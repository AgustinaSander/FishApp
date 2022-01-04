
package Logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class FichaDatos implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int idFicha;
    @OneToMany
    private List<Medicion> listaMediciones;

    public FichaDatos() {
    }

    public FichaDatos(int idFicha, List<Medicion> listaMediciones) {
        this.idFicha = idFicha;
        this.listaMediciones = listaMediciones;
    }

    public int getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(int idFicha) {
        this.idFicha = idFicha;
    }

    public List<Medicion> getListaMediciones() {
        return listaMediciones;
    }

    public void setListaMediciones(List<Medicion> listaMediciones) {
        this.listaMediciones = listaMediciones;
    }
    
    
}
