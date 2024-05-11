<%@ page session="true"%>
<%@ page errorPage="../errors/failure.jsp"%>

<%@ page import="java.util.*"%>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="beans.Risultato" %>

<%
//solo codice java perchè mi serve solo per il calcolo

	int[][] matrix = new int[5][5];
	StringTokenizer tokenizer = new StringTokenizer(request.getParameter("values"),"@");
	for(int i=0; i<5; i++) {
		for(int j=0; j<5; j++) {
			matrix[i][j] = Integer.parseInt(tokenizer.nextToken());
		}
	}
	
	int lastCount = 0, currentCount = 0;
	boolean res = true;
	
	for(int i=0; i<5; i++) {
		for(int j=0; j<5; j++) {
			currentCount += matrix[j][i]; //scorro le colonne
		}
		
		if (lastCount != 0 && currentCount != lastCount) {
			res = false;
			break;
		} else
			lastCount = currentCount;
	}
	
	Gson g = new Gson();
	response.getWriter().println(g.toJson(new Risultato(res, lastCount)));
%>

