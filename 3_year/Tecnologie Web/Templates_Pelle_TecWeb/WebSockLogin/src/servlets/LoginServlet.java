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

public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		//mappa per raccogliere le informazioni di ogni utente che sta eseguendo le operazioni
		Map<String, UserInfo> users = new HashMap<>();
		
		//se la mappa non è nulla la salvo nella mia variabile
		if(getServletContext().getAttribute("database") != null) {
			users = (Map<String, UserInfo>) getServletContext().getAttribute("database");
		} else {
			
			//altrimenti va inizializzato il database
			UserInfo u = new UserInfo();
			u.setUsername("Pelle");
			u.setPassword("Amici123@");
			u.setCounter(1);
			users.put("Pelle", u);
			
			getServletContext().setAttribute("database", users);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//dai parametri della request ottengo username e password
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//questi parametri vanno salvati nella sessione corrente
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		
		//dal contesto della servlet poi ottengo il database
		Map<String, UserInfo> users = (Map<String, UserInfo>) getServletContext().getAttribute("database");
		
		if(username.compareTo("admin")==0 && password.compareTo("admin")==0) {
			//se il login corrisponde a quello dell'admin va reindizzato alla sua personale pagina
			session.setAttribute("logged", true);
			response.sendRedirect("./admin.jsp");
		} 
		if(users.containsKey(username) && users.get(username).getPassword().compareTo(password)==0) {
			//se il login corrisponde a quello di un utente va reindizzato alla home degli utenti
			session.setAttribute("logged", true);
			response.sendRedirect("./home.jsp");
		}
		else {
			//altrimenti non è registrato
			session.setAttribute("login_msg", "Password/Username errati oppure Utente non registrato");
			response.sendRedirect("./login.jsp");
		}
	}
}
