package servlet;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import bean.Data;
import bean.Matrix;
import bean.Result;

public class CalculateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Gson gson;
	private List< HttpSession> sessions;

	@Override
	public void init() {
		gson = new Gson();	

	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		
		this.sessions.add(request.getSession());
		mySetAttribute("sessions",this.sessions);
		
		Data data = gson.fromJson(request.getParameter("dati"), Data.class);
		
		// Data data = gson.fromJson(request.getReader(), Data.class);
		
		int[][] mA = data.getMatrA().getMatr();
		int[][] mB = data.getMatrB().getMatr();
		
		int mlength = data.getMatrA().getMatrLength();
		
		int mres[][] = new int[mlength][mlength/2];     //la matrice res ha mlength righe e mlength/2 colonne
		
		if(mA==null || mB==null) {
			response.getWriter().println(gson.toJson(new Error("error: matrix is null")));
		}
		else {
			// faccio la somma delle sue matrici , le colonne devono essere col/2
			for(int i=0;i<mlength; i++) {
				for(int j=0;j<mlength/2; j++) {
					mres[i][j] = mA[i][j] + mB[i][j];
					}
				}
			}
		Matrix res = new Matrix(mres);
		response.getWriter().println(gson.toJson(res));
	}
	
	//set attribute con synchronized
	synchronized private void mySetAttribute(String arg0, Object arg1) {
		this.getServletContext().setAttribute(arg0,arg1);
	}

}

