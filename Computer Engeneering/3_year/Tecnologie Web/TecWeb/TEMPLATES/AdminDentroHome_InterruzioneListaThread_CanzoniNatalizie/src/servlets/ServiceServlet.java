package servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.ExecThread;
import beans.Info;
import beans.Result;

public class ServiceServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1679299513106245654L;
	
	Gson gson;
	
	@Override
	public void init() throws ServletException {
		
		this.gson = new Gson();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "application/json");
		
		if(getServletContext().getAttribute("threads")==null) {
			resp.sendRedirect("./init");
		}
		
		Map<String, List<ExecThread>> threads = (Map<String, List<ExecThread>>) getServletContext().getAttribute("threads");
		
		int num = Integer.parseInt((String) req.getParameter("num"));
		
		System.out.println("Service: nuova richiesta di download con num: " + num);
		
		String path = "CanzoniNatalizie";
		
		List<String> nomi = (List<String>) getServletContext().getAttribute("nomi");
		
		int cicli_pieni = threads.keySet().size() / num;
		int resto = threads.keySet().size() % num;
		int numero_cicli = cicli_pieni + (resto == 0 ? 0 : 1);
		
		Result result = new Result();
		
		int count_cicli = 1;
		int count_canzone = 0;
		for(int i=0; i<numero_cicli; i++) {
			if(count_cicli <= cicli_pieni) {
				//bisogna creare num thread perchè si tratta di un ciclo pieno
				ExecThread[] thread = new ExecThread[num];
				for(int j=0; j<num; j++) {
					thread[j] = new ExecThread();
					thread[j].setCanzone(path + "/" + nomi.get(count_canzone));
					thread[j].setCount(count_canzone);
					
					threads.get(nomi.get(count_canzone)).add(thread[j]);
					
					count_canzone++;
				}
				
				//vanno avviati num thread concorrentemente alla volta
				for(int j=0; j<num; j++) {
					try {
						thread[j].start();
					}catch (Exception e) {
						System.out.println("Thread interrupted");
						e.printStackTrace();
						
						result.addMessage("ERRORE NELL'AVVIO DEL THREAD: "+ thread[j].getCount());
					}
					
				}
				
				//si aspetta la fine dei num thread e si passa al ciclo successivo 
				for(int j=0; j<num; j++) {
					try {
						thread[j].join();
						
						threads.get(nomi.get(thread[j].getCount())).remove(thread[j]);
						
						result.addMessage("ESECUZIONE CORRETTA DEL THREAD: "+ thread[j].getCount());
						result.addInfo(new Info(thread[j].getTitolo(), thread[j].getDimensione(), 
								thread[j].getFormato(), thread[j].getContenuto()));
						
						System.out.println("ESECUZIONE CORRETTA DEL THREAD: "+ thread[j].getCount());
						
					} catch (InterruptedException e) {
						System.out.println("Thread interrupted");
						e.printStackTrace();
						
						result.addMessage("ADMIN INTERROMPE THREAD: " + thread[j].getCount());
					}
				}
				//arrivati qui è terminato un ciclo pieno, si passa al successivo
				count_cicli++;
			}else {
				if(resto != 0) {
					//bisogna creare resto thread perchè è l'ultimo ciclo e mancano canzoni 
					ExecThread[] thread = new ExecThread[resto];
					for(int j=0; j<resto; j++) {
						thread[j] = new ExecThread();
						thread[j].setCanzone(path + "/" + nomi.get(count_canzone));
						thread[j].setCount(count_canzone);
						
						threads.get(nomi.get(count_canzone)).add(thread[j]);
						
						count_canzone++;
					}
					
					for(int j=0; j<resto; j++) {
						try {
							thread[j].start();
						}catch (Exception e) {
							System.out.println("Thread interrupted");
							e.printStackTrace();
							
							result.addMessage("ERRORE NELL'AVVIO DEL THREAD: "+ thread[j].getCount());
						}
						
					}
					
					for(int j=0; j<resto; j++) {
						try {
							thread[j].join();
							
							threads.remove(thread[j].getName());
							
							result.addMessage("ESECUZIONE CORRETTA DEL THREAD: "+ thread[j].getCount());
							result.addInfo(new Info(thread[j].getTitolo(), thread[j].getDimensione(), 
									thread[j].getFormato(), thread[j].getContenuto()));
							
							System.out.println("ESECUZIONE CORRETTA DEL THREAD: "+ thread[j].getCount());
							
						} catch (InterruptedException e) {
							System.out.println("Thread interrupted");
							e.printStackTrace();
							
							result.addMessage("ADMIN INTERROMPE THREAD: " + thread[j].getCount());
						}
						
					}
				}//fine if resto !=0
				
				//else: se resto == 0 bastanao i cicli pieni
			}//fine else ultimo ciclo
		}//fine for iterazione per numero di cicli necessario
		resp.getWriter().write(this.gson.toJson(result));
	}
}
