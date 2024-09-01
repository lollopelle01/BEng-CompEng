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

public class AppendServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1317192435074620680L;
	Gson gson;
	@Override
	public void init() throws ServletException {
		gson = new Gson();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String text = req.getParameter("text");
		String name = req.getParameter("article");
		resp.setContentType("application/json");
		
		Response response = new Response();
		HttpSession session = req.getSession();
		
		Map<String, Article> articles = (Map<String, Article>) getServletContext().getAttribute("articles");
		Article article = articles.get(name);
		System.out.println("Current session: " + article.getCurrentSession() + "\tSession: " + session.getId());
		
		if(article.getCurrentSession().equals(session.getId())) {
			article.setText(article.getText() + text);
			
			response.setResult(true);
			response.setArticle(article);
		} else {
			response.setResult(false);
		}
		
		resp.getWriter().write(gson.toJson(response));
	}
}
