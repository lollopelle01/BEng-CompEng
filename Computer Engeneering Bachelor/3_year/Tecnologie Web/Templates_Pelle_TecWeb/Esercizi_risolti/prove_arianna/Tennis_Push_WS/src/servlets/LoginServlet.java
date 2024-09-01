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
	
	@Override
	public void init() throws ServletException 
	{
		super.init();
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

		Utente u = new Utente();
		u.setNome(username);
		u.setSessioneAttiva(true);
		session.setAttribute("utente", u);
		
		DBUtenti dbUtenti = (DBUtenti) app.getAttribute("DBUtenti");
		dbUtenti.addUtente(u);
		this.getServletContext().setAttribute("DBUtenti", dbUtenti);
		
		
		if (username.equals("admin") && password.equals("admin"))
			response.sendRedirect(request.getContextPath() + "Admin.jsp");
		else 
			response.sendRedirect(request.getContextPath() + "User.jsp");
	}
}
