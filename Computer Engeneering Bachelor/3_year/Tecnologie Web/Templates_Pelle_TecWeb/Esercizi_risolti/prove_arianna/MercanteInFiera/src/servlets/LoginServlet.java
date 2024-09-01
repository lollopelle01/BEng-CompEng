package servlets;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.Gioco;
import beans.Utente;

public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private String firstPageAfterLogin = "mainUser.jsp";
	
	@Override
	public void init() throws ServletException {
		super.init();
		this.getServletContext().setAttribute("gioco", new Gioco());
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		ServletContext app = this.getServletContext();
		Gioco gioco = (Gioco) app.getAttribute("gioco");
		
		if(gioco.isGiocoCominciato()) {
			app.getRequestDispatcher("/access_denied.html").forward(request, response);
			return;
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Utente u;
		
		if (username == null || password == null)
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);

		else if (session.getAttribute("utente") == null) {
				u = new Utente();
				u.setNome(username);
				u.setSessioneAttiva(true);
				
				session.setAttribute("utente", u);
				gioco.getUtenti().add(u);
				
				gioco.addMessaggio("Aggiunto utente "+u.getNome());
				if(gioco.getUtenti().size()==2) {
					gioco.initGioco();
					gioco.addMessaggio("Gioco iniziato alle "+Instant.now().toString());
				}
				response.sendRedirect(request.getContextPath() + firstPageAfterLogin);
		} else
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
	}
}
