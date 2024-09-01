
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <script src="./scripts/jquery-1.12.3.min.js"></script>
    <link rel="stylesheet" href="./styles/login.css">
    <script src="./scripts/registration.js" defer></script>
    </head>
    <body>
    <%
    		String error = "";
    		if(session.getAttribute("logged") != null) {
    			error = (String) session.getAttribute("logged");
    		}
	%>
	<header>
            <h1>REGISTRAZIONE</h1>
        </header>
        <main>
            <form action="registration" method="post">
                <label>
                    Username <br>
                    <input type="text" name="username" id="username"> <br>
                </label>
                <label>
                    Password <br>
                    <input type="password" name="password" id="password"> <br>
                </label>
                <p id="show-pwd" class="show-pwd">Mostra password</p> <br>
                <label for="groups">Gruppo di acquisto</label> <br>
               
                <div class="message"><%= error %></div>
                <button type="submit" id="submit-btn">Registrati</button>
            </form>
            <div class="login">Hai già un account? <a href="login.jsp">Loggati</a></div>
        </main>
        
    </body>
</html>