package servlets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
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
			Map<String, Integer> richiestePerSessione = (Map<String, Integer>) getServletContext().getAttribute("richieste");
			if(richiestePerSessione.get(session.getId()) == null) {
				richiestePerSessione.put(session.getId(), 1);
			} else {
				Integer counter = richiestePerSessione.get(session.getId());
				richiestePerSessione.put(session.getId(), counter + 1);
			}
			
			CounterRichieste requests = (CounterRichieste) getServletContext().getAttribute("counter");
			Duration duration = Duration.between(requests.getTimeLastRequest(), LocalDateTime.now());
			if(duration.toMinutes() > 60) {
				requests.initialize();	
			}
			requests.updateCounter();
			
			/*--------------------------------------------------------*/
			/* GESTIONE DELLA RICHIESTA */
			String fname = req.getParameter("fname");
			if(fname.isEmpty() || fname.length() > 30) {
				Result result = new Result();
				result.setMessage("Invalide parameter");
				resp.getWriter().write(gson.toJson(result));
			} else {
				try {
					BufferedReader reader = new BufferedReader(new FileReader(getServletContext().getRealPath("/resources/" + fname)));
					String line;
					String text = "";
					String chars = "abcdefghijklmnopqrstuvwxyz";
					chars += chars.toUpperCase();
					String c = chars.charAt(random.nextInt(chars.length())) + "";
					while((line = reader.readLine()) != null) {
						text += line.replaceAll(c, "");
					}
					reader.close();
					
					session.setAttribute("text", text);
					System.out.println("ServiceServlet: nuovo testo elaborato: "+text);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/service.jsp");
					dispatcher.forward(req, resp);
				} catch (FileNotFoundException e) {
					Result result = new Result();
					result.setMessage("File not found");
					resp.getWriter().write(gson.toJson(result));
				}
		}
	}
}
