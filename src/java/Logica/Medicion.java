
package Logica;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Medicion implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int idMedicion;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @OneToMany
    private List<Parametro> listaParametros;

    public Medicion() {
    }

    public Medicion(int idMedicion, Date fecha, List<Parametro> listaParametros) {
        this.idMedicion = idMedicion;
        this.fecha = fecha;
        this.listaParametros = listaParametros;
    }

    public int getIdMedicion() {
        return idMedicion;
    }

    public void setIdMedicion(int idMedicion) {
        this.idMedicion = idMedicion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Parametro> getListaParametros() {
        return listaParametros;
    }

    public void setListaParametros(List<Parametro> listaParametros) {
        this.listaParametros = listaParametros;
    }
    
    
    
}
