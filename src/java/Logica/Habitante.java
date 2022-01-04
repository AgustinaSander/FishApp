
package Logica;

import Logica.TipoHabitante;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Habitante implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int idHabitante;
    @Basic
    private TipoHabitante tipo;
    private String nombre;
    private double precio;
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;
    @Temporal(TemporalType.DATE)
    private Date fechaMuerte;
    @OneToOne
    private Imagen imagenPerfil;
    @OneToOne
    private Album album;
    @OneToOne
    private FichaMedica ficha;
    @ManyToOne
    private Pecera pecera;

    public Habitante() {
    }

    public Habitante(int idHabitante, TipoHabitante tipo, String nombre, Date fechaCompra, Date fechaMuerte, Imagen imagenPerfil, double precio, Album album, FichaMedica ficha, Pecera pecera) {
        this.idHabitante = idHabitante;
        this.tipo = tipo;
        this.nombre = nombre;
        this.fechaCompra = fechaCompra;
        this.fechaMuerte = fechaMuerte;
        this.imagenPerfil = imagenPerfil;
        this.precio = precio;
        this.album = album;
        this.ficha = ficha;
        this.pecera = pecera;
    }

    public int getIdHabitante() {
        return idHabitante;
    }

    public void setIdHabitante(int idHabitante) {
        this.idHabitante = idHabitante;
    }

    public TipoHabitante getTipo() {
        return tipo;
    }

    public void setTipo(TipoHabitante tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Date getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(Date fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public Imagen getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(Imagen imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public FichaMedica getFicha() {
        return ficha;
    }

    public void setFicha(FichaMedica ficha) {
        this.ficha = ficha;
    }

    public Pecera getPecera() {
        return pecera;
    }

    public void setPecera(Pecera pecera) {
        this.pecera = pecera;
    }
    
    
}
