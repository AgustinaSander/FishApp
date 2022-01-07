
package Servlets;

import Logica.ControladoraLogica;
import Logica.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SvUsuario", urlPatterns = {"/SvUsuario"})
public class SvUsuario extends HttpServlet {

    ControladoraLogica control = new ControladoraLogica();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect("index.jsp");
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Obtengo los datos de login
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        Usuario usuarioLogueado = control.iniciarSesion(email, password);
        if(usuarioLogueado != null){
            HttpSession miSession = request.getSession();
            miSession.setAttribute("usuario", usuarioLogueado);
            
            response.sendRedirect("principal.jsp");
        }
        else{
            request.setAttribute("msg", "<i class=\"fas fa-exclamation-circle text-danger\"></i> Los datos son incorrectos");
            request.setAttribute("error",true);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }
        
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
