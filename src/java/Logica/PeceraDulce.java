
package Logica;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;

@Entity
public class PeceraDulce extends Pecera{

    public PeceraDulce() {
    }

    public PeceraDulce(String nombre, double ancho, double largo, double alto, double capacidad, String iluminacion, String sistFiltrado, Date fechaInicio, Date fechaFin) {
        super(nombre, ancho, largo, alto, capacidad, iluminacion, sistFiltrado, fechaInicio, fechaFin);
    }
    
    public PeceraDulce(int idPecera, String nombre, double ancho, double largo, double alto, double capacidad, String iluminacion, String sistFiltrado, Date fechaInicio, Date fechaFin, List<Habitante> listaHabitantes, Album album, Imagen fotoPrincipal, FichaDatos fichaDatos) {
        super(idPecera, nombre, ancho, largo, alto, capacidad, iluminacion, sistFiltrado, fechaInicio, fechaFin, listaHabitantes, album, fotoPrincipal, fichaDatos);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdPecera() {
        return idPecera;
    }

    public void setIdPecera(int idPecera) {
        this.idPecera = idPecera;
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
    
    
}
