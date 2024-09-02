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
		if(session.getAttribute("logged") == null || (!((String) session.getAttribute("logged")).equals("Logged.") && !((String) session.getAttribute("logged")).equals("Admin."))) {
			response.sendRedirect("./login.jsp");
		}
	%>
        <header><h1>HOMEPAGE</h1></header>
        <main>
            <div id="request-div">
                <label for="fname">Inserisci il nome di un file sul server</label> <br>
                <input type="text" name="fname" id="fname"> <br>
                <label for="target">Inserisci una stringa alfabetica</label> <br>
                <input type="text" name="target" id="target">
            </div>
            
            <div id="testo"></div>
            <div id="occorrenze"></div>
            
            <div id="ajax"></div>
            <%
				if(session.getAttribute("logged") != null && ((String) session.getAttribute("logged")).equals("Admin.")) { %>
				
            	<div class="registration">Vuoi ritornare nlla tua pagina <a href="Admin.jsp">Admin?</a></div>
            <%} %>
        </main>
    </body>
</html>