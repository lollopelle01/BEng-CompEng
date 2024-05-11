package servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.*;

public class Servlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static Map<File, ArrayList<HttpSession>> files;

	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {

		// Inizializzo database utente-file
		files = new HashMap<File, ArrayList<HttpSession>>();

	}

	/**
	 * GET: invio contenuto di file
	 * POST: richiedo scrittura ed aggiorno file
	 */

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Prendo i parametri
		HttpSession sessione = request.getSession();

		// Vedo quale file sta visualizzando
		File target = null;
		for (File f : files.keySet()){
			if(files.get(f).contains(sessione)){target = f; break;}
		}
		if (target == null){sessione.setAttribute("msg", "non stai visualizzando nessun articolo, coglione");}
		else{ // procediamo 

			// Se non ha il permesso aspetta il suo turno
			if((boolean)sessione.getAttribute("permesso")!=true ){
				new AttesaThread(target, request, response, files);
			}
			else{ // Se ha il permesso
				BufferedWriter bw = new BufferedWriter(new FileWriter(target));
				bw.write(request.getParameter("testo"));
				sessione.setAttribute("msg", "Modifica avvenuta correttamente.");
				sessione.setAttribute("permesso", false);
				files.get(target).remove(sessione);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// Prendo i parametri
		HttpSession sessione = request.getSession();
		String fileName = request.getParameter("nome_articolo"); System.out.println(fileName);

		// Così ho associazione al file se esiste e lo creo se non esiste
		File file = new File(fileName);
		if(!file.exists()){file.createNewFile();}

		// Aggiorno il database 
		if (!files.containsKey(file)){ // se file non esiste creo coda
			ArrayList<HttpSession> nuovaCoda = new ArrayList<HttpSession>(); nuovaCoda.add(sessione);
			files.put(file, nuovaCoda);
		}
		else{ // se file esiste, aggiorno la coda
			files.get(file).add(sessione);
		}

		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while((line=br.readLine())!=null){
			response.getWriter().write(line + "\n");
			System.out.println(line);
		}
		br.close(); 
		response.getWriter().close();
	}
}

class AttesaThread extends Thread{
	private HttpServletResponse response;
	private HttpServletRequest request;
	private Map<File, ArrayList<HttpSession>> files;
	private File file;

	public AttesaThread(File file, HttpServletRequest request, HttpServletResponse response, Map<File, ArrayList<HttpSession>> files){
		this.response = response;
		this.request = request;
		this.files = files;
		this.file = file;
	}

	@Override
	public void run(){
		while(files.get(file).get(0)!=request.getSession()){ // C'è qualcuno in fila prima di lui
			request.getSession().setAttribute("msg", "Sei in fila...");
			// Aspetta in fila
		}

		// Ora può accedere alla risorsa
		request.setAttribute("permesso", true);
		request.getSession().setAttribute("msg", "È il tuo turno di modificare.");
	}
}
