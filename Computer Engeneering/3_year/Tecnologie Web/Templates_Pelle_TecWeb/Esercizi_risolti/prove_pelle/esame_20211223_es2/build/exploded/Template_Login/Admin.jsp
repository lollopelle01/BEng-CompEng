<%@page import="beans.UserInfo"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Administrator</title>
	</head>
	<body>
	
	<%
		try {
				Map<String, UserInfo> userMap = (Map<String, UserInfo>) getServletContext().getAttribute("database");
				%>
				<table>
				<% 
				for(String s : userMap.keySet()) { %>
					<tr>
						<td><%= userMap.get(s).getUsername() %></td>
						<td><%= userMap.get(s).getCounter() %></td>
					</tr>		
				<%}%>
				</table>
				
				<%
		} catch(Exception e) {
			e.printStackTrace();
		}
 		
	%>
	</body>
	<script>
		setTimeout(function(){
			   window.location.reload();
			}, 5000);
	</script>
</html>