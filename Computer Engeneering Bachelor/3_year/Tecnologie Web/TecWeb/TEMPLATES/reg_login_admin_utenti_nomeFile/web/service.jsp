<%@page import="com.google.gson.Gson"%>
<%@page import="beans.Result"%>
<%@page import="java.util.Random"%>
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
			Gson gson = (Gson) getServletContext().getAttribute("gson");
			String text = (String) session.getAttribute("text");
			System.out.println("ServiceJSP: arrivato testo: " + text);
			
			String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			
			int counter=0;
			for(int i=0; i<text.length(); i++){
				if(chars.contains(text.charAt(i) + "")){
					counter++;
				}
			}
			
			Result result = new Result();
			result.setText(text);
			result.setMessage("Success.");
			result.setCounter(counter);
			
			System.out.println(text);
			
			response.getWriter().write(gson.toJson(result));
			response.getWriter().close();
		%>
	</body>
</html>