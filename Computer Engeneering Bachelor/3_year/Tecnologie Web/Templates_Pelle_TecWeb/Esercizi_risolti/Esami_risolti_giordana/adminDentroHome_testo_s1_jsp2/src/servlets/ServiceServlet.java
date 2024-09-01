package servlets;

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

import beans.CounterRichieste;
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
			/* UPDATE STATISTICHE RICHIESTE */
			
			//aggiorniamo la mappa delle richieste per sessione, se nuova sessione inizializziamo, altrimenti aumentiamo il counter
			Map<String, Integer> richiestePerSessione = (Map<String, Integer>) getServletContext().getAttribute("richieste");
			if(richiestePerSessione.get(session.getId()) == null) {
				richiestePerSessione.put(session.getId(), 1);
			} else {
				Integer counter = richiestePerSessione.get(session.getId());
				richiestePerSessione.put(session.getId(), counter + 1);
			}
			
			//questa serve solo per contare le richieste arrivate negli ultimi 60 minuti, quindi ogni ora va resettata, altrimenti incrementata
			CounterRichieste requests = (CounterRichieste) getServletContext().getAttribute("counter");
			Duration duration = Duration.between(requests.getTimeLastRequest(), LocalDateTime.now());
			if(duration.toMinutes() > 60) {
				requests.initialize();	
			}
			requests.updateCounter();
			
			//lista delle sessioni che sono state attive negli utlimi 30 giorni, le metto tutte poi nella admin scelgo quelle giuste
			List<HttpSession> sessioni_attive = (List<HttpSession>) getServletContext().getAttribute("sessioni_attive");
			if(!sessioni_attive.contains(session)) {
				sessioni_attive.add(session);
			}
			
			/*--------------------------------------------------------*/
			/* GESTIONE DELLA RICHIESTA */
			String text = req.getParameter("text");
			
			if(text.isEmpty() || text.length() > 5000) {
				Result result = new Result();
				result.setMessage("Invalide parameter");
				resp.getWriter().write(gson.toJson(result));
			} else {	
				//creo una stringa composta da un carattere random
				String randomChar = (char)(random.nextInt(26) + 'a') + "";
				System.out.println("Servlet: Random char: " + randomChar);
				
				//sostituisco tutti i caratteri (random) all'interno del testo 
				text = text.replaceAll(randomChar, "");
				
				if(!text.contains(randomChar)) {
					System.out.println("Servlet: Carattere " + randomChar + " eliminato correttamente dalla stringa");
				}
				
				session.setAttribute("text", text);
				System.out.println("ServiceServlet: nuovo testo elaborato: "+ text);
				
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/service.jsp");
				dispatcher.forward(req, resp);
				
		}
	}
}
