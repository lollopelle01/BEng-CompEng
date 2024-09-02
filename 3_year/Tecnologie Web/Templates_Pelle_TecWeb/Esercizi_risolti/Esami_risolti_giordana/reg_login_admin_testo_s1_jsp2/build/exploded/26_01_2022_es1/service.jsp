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
			Random random = new Random(System.currentTimeMillis());
			String chars = "abcdefghijklmnopqrstuvwxyz";
			chars += chars.toUpperCase();
			String c = chars.charAt(random.nextInt(chars.length())) + "";
			text = text.replaceAll(c, "");
			int counter = 0;
			for(int i = 0; i < text.length(); i++) {
				if((text.charAt(i) + "").equals((text.charAt(i) + "").toUpperCase())) {
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