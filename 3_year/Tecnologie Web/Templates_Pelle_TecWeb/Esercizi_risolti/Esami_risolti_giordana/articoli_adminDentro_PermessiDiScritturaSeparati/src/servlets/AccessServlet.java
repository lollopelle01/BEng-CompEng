package servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.Article;
import beans.Response;

public class AccessServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8368526876598512649L;
	Gson gson;
	
	@Override
	public void init() throws ServletException {
		gson = new Gson();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Map<String, Article> articles = (Map<String, Article>) getServletContext().getAttribute("articles");
		String name = req.getParameter("article");
		
		Article article = articles.get(name);
		Response response = new Response();
		
		if(article != null) {
			response.setType("access");
			response.setArticle(article);
			
			if(article.getCurrentSession() == null && article.getCurrentSession() != "") {
				response.setResult(true);
				article.setCurrentSession(session.getId());
			} else {
				response.setResult(false);
			}
		} else {
			response.setType("error");
		}
		
		resp.setContentType("application/json");
		resp.getWriter().write(gson.toJson(response));
	}
}
