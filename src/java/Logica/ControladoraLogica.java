
package Logica;

import Persistencia.ControladoraPersistencia;
import java.util.List;

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
}
