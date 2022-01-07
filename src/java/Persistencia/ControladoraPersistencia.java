
package Persistencia;

import Logica.Imagen;
import Logica.Pecera;
import Logica.Usuario;
import java.util.List;

public class ControladoraPersistencia {
    UsuarioJpaController usuarioJpa = new UsuarioJpaController();
    PeceraJpaController peceraJpa = new PeceraJpaController();
    PeceraMarinaJpaController peceraMarinaJpa = new PeceraMarinaJpaController();
    PeceraDulceJpaController peceraDulceJpa = new PeceraDulceJpaController();
    ImagenJpaController imagenJpa = new ImagenJpaController();
    
    public void crearUsuario(Usuario u) {
        usuarioJpa.create(u);
    }

    public List<Usuario> obtenerUsuarios() {
        return usuarioJpa.findUsuarioEntities();
    }

    public Pecera crearPecera(Pecera pecera) {
        return peceraJpa.create(pecera);
    }

    public Usuario obtenerUsuario(int idUsuario) {
        return usuarioJpa.findUsuario(idUsuario);
    }

    public void modificarUsuario(Usuario u) throws Exception {
        usuarioJpa.edit(u);
    }

    public Imagen crearImagen(Imagen imagen) {
        return imagenJpa.create(imagen);
    }

    public int obtenerCantidadImagen() {
        return imagenJpa.getImagenCount();
    }
    
}
