
package Logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Album implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int idAlbum;
    @Basic
    private String descripcion;
    @OneToMany
    private List<Imagen> listaImagenes;

    public Album() {
    }

    public Album(int idAlbum, String descripcion, List<Imagen> listaImagenes) {
        this.idAlbum = idAlbum;
        this.descripcion = descripcion;
        this.listaImagenes = listaImagenes;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Imagen> getListaImagenes() {
        return listaImagenes;
    }

    public void setListaImagenes(List<Imagen> listaImagenes) {
        this.listaImagenes = listaImagenes;
    }
    
    
    
}
