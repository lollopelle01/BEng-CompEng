<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./styles/home.css">
        <script src="./scripts/jquery-1.12.3.min.js"></script>
        <script src="./scripts/home.js" defer></script>
        <title>Homepage</title>
    </head>
    <body>
    <%
		if(session.getAttribute("logged") == null || !((String) session.getAttribute("logged")).equals("Logged.")) {
			response.sendRedirect("./login.jsp");
		}
    
    	char grid[][] = (char[][]) getServletContext().getAttribute("grid");
	%>
        <header><h1>HOMEPAGE</h1></header>
        <main>
            <div>
                <table id="grid">
                	<%for(int i=0; i<10; i++){ %>
                	<tr>
                		<%for(int j=0; j<10; j++){ %>
                			<td><input type="text" id="<%=i%>_<%=j%>" value="<%= grid[i][j]%>"></td>
                		<%} %>
                	</tr>
                	<%} %>
                </table>
            </div>
            
            <div id="ajax"></div>
        </main>
    </body>
</html>