<%@ page import="java.util.*" %>
<%@ page import="beans.Tavolo" %>
<%@ page import="beans.Drink" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Refresh" content= "2"/>
<link rel="stylesheet" href="./styles/home.css">
<title>Administrator</title>
</head>
<body>
	<%
		if(session.getAttribute("logged") == null || !((String) session.getAttribute("logged")).equals("Admin.")) {
			response.sendRedirect("./login.jsp");
		}
		
	%>
	
	        
</body>
</html>