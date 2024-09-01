package servlets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			HttpSession session = req.getSession();
			int counter = 1;
			
			if(session.getAttribute("counter") != null) {
				counter = (int) session.getAttribute("counter");
				session.setAttribute("counter", counter +1);
			}
			else {
				session.setAttribute("counter", counter);
			}
			
			String username = (String) session.getAttribute("username");
			
			char grid[][] = (char[][]) getServletContext().getAttribute("grid");
			
			List<String> parole = (List<String>) getServletContext().getAttribute("parole");
			/*--------------------------------------------------------*/
			/* GESTIONE DELLA RICHIESTA */
			Result result = new Result();
			
			if(counter > 500) {
				System.out.println("Service: l'utente " + username + ", ha superato il limite di 500 caratteri modificabili");
				result.setMessage("Hai superato il limite massimo consentito di caratteri modificabili");
				resp.getWriter().write(gson.toJson(result));
			}
			else {
				String id[] = (String[]) req.getParameter("id").split("_");
				String c = (String) req.getParameter("char");
				
				int i = Integer.parseInt(id[0]);
				int j = Integer.parseInt(id[1]);
				
				System.out.println("Service: l'utente " + username + ", nella riga: " + i + " e colonna: "+ j +", vorrebbe scrivere: " + c);
				
				if(c.isEmpty() || c.length() != 1 || i<0 || j<0 || i>9 || j>9) {
					result.setMessage("Invalide parameter");
					resp.getWriter().write(gson.toJson(result));
				} else {
					grid[i][j] = c.charAt(0);
					
					//ora controlliamo la riga nel caso sia piena
					boolean completa = true;
					String linea = "";
					for(int h=0; h<10; h++) {
						if(grid[i][h] == ' ')
							completa = false;
						else
							linea += grid[i][h];
					}
					System.out.println("Service: l'utente " + username + ", scrive " + linea);
					
					if(completa) {
						System.out.println("Service: l'utente " + username + ", ha complettao la riga " + i + " con: " + linea);
						if(parole.contains(linea))
							result.setMessage("parola riga corretta");
						else
							result.setMessage("parola riga non corretta");
					}
					else
						result.setMessage("carattere aggiunto correttamente");
					
					
					//ora controlliamo la colonna nel caso sia piena
					boolean completa2 = true;
					String colonna = "";
					for(int h=0; h<10; h++) {
						if(grid[h][j] == ' ')
							completa2 = false;
						else
							colonna += grid[h][j];
					}
					System.out.println("Service: l'utente " + username + ", scrive " + colonna);
					if(completa2) {
						System.out.println("Service: l'utente " + username + ", ha complettao la colonna " + j + " con: " + colonna);
						if(parole.contains(colonna))
							result.setMessage_colonna("parola colonna corretta");
						else
							result.setMessage_colonna("parola colonna non corretta");
					}
					
					
					resp.getWriter().write(gson.toJson(result));
				}
			}
	}
}
