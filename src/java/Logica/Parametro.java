
package Logica;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Parametro implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int idParametro;
    @Basic
    private String parametro;
    private String valor;
    private String observaciones;

    public Parametro() {
    }

    public Parametro(int idParametro, String parametro, String valor, String observaciones) {
        this.idParametro = idParametro;
        this.parametro = parametro;
        this.valor = valor;
        this.observaciones = observaciones;
    }

    public int getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(int idParametro) {
        this.idParametro = idParametro;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
}
