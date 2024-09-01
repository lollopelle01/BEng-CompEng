<?xml version='1.0' encoding='ISO-8859-1'?>

<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="java.util.*"%>

<% response.setHeader("Content-Type","application/xml"); %>

<!DOCTYPE HTML PUBLIC >
<html>
<head>
    <title>Contatore parole iniziano con S</title>
</head>

<body>

	<% 
		String text = (String)request.getAttribute("text");
		StringTokenizer stk = new StringTokenizer(text);
		String word;
		List<String> elencoParole = new ArrayList<>();
		int count = 0;
		
		while(stk.hasMoreTokens()) {
			word = stk.nextToken();
			if(word.charAt(0) == 'S') {
				elencoParole.add(word);
				count++;
			}
		}
		
		String risposta = String.valueOf(count) + elencoParole;
		response.getWriter().print(risposta);
		
		//fondamentale per prendere poi le variabili attraverso tag
		for (String str : elencoParole ) { %>	
			<parola><![CDATA[ <%=str%> ]]></parola>
	<% } %>

		<contatore><![CDATA[ <%=count%> ]]></contatore>
</body>
</html>