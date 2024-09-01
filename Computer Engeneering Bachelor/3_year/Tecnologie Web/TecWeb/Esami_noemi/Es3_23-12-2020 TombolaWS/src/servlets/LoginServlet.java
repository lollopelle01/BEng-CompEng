package servlets;

//import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used for authentication process.
 * @author rootLeo00
 */

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	//private Gson gson;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");

		String password=req.getParameter("password");
		String username=req.getParameter("username");


		if(username.equals("admin") && password.equals("admin")) {
			req.getSession().setAttribute("username", username);
			resp.sendRedirect(req.getContextPath()+"/admin.jsp");
			return;//DA MODIFICARE
			/*RequestDispatcher requestDispatcher = req.getRequestDispatcher(req.getContextPath()+"/pages/cart.jsp");
					requestDispatcher.forward(req, resp);*/
		}
		else {
			req.setAttribute("result","<p><strong>PASSWORD ERRATA<strong><p/><br/><br/>");
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
			requestDispatcher.forward(req, resp);
			return;
		}
	}


}

