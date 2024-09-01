<%@page import="com.google.gson.Gson"%>
<%@page import="beans.ExecThread"%>
<%@page import="beans.Result"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Service</title>
	</head>
	<body>
		<%
		Gson gson = new Gson();
		
		//mappa per il controllo dei thread
		Map<String, ExecThread> threads;
		if((threads = (Map<String, ExecThread>) getServletContext().getAttribute("threads")) == null ) {
			threads = new HashMap<>();
			getServletContext().setAttribute("threads", threads);
		}
		else
			threads = (Map<String, ExecThread>) getServletContext().getAttribute("threads");
		
		
		//ricezione matrice//
				String[] matrice = request.getParameter("matrice").split(",");
				
				int matrix[][] = new int[5][5];
				int count=0;
				
				for(int i=0; i<5; i++) {
					for(int j=0; j<5; j++) {
						matrix[i][j] = Integer.parseInt(matrice[count]);
						count++;
					}
				}
				System.out.println("JSP: " + matrix.toString());
				
				//delegazione a un thread//
				ExecThread thread = new ExecThread(2, matrix);
				
				threads.put(thread.getName(), thread);
				
				try {
					thread.start();
					thread.join();
					
					threads.remove(thread.getName());
					
					Result result = new Result();
					result.setResult(thread.isResult());
					System.out.println("JSP: result: " +thread.isResult());
					
					if(thread.isResult()) {
						result.setSomma(thread.getSomma());
						System.out.println("JSP: SOMMMA: " +thread.getSomma());
					}
					
					response.getWriter().write(gson.toJson(result));
					
					response.getWriter().close();
					
				} catch (InterruptedException e) {
					System.out.println("JSP: Thread interrupted");
					e.printStackTrace();
					
					Result result = new Result();
					result.setMessage("Richiesta interrotta dall'amministratore");
					response.getWriter().write(gson.toJson(result));
					response.getWriter().close();
				}
		%>
	</body>
</html>