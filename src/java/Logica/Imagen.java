
package Logica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Imagen implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int idImagen;
    @Basic
    private String titulo;
    private String descripcion;
    private String url;
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public Imagen() {
    }

    public Imagen(int idImagen, String titulo, String descripcion, String url, Date fecha) {
        this.idImagen = idImagen;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.url = url;
        this.fecha = fecha;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
}
