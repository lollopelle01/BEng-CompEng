<%@page import="java.io.File"%>
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
    <script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {
    	for (let elem of document.getElementsByClassName("stop")) {
    		elem.addEventListener("click", function (e) {
    			let id = e.target.id
    			
    			let xhr = new XMLHttpRequest();
    			xhr.open("POST", "<%= request.getContextPath() %>/adminServlet")
    			xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")

    			xhr.send(`i=\${id}`)

    		})
    	}
    })
    </script>
</head>
<body>
<%
if (success) {
	String dir_path = getServletContext().getRealPath("CanzoniNatalizie");
	File[] lista_file = new File(dir_path).listFiles();
%>
<ul>
<%
	for (int i = 0; i < lista_file.length; i++) {
%>
	<ol><button class="stop" id="<%= i %>"><%= lista_file[i].getName() %></button></ol>
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
