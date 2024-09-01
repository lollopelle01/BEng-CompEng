package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.User;

public class InitServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Gson gson;
	Map<String, User> database;
	List<String> parole;
	
	@Override
	public void init() throws ServletException {
		this.gson = new Gson();
		
		String path = getServletContext().getRealPath("/resources/database.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			this.database = new HashMap<>();
			String line;
			while((line = reader.readLine()) != null) {
				String[] info = line.split("#####");
				User user = new User();
				user.setusername(info[0]);
				user.setPassword(info[1]);
				System.out.println(user);
				database.put(info[0], user);
			}
			reader.close();
			getServletContext().setAttribute("database", database);
			getServletContext().setAttribute("gson", gson);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String path2 = getServletContext().getRealPath("/resources/cruciverba.txt");
		try {
			BufferedReader reader2 = new BufferedReader(new FileReader(path2));
			this.parole = new ArrayList<String>();
			String line2;
			
			while((line2 = reader2.readLine()) != null) {
				line2= line2.toUpperCase();
				
				this.parole.add(line2);
			}
			reader2.close();
			
			getServletContext().setAttribute("parole", parole);
			System.out.println("Server: parole possibili: " + this.parole.toString());
			
			char grid[][] = new char[10][10];
			
			for(int i=0; i<10; i++)
				for(int j=0; j<10; j++)
					grid[i][j]= ' ';
			
			getServletContext().setAttribute("grid", grid);
			
			System.out.println("Server inizializzato correttamente.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("./login.jsp");
	}
}
