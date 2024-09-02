package servlets;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Utente;
import beans.Whiteboard;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Whiteboard whiteboard;
    
    @Override
	public void init() throws ServletException {
    	super.init();
    	ServletContext application = this.getServletContext();
    	synchronized (application) {
			//Inizializzazione stato di applicazione
			whiteboard = (Whiteboard) application.getAttribute("whiteboard");
			if (whiteboard == null) {
				whiteboard = new Whiteboard();
				application.setAttribute("whiteboard", whiteboard);
			}
		}
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Invio tutta la whiteboard dell'utente
		//Servlet:
		Utente utente = (Utente) request.getSession().getAttribute("utente");
		if (utente == null) {
			request.setAttribute("loginerror", "Non sei autenticato!");
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		}
		utente.setActualVersion(whiteboard.getVersion());
		response.getWriter().println("<ul>");
		for (String line : whiteboard.getLinesFromVersion(utente.getAccessoVersion())) {
			response.getWriter().println("<li>"+line+"</li>");
		}
		response.getWriter().println("</ul>");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Aggiorno e invio la whiteboard aggiornata
		Utente utente = (Utente) request.getSession().getAttribute("utente");
		if (utente == null) {
			request.setAttribute("loginerror", "Non sei autenticato!");
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			String aut = (String) request.getSession().getAttribute("authorization");
			if (aut != null && aut.equals("yes")) {
				String cancella = request.getParameter("cancellatutto");
				if (cancella != null && cancella.equals("cancellatutto")) {
					whiteboard.reset();
					getServletContext().getRequestDispatcher("/whiteboard.jsp").forward(request, response);
				}
			}
		}
		String lineToAdd = request.getParameter("linea");
		if (lineToAdd != null && lineToAdd.length() > 0 && whiteboard.tryAddLine(lineToAdd, utente.getActualVersion())) {
				doGet(request, response);
		} else {
			response.getWriter().println("versionmismatch");
		}
	}


}
