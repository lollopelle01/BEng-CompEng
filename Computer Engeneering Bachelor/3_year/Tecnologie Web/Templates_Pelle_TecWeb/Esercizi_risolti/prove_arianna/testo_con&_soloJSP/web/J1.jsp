<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="java.util.*"%>

<!DOCTYPE HTML PUBLIC >
<html>
<head>
    <title>Change Minuscole</title>
</head>

<body>
	
	<% 
		String text = (String)request.getAttribute("text");
		text = text.toUpperCase();
	
		request.setAttribute("text", text);
	%>
		
	<jsp:forward page="J2.jsp" />


</body>
</html>
