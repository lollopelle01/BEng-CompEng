<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page session="true"%>

<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Admin Page</title>
	
	<%
		File directory = new File("CanzoniNatalizie");
		File[] files = directory.listFiles();
	%>
	</head>
<body>
	
	<h5>Ecco la lista delle canzoni nella cartella <strong>CanzoniNatalizie</strong></h5>
	
	<p>Seleziona una canzone per bloccare tutti i suoi download</p>
	
	<form action="AdminServlet" method="post">
		<select id="blocca" name="blocca" >
			<%  for(int i=0; i<files.length; i++) {  %>
					<option><%=files[i].getName()%></option>
			<% 	} %>
		</select> 
	</form>
        
</body>
</html>