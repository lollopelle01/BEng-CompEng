package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class AddizioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
    
    @Override
	public void init() throws ServletException {
    	super.init();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileJSON = request.getParameter("file");
		String file = gson.fromJson(fileJSON, java.lang.String.class);
		String[] numbers = file.split("\\r?\\n");
		int result = 0;
		for (String n : numbers) {
			try {
				result+=Integer.parseInt(n);
			} catch (Exception e) {
				return;
			}
		}
		response.getWriter().println(gson.toJson(result));
	}


}
