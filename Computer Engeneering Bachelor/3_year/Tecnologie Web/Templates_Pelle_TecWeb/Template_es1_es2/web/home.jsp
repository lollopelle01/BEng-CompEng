<%@page import="beans.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="./scripts/utils.js"></script>
    <script src="./scripts/jquery-1.12.3.min.js"></script>
    <!-- RICORDATI DI IMPORTARE TITTI I .JS-->
    <script src="./scripts/home.js" defer></script>
    <title>HOME</title>
</head>
<body>

    <%
        // Se non è loggato lo rimando al login
		if(session.getAttribute("logged") == null || !(boolean)session.getAttribute("logged")) {
			response.sendRedirect("./login.jsp");
		}
    %>

    <h1>ACQUISTO ARTICOLI</h1>
    <textarea name="text" id="text" cols="30" rows="10"></textarea>
    <div id="ajax_message"></div>
    
</body>
</html>