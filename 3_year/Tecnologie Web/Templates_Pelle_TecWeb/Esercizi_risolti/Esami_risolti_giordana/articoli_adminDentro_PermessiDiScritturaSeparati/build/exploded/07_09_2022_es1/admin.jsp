<%@page import="java.util.Map"%>
<%@page import="beans.Article"%>
<%@ page session="true"%>
<html>
   <head>
		
        <title>Homepage</title>
		<link type="text/css" href="./styles/home.css" rel="stylesheet"></link>
        <script src="./scripts/jquery-1.12.3.min.js"></script>
        <script src="./scripts/admin.js" defer></script>
   </head>
   <body>
   	<%
   		if(!request.getParameter("username").equals("admin") || !request.getParameter("password").equals("admin")) {
   			response.sendRedirect("./home.jsp");
   		}
   		Map<String, Article> articles = (Map<String, Article>) getServletContext().getAttribute("articles");
   	%>
        <header>
            <h1>ADMIN</h1>
        </header>

        <main>
            <table>
                <tr>
                    <th>ARTICOLI</th>
                    <th>LISTA UTENTI</th>
                    <th>UTENTE CON PERMESSI DI SCRITTURA</th>
                    <th>RIMUOVI</th>
                </tr>
                <%for(String article : articles.keySet()) {%>
                	<tr>
                		<td><%=article %></td>
                		<td>
	                		<ul>
	                			<%for(String user : articles.get(article).getUsers()) {%>
	                				<li><%=user %></li>
	                			<%} %>
	                		</ul>
                		</td>
                		<td>
                			<%=articles.get(article).getCurrentSession() %>
                		</td>
                		<td>
                			<button id="<%= article%>">Rimuovi</button>
                		</td>
                	</tr>
				<%} %>
            </table>
        </main>

   </body>
</html>