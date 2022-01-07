
package Servlets;

import Logica.ControladoraLogica;
import Logica.Imagen;
import Logica.Pecera;
import Logica.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet(name = "SvAltaPecera", urlPatterns = {"/SvAltaPecera"})
public class SvAltaPecera extends HttpServlet {

    ControladoraLogica control = new ControladoraLogica();
    private String pathFiles = "C:\\Users\\Agustina\\OneDrive\\UTN\\Cursos\\Curso JAVA - Udemy\\FishApp\\web\\files\\";
    private File uploads = new File(pathFiles);
    private String[] extens = {".ico",".png",".jpg",".jpeg"};
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre = request.getParameter("nombre");
        String tipoAgua = request.getParameter("tipoAgua");
        double alto = 0.0;
        double ancho = 0.0;
        double largo = 0.0;
        double capacidad = 0.0;
        
        if(!(request.getParameter("alto").equals(""))){
            alto = Double.parseDouble(request.getParameter("alto"));
        }
        
        if(!(request.getParameter("ancho").equals(""))){
            ancho = Double.parseDouble(request.getParameter("ancho"));
        }
        
        if(!(request.getParameter("largo").equals(""))){
            largo = Double.parseDouble(request.getParameter("largo"));
        }
        
        if(!(request.getParameter("capacidad").equals(""))){
            capacidad = Double.parseDouble(request.getParameter("capacidad"));
        }
        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = null;
        Date fechaFin = null;
        try {
            fechaInicio = formato.parse(request.getParameter("fechaInicio"));
            if(request.getParameter("fechaFin") != null){
                fechaFin = formato.parse(request.getParameter("fechaFin"));
            }
        } catch (ParseException ex) {
            ex.printStackTrace(System.out);
        }
        String filtrado = request.getParameter("filtrado");
        String dosificadora = request.getParameter("dosificadora");
        String sump = request.getParameter("sump");
        String iluminacion = request.getParameter("iluminacion");
        Part part = request.getPart("foto");
        String foto = "";
        if(part != null){
            if(isExtension(part.getSubmittedFileName(), extens)){
                foto = saveFile(part, uploads);
            }
        }
        
        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
        
        try {
            Pecera creada = control.crearPecera(u.getIdUsuario(), nombre, tipoAgua, alto, ancho, largo, capacidad, fechaInicio, fechaFin, filtrado, dosificadora, sump, iluminacion, foto);
            
            //Actualizo la lista de peceras del usuario
            List<Pecera> listaPeceras = u.getListaPeceras();
            listaPeceras.add(creada);
            u.setListaPeceras(listaPeceras);
            
            response.sendRedirect("principal.jsp");
        } catch (Exception ex) {
            Logger.getLogger(SvAltaPecera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private String saveFile(Part part, File pathUploads){
        String pathAbsolute = "";
        String fileName = "";
        try {
            Path path = Paths.get(part.getSubmittedFileName());
            fileName = path.getFileName().toString();
            InputStream input = part.getInputStream();
            /*int idImagen = control.obtenerCantidadImagen();
                idImagen++;
                File file = new File(pathUploads, Integer.toString(idImagen));
            */
            if(input != null){
                File file = new File(pathUploads, fileName);
                pathAbsolute = file.getAbsolutePath();
                Files.copy(input, file.toPath());
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return fileName;
    }
    
    private boolean isExtension(String fileName, String[] extensions){
        for(String et: extensions){
            if(fileName.toLowerCase().endsWith(et)){
                return true;
            }
        }
        return false;
    }
    
}
