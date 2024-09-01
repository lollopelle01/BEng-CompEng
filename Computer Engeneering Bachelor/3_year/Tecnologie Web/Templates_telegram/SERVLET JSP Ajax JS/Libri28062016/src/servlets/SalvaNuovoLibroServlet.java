package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Catalogo;
import beans.Libro;

public class SalvaNuovoLibroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Catalogo catalogo;
	private Gson gson = new Gson();
    
    @Override
	public void init() throws ServletException {
    	super.init();
    	ServletContext application = this.getServletContext();
		synchronized (application) {
			//Inizializzazione stato di applicazione
			catalogo = (Catalogo) application.getAttribute("catalogo");
			if (catalogo == null) {
				catalogo = new Catalogo();
				application.setAttribute("catalogo", catalogo);
			}
		}
    }

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String authorized = (String) request.getSession().getAttribute("authorization");
		PrintWriter out = response.getWriter();
		if (authorized != null && authorized.equals("yes")) {
			String libroString = request.getParameter("libro");
			if (libroString != null) {
				Libro libro = gson.fromJson(libroString, Libro.class);
				if (catalogo.aggiungiLibro(libro))
					out.println("Libro aggiunto correttamente!");
				else
					out.println("Libro non aggiunto perche' gia' presente");
			} else {
				out.println("Nessun libro in richiesta...");
			}
		} else {
			out.println("Hai tentato un'azione non autorizzata...");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


}
