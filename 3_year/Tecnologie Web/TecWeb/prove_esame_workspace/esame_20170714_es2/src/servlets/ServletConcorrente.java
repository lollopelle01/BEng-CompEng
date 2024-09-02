package servlets;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ServletConcorrente extends HttpServlet{

	private static final long serialVersionUID = 1L;
	Gson g;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Ho ricevuto il testo tramite una post, faccio due thread e assegno a ognuno di loro la parte di testo
		
		// Estraggo i parametri
		String testo = request.getParameter("text_area"); int len = testo.length();
		String fileName = request.getParameter("file_name");
		
		System.out.println("Valore del campo text_area: " + testo);
        System.out.println("Valore del campo file_name: " + fileName);
		
		// Creo ed avvio i thread
		new ServletConcorrenteThread((String) testo.subSequence(0, len/2), false, fileName, response).start(); // a->A su prima metà
		new ServletConcorrenteThread((String) testo.subSequence(len/2, len), true, fileName, response).start(); // A->a su seconda metà
	}
	
}

class ServletConcorrenteThread extends Thread {
	private String testo;
	private boolean modalita; //true: A->a		false: a->A
	private String fileName;
	private HttpServletResponse response;
	
    public ServletConcorrenteThread(String testo, boolean modalita, String fileName, HttpServletResponse response) {
		super();
		this.testo = testo;
		this.modalita = modalita;
		this.fileName = fileName;
		this.response = response;
	}

	@Override
    public synchronized void run() {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
			
			// Scrivo il file
			if (!this.modalita) { // a->A
				// Calcolo
				int numCharMod = this.testo.replaceFirst("[^a-z]", "").length();
				bw.write(this.testo.toUpperCase());
				
				// creo risposta
				Gson g = new Gson();
				String risposta = "<!DOCTYPE html><html><head></head><body>Numero caratteri modificati (a->A):" + numCharMod + "</body></html>";
				
				// invio risposta
				this.response.getWriter().println(risposta);
				this.response.getWriter().close();
				this.response.sendRedirect("./start.html");
	        }
	        else { // A->a
	        	bw.write(this.testo.toLowerCase());
	        }
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }
}
