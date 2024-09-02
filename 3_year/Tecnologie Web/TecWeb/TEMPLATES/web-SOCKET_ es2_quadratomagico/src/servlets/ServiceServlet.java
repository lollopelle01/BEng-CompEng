package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Result;


public class ServiceServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7554977297793320847L;
	Gson gson;
	Random random;
	
	@Override
	public void init() throws ServletException {
		this.gson = new Gson();
		this.random = new Random(System.currentTimeMillis());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
		/*--------------------------------------------------------*/
		/* GESTIONE DELLA RICHIESTA */
		
		String[] matrice = req.getParameter("matrice").split(",");
		
		int matrix[][] = new int[3][3];
		int count=0;
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				matrix[i][j] = Integer.parseInt(matrice[count]);
				count++;
			}
		}
		
		System.out.println("Servlet: " + matrix.toString());
		
		boolean primo_controllo = false;
		
		if(!primo_controllo) {
			primo_controllo=true;
			
			int somma[]=new int [3];
			boolean controllo = false;
			//devo controllarre la somma delle righe
			for(int i=0; i<3; i++) {
				somma[i] =0;
				for(int j=0; j<3; j++) {
					somma[i] += matrix[i][j];
				}
			}
			
			if(somma[0] == somma[1] && somma[1] == somma[2] && somma[0] == somma[2]) {
				controllo = true;
			}
			
			Result r = new Result();
			r.setEsito(controllo);
			if(controllo) {
				r.setSomma(somma[0]);
			}
			
			PrintWriter out = resp.getWriter();
			out.write(this.gson.toJson(r));
		}
		else {
			if(primo_controllo) {
				int somma[]=new int [3];
				boolean controllo = false;
				
				//devo controllarre la somma delle colonne
				for(int i=0; i<3; i++) {
					somma[i] =0;
					for(int j=0; j<3; j++) {
						somma[i] += matrix[j][i];
					}
				}
				
				if(somma[0] == somma[1] && somma[1] == somma[2] && somma[0] == somma[2]) {
					System.out.println("Servlet: controllo righe true");
					controllo = true;
				}
				
				if(controllo) {
					//devo controllare le 2 diagonali
					int sommad[] = new int[2];
					
					sommad[0] = matrix[0][0] + matrix[1][1] + matrix[2][2];
					sommad[1] = matrix[2][0] + matrix[1][1] + matrix[0][2];
					
					if(sommad[0] == sommad[1] && sommad[0] == somma[0]) {
						System.out.println("Servlet: controllo su diagonali e colonne true");
					}
					else
						controllo=false;
					
					Result r = new Result();
					r.setEsito(controllo);
					if(controllo) {
						r.setSomma(somma[0]);
					}
					
					PrintWriter out = resp.getWriter();
					out.write(this.gson.toJson(r));
				}
			}
			else {
				System.out.println("Server: non riconosicuto parametro operazione");
			}
		}
			
	}
}