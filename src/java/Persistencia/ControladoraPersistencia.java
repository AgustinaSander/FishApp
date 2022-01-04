
package Persistencia;

import Logica.Usuario;
import java.util.List;

public class ControladoraPersistencia {
    UsuarioJpaController usuarioJpa = new UsuarioJpaController();
    
    public void crearUsuario(Usuario u) {
        usuarioJpa.create(u);
    }

    public List<Usuario> obtenerUsuarios() {
        return usuarioJpa.findUsuarioEntities();
    }
    
}
