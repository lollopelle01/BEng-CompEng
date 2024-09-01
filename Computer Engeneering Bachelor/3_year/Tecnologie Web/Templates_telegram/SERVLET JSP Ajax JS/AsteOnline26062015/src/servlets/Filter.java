package servlets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


/**
 * Servlet implementation class Dispatcher
 */
@WebServlet("/Filter")
public class Filter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Gson gson;
       
	@Override
	public void init() throws ServletException {
		super.init();
		gson = new Gson();
	}
 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stringa = request.getParameter("stringa");
		
		if (Pattern.matches("[a-zA-Z]{3,20}", stringa)) {
			List<String> result = new ArrayList<String>();
			try {
				BufferedReader stream = new BufferedReader (new InputStreamReader(new FileInputStream(getServletContext().getResource("/testo.txt").getFile())));
				String line;
				while ((line = stream.readLine()) != null) {
					if (line.contains(stringa+" "))
						result.add(line);
				}
				response.getWriter().write(gson.toJson(result.toArray()));
				stream.close();
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		else
			throw new ServletException("Stringa non valida");
		
		
		
		
	}


}
