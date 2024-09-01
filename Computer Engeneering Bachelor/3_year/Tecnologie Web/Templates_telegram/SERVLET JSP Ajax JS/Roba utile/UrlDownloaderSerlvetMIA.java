package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Utente;
import beans.UtentiManager;

public class JSONServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UtentiManager utentiManager;
	Gson gson = new Gson();
    
    @Override
	public void init() throws ServletException {
    	super.init();
    	ServletContext application = this.getServletContext();
		synchronized (application) {
			//Inizializzazione stato di applicazione
			utentiManager = (UtentiManager) application.getAttribute("utentiManager");
			if (utentiManager == null) {
				utentiManager = new UtentiManager();
				application.setAttribute("utentiManager", utentiManager);
			}
		}
    }

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url1 = request.getParameter("url1");
		String url2 = request.getParameter("url2");
		String url3 = request.getParameter("url3");
		if (url1 != null && url2 != null && url3 != null) {
			List<String> urls = new ArrayList<>();
			urls.add(url1);
			urls.add(url2);
			urls.add(url3);
			
			List<String> result = new ArrayList<>();
			
			for (String urlString : urls) {
				URL url = new URL(urlString);
				InputStream stream = url.openStream();
				StringBuilder sb = new StringBuilder();
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				reader.close();
				result.add(sb.toString());
			}
			
			response.getWriter().println(gson.toJson(result.toArray()));
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


}
