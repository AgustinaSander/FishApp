
package Logica;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)  
public abstract class Pecera implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    protected int idPecera;
    @Basic
    protected String nombre;
    protected double ancho;
    protected double largo;
    protected double alto;
    protected double capacidad;
    protected String iluminacion;
    protected String sistFiltrado;
    @Temporal(TemporalType.DATE)
    protected Date fechaInicio;
    @Temporal(TemporalType.DATE)
    protected Date fechaFin;
    @OneToMany
    protected List<Habitante> listaHabitantes;
    @OneToOne
    protected Album album;
    @OneToOne
    protected Imagen fotoPrincipal;
    @OneToOne
    protected FichaDatos fichaDatos;

    public Pecera() {
    }

    public Pecera(String nombre, double ancho, double largo, double alto, double capacidad, String iluminacion, String sistFiltrado, Date fechaInicio, Date fechaFin) {
        this.nombre = nombre;
        this.ancho = ancho;
        this.largo = largo;
        this.alto = alto;
        this.capacidad = capacidad;
        this.iluminacion = iluminacion;
        this.sistFiltrado = sistFiltrado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Pecera(int idPecera, String nombre, double ancho, double largo, double alto, double capacidad, String iluminacion, String sistFiltrado, Date fechaInicio, Date fechaFin, List<Habitante> listaHabitantes, Album album, Imagen fotoPrincipal, FichaDatos fichaDatos) {
        this.idPecera = idPecera;
        this.nombre = nombre;
        this.ancho = ancho;
        this.largo = largo;
        this.alto = alto;
        this.capacidad = capacidad;
        this.iluminacion = iluminacion;
        this.sistFiltrado = sistFiltrado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.listaHabitantes = listaHabitantes;
        this.album = album;
        this.fotoPrincipal = fotoPrincipal;
        this.fichaDatos = fichaDatos;
    }

    public int getIdPecera() {
        return idPecera;
    }

    public void setIdPecera(int idPecera) {
        this.idPecera = idPecera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }

    public String getIluminacion() {
        return iluminacion;
    }

    public void setIluminacion(String iluminacion) {
        this.iluminacion = iluminacion;
    }

    public String getSistFiltrado() {
        return sistFiltrado;
    }

    public void setSistFiltrado(String sistFiltrado) {
        this.sistFiltrado = sistFiltrado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Habitante> getListaHabitantes() {
        return listaHabitantes;
    }

    public void setListaHabitantes(List<Habitante> listaHabitantes) {
        this.listaHabitantes = listaHabitantes;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Imagen getFotoPrincipal() {
        return fotoPrincipal;
    }

    public void setFotoPrincipal(Imagen fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }

    public FichaDatos getFichaDatos() {
        return fichaDatos;
    }

    public void setFichaDatos(FichaDatos fichaDatos) {
        this.fichaDatos = fichaDatos;
    }

    @Override
    public String toString() {
        return "Pecera{" + "idPecera=" + idPecera + ", nombre=" + nombre + ", ancho=" + ancho + ", largo=" + largo + ", alto=" + alto + ", capacidad=" + capacidad + ", iluminacion=" + iluminacion + ", sistFiltrado=" + sistFiltrado + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", listaHabitantes=" + listaHabitantes + ", album=" + album + ", fotoPrincipal=" + fotoPrincipal + ", fichaDatos=" + fichaDatos + '}';
    }
    
    
}
