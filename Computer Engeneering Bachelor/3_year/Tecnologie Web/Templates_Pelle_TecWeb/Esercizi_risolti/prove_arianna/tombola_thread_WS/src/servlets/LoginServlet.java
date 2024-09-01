package servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.DBUtenti;
import beans.Utente;

public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private String firstPageAfterLogin;
	private String adminPageAfterLogin;
	
	@Override
	public void init() throws ServletException 
	{
		super.init();
		firstPageAfterLogin = this.getServletContext().getInitParameter("firstPageAfterLogin"); //CAMBIA PAGINA IN WEB.XML
		adminPageAfterLogin = this.getServletContext().getInitParameter("adminPageAfterLogin"); //CAMBIA PAGINA IN WEB.XML
		this.getServletContext().setAttribute("DBUtenti", new DBUtenti());
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		ServletContext app = this.getServletContext();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username == null || password == null)
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);

		if (session.getAttribute("utente") == null) {
				Utente u = new Utente();
				u.setNome(username);
				u.setSessioneAttiva(true);
				session.setAttribute("utente", u);
				DBUtenti dbUtenti = (DBUtenti) app.getAttribute("DBUtenti");
				dbUtenti.addUtente(u);
				this.getServletContext().setAttribute("DBUtenti", dbUtenti);
				
				if (username.equals("admin") && password.equals("admin"))
					response.sendRedirect(request.getContextPath() + adminPageAfterLogin);
				else 
					response.sendRedirect(request.getContextPath() + firstPageAfterLogin);
		} else
			getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
			//già loggato, effettuo la ridirezione nella login.jsp
	}
}
