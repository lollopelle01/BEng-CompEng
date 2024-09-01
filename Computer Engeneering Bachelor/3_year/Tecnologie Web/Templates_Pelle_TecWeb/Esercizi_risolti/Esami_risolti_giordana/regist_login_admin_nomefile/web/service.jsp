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
			String target = (String) session.getAttribute("target");
			System.out.println("ServiceJSP: arrivato testo: " + text);
			System.out.println("ServiceJSP: arrivato target: " + target);
			target = target.toUpperCase();
			Result result = new Result();
			
			if(text.isEmpty() || text.isEmpty() || target.isBlank() || target.isEmpty()){
				result.setMessage("Error on JSP");
			}
			else{
				int lastIndex = 0;
				int count = 0;

				while (lastIndex != -1) {

				    lastIndex = text.indexOf(target, lastIndex);

				    if (lastIndex != -1) {
				        count++;
				        lastIndex += target.length();
				    }
				}
				System.out.println("ServiceJSP: contate occorrenze: " + count);
				
				result.setText(text);
				result.setMessage("Success.");
				result.setCounter(count);
			}
			
			
			response.getWriter().write(gson.toJson(result));
			response.getWriter().close();
		%>
	</body>
</html>