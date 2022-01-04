
package Logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class FichaMedica implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int idFicha;
    @OneToOne
    private Habitante habitante;
    @OneToMany
    private List<Enfermedad> listaEnfermedades;

    public FichaMedica() {
    }

    public FichaMedica(int idFicha, Habitante habitante, List<Enfermedad> listaEnfermedades) {
        this.idFicha = idFicha;
        this.habitante = habitante;
        this.listaEnfermedades = listaEnfermedades;
    }

    public int getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(int idFicha) {
        this.idFicha = idFicha;
    }

    public Habitante getHabitante() {
        return habitante;
    }

    public void setHabitante(Habitante habitante) {
        this.habitante = habitante;
    }

    public List<Enfermedad> getListaEnfermedades() {
        return listaEnfermedades;
    }

    public void setListaEnfermedades(List<Enfermedad> listaEnfermedades) {
        this.listaEnfermedades = listaEnfermedades;
    }
    
    
}
