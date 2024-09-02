package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.*;

import com.google.gson.Gson;

public class S1 extends HttpServlet{

	private static final long serialVersionUID = 1L;
	//private static Map<String, ExecThread> threads;
	private Gson g = new Gson();
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		// Inizializzo eventuali mappe/liste x database
		//threads = new HashMap<String, ExecThread>(); // Se c'è bisogno di operazioni concorrenti
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Gestione richieste POST
	}

    @SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Gestione richieste GET
		int[][] matrice = this.parseMatrix(request);
		Result result = new Result();

		/** CALCOLIAMO RIGHE **/
		int somma_righa=0;
		int somma_prec;
		for(int i=0; i<3; i++){
			somma_prec = somma_righa;
			for(int j=0; j<3; j++){
				somma_righa += matrice[i][j];
			}
			if(somma_prec!=somma_righa && somma_prec!=0){ // errore nelle somme
				result.setFlag(false);
				break;
			}
		}
		
		if(result.isFlag()!=false){ // non è stato modificato --> somme valide
			result.setFlag(true);
			result.setSomma(somma_righa);
		}

		response.getWriter().write(g.toJson(result));
	}






	/********* UTILITIES **************************************************************************************************/
	// Metodo utile quando ci si passa matrici
	private int[][] parseMatrix(HttpServletRequest request){
		String[] matrice = request.getParameter("matrice").split(",");
		
		int matrix[][] = new int[3][3];
		int count=0;
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				matrix[i][j] = Integer.parseInt(matrice[count]);
				count++;
			}
		}

		return matrix;
	}

	// Metodo utile quando devi lanciare dei thread
	// private Result gestisciThread(ExecThread thread){
	// 	Result result = new Result();
	// 	try {
	// 		thread.start(); // lanciamo il thread
	// 		thread.join(); // aspettimao il thread per gestirne il risultato
			
	// 		threads.remove(thread.getName());
			
	// 		// Compilo la result
			
	// 	} catch (InterruptedException e) {
	// 		System.out.println("Thread interrupted");
	// 		e.printStackTrace();
	// 		// result.setMessage("Richiesta interrotta dall'amministratore");
	// 	}
	// 	return result;
	// }
	
}
