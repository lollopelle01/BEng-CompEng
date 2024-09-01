<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="java.util.*"%>

<!-- codice html restituito al client -->
<!DOCTYPE HTML PUBLIC >
<html>
<head>
    <title>Parole finite con A</title>
</head>

<body>

	<% 
		String text = (String)request.getAttribute("text");
		StringTokenizer stk = new StringTokenizer(text);
		String textChanged = "";
		String word;
		
		while(stk.hasMoreTokens()) {
			word = stk.nextToken();
			
			if(word.charAt(word.length()-1) != 'A')
				textChanged += word;
		}
	
		request.setAttribute("text", textChanged);
	%>
		
	<jsp:forward page="J3.jsp" />

</body>
</html>
