package servlets;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;

import beans.Canzone;
import beans.ErrorMessage;
import beans.GenericMessage;
import beans.StringMessage;

public class MusicaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Gson G = new Gson();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		String dir_path = getServletContext().getRealPath("CanzoniNatalizie");
		System.out.println(dir_path);
		File[] lista_file = new File(dir_path).listFiles();
		System.out.println(G.toJson(lista_file));

//		int c = Integer.parseInt(request.getParameter("c"));
		int i = Integer.parseInt(request.getParameter("i"));

		if (i < lista_file.length) {
			if (i == AdminServlet.canzoneBloccata) {
				out.println(G.toJson(new StringMessage("canzone-bloccata","download bloccato")));
			} else {
				out.println(G.toJson(new GenericMessage<Canzone>("canzone", new Canzone(lista_file[i]))));
			}
		} else {
			out.println(G.toJson(new ErrorMessage("i troppo alto")));
		}
	}
}