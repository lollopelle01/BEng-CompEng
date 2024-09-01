<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ page import="com.google.gson.*"%>
<html>
	<head>
		
	</head>
	<body>
	<% Gson g = new Gson(); %>
		<div>
			 <form action="ConcurrentDownloadServlet" method="get"> 
				Quante pagine vuoi scaricare?
				<input type="text" id="number" name="value" size="6"/><br>
				<input type="button" value="invia" onclick="myFunction();"> 
			</form>  
			<%
			 
			if(session.getAttribute("result")!=null)
			{
				int result =(Integer)session.getAttribute("result");
			%>
				<input type="text" id="result" size="6" value="<%=result %>" readonly>
			<%
			}
			%>
			
		</div>
	</body>
</html>