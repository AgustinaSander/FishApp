
package Logica;

import Persistencia.ControladoraPersistencia;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.incubator.jpackage.internal.IOUtils;

public class ControladoraLogica {
    ControladoraPersistencia controlPers = new ControladoraPersistencia();

    public boolean crearUsuario(String nombre, String apellido, String email, String password) {
        Usuario u = new Usuario(nombre, apellido, email, password);
        //Verifico que no exista ningun usuario con ese email
        List<Usuario> usuarios = controlPers.obtenerUsuarios();
        boolean existeUsuario = false;
        for(Usuario usu: usuarios){
            if(usu.getEmail().equals(email)){
                existeUsuario = true;
                break;
            }
        }
        
        if(!existeUsuario){
            controlPers.crearUsuario(u);
            return true;
        }
        return false;
    }

    public Usuario iniciarSesion(String email, String password) {
        //Verifico que exista algun usuario con esos datos
        List<Usuario> usuarios = controlPers.obtenerUsuarios();
        Usuario u = null;
        for(Usuario usu: usuarios){
            if(usu.getEmail().equals(email) && usu.getContrasena().equals(password)){
                u = usu;
                break;
            }
        }
        
        return u;
    }

    public Pecera crearPecera(int idUsuario, String nombre, String tipoAgua, double alto, double ancho, double largo, double capacidad, Date fechaInicio, Date fechaFin, String filtrado, String dosificadora, String sump, String iluminacion, String foto) throws IOException{
        
        Pecera pecera = null;
        if(tipoAgua.equals("0")){
            pecera = new PeceraDulce(nombre, ancho, largo, alto, capacidad, iluminacion, filtrado, fechaInicio, fechaFin);
        }
        else if(tipoAgua.equals("1")){
            pecera = new PeceraMarina(dosificadora, sump, nombre, ancho, largo, alto, capacidad, iluminacion, filtrado, fechaInicio, fechaFin);
        }
        Imagen imagen = new Imagen(foto, new Date());
        
        if(!(foto.equals(""))){
            Imagen i = controlPers.crearImagen(imagen);
            pecera.setFotoPrincipal(i);
        }

        Pecera p = controlPers.crearPecera(pecera);
        
        Usuario u = controlPers.obtenerUsuario(idUsuario);
        List<Pecera> peceras = u.getListaPeceras();
        peceras.add(p);
        u.setListaPeceras(peceras);
        try {
            controlPers.modificarUsuario(u);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return p;
    
    }

    public int obtenerCantidadImagen() {
        return controlPers.obtenerCantidadImagen();
    }
}
