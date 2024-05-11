<%@page import="javax.websocket.Session"%>
<%@page import="utils.SharedWSSessionManager"%>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
String username = request.getParameter("username");
String password = request.getParameter("password");

Boolean success = false;

if ((username != null && username.equals("admin")) && (password != null && password.equals("admin"))) {
    success = true;
}

%>

<html>
<head>
    <title>Admin</title>
</head>
<body>
<%
if (success) {
%>
<h2>Admin</h2>
<ul>

<%
	SharedWSSessionManager swssm = SharedWSSessionManager.getInstance();
	for (Session s : swssm.getSessions()) {
%>
	<li><%= s.getId() %> : <%= swssm.getDates().get(s) %></li>
<%
	}
%>

</ul>

<%
} else {
%>
<h2>Errore</h2>
<%
}
%>
</body>
</html>
