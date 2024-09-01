<%@ page session="true"%>
<%@ page errorPage="../errors/failure.jsp"%>

<%@ page import="java.util.*"%>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="beans.Risultato" %>

<% response.setHeader("Content-Type","application/json"); %>

<%
	String text = (String)request.getAttribute("text");
	String textChanged = "";
	String alphabet = "abcdefghijklmnopqrstuvwxtz";
	
	Random r = new Random();
	int i = r.nextInt(alphabet.length()+1);
	int countMasc = 0;
	
	char c = alphabet.charAt(i), cc;
	
	for(int j=0; j<text.length(); j++) {
		cc = text.charAt(j);
		if(cc >= 'A' && cc <= 'Z')
			countMasc++;
		if(cc != c)
			textChanged += cc;
	}

	Gson g = new Gson();
	response.getWriter().println(g.toJson(new Risultato(textChanged, countMasc)));
%>

