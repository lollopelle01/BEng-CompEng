package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.Catalogo;
import beans.FinderThread;
import beans.Libro;
import beans.RisultatiPassati;


public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Catalogo catalogo;
	private Gson gson = new Gson();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
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
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeAutore = request.getParameter("nomeautore");
		if (nomeAutore != null && nomeAutore.length() > 0 && !nomeAutore.matches(".*/[0-9]/.*")) {
			Catalogo result = new Catalogo();
			int num = (catalogo.getLibri().size() / 10)+1;
			Libro[] libri = catalogo.getLibri().toArray(new Libro[0]);
			List<FinderThread> threads = new ArrayList<>();
			for (int i=0; i<num; i++) {
				FinderThread thread = new FinderThread(libri, i*10, result, nomeAutore);
				thread.run();
				threads.add(thread);
			}
			
			for (FinderThread thread : threads) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			HttpSession session = request.getSession();
			RisultatiPassati risultatiPassati;
			synchronized (session) {
				risultatiPassati = (RisultatiPassati) session.getAttribute("risultatiPassati");
				if (risultatiPassati == null) {
					risultatiPassati = new RisultatiPassati();
					session.setAttribute("risultatiPassati", risultatiPassati);
				}
			}
			risultatiPassati.addRisultato(result);
			response.getWriter().println(gson.toJson(result.getLibri().toArray()));
		}
	}

}
