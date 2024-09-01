package servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.ExecThread;

public class InitServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Gson gson;
	
	@Override
	public void init() throws ServletException {
		this.gson = new Gson();
		
		getServletContext().setAttribute("gson", gson);
		
		String path = getServletContext().getRealPath("CanzoniNatalizie");
		File dir = new File("CanzoniNatalizie");
		
		//mappa che associa per ogni canzon epresente nella cartella 
		//una lista di thread attivvi che stanno cercando di scaricarla
		Map<String, List<ExecThread>> canzoni = new HashMap<>();
		
		List<String> nomi = new ArrayList<>();
		
		System.out.println("Init: " + dir.list().toString());
		
		
		
		if(dir.isDirectory()) {
			String nome[] = dir.list();
			
			if(nome.length != 0) {
				for(int i=0; i<nome.length; i++) {
					canzoni.put(nome[i], new ArrayList<>());
					nomi.add(nome[i]);
					
					System.out.println("Init: trovato file nella cartella CanconiNatalizie: " + nome[i]);
				}
			}else {
				System.out.println("Init: errore con la cartella CanzoniNatalizie, non contiene file");
			}
			
		}else {
			System.out.println("Init: errore con la cartella CanzoniNatalizie, non è una directory");
		}
		
		
		getServletContext().setAttribute("threads", canzoni);
		getServletContext().setAttribute("nomi", nomi);
			
		System.out.println("Server inizializzato correttamente.");
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("./home.jsp");
	}
}
