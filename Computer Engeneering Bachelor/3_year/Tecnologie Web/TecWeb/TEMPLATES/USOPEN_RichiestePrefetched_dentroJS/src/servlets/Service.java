package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Player;
import beans.Response;
import beans.Tabellone;

public class Service extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6837220559009226772L;
	Tabellone tabellone;
	Gson gson;
	@Override
	public void init() throws ServletException {
		super.init();
		
		tabellone = new Tabellone();
		gson = new Gson();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		
		String surname = request.getParameter("surname");
		Player player = tabellone.getPlayer(surname);
		Response response = new Response();
		
		if(player == null) {
			response.setMessage("Non partecipa agli US Open 2022");
		} else {
			response.setMessage("Player found");
			response.setPlayer(player);
			
			int index = tabellone.getPlayers().indexOf(player);
			response.setPrefetched(new ArrayList<>());
			
			if(index % 32 == 0) {
				for(int i = 0; i < 32; i++) {
					response.getPrefetched().add(tabellone.getPlayers().get(i));
				}
			} else if(index % 32 == 1) {
				for(int i = 32; i < 64; i++) {
					response.getPrefetched().add(tabellone.getPlayers().get(i));
				}
			} else if(index % 32 == 2) {
				for(int i = 64; i < 96; i++) {
					response.getPrefetched().add(tabellone.getPlayers().get(i));
				}
			} else {
				for(int i = 96; i < 127; i++) {
					response.getPrefetched().add(tabellone.getPlayers().get(i));
				}
			}
		}
		
		resp.getWriter().write(gson.toJson(response));
	}
}
