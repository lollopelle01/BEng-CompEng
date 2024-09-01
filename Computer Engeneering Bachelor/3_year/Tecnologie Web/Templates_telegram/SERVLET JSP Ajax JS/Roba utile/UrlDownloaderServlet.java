package esame.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlDownloaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String URL_TO_DOWNLOAD = "urlToDownload";

	// riceve un url da scaricare come attributo di request e lo
	// scrive in output, settando anche content type
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String urlString = (String) request.getAttribute(URL_TO_DOWNLOAD); 
		if (urlString!=null){
			PrintWriter out = response.getWriter();
			URLConnection connection = (new URL(urlString)).openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			response.setContentType(connection.getContentType());
			String line;
			while ((line = reader.readLine())!=null)
				out.println(line);
		}
	}
}
