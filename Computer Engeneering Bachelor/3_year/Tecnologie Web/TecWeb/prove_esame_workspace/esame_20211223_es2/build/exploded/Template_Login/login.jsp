<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
    <link rel="stylesheet" href="./styles/style.css">
    <script src="./scripts/jquery-1.12.3.min.js"></script>
    <script src="./scripts/script.js" defer></script>
    <title>Login</title>
</head>
<body>

    <main>
        <h1>LOGIN</h1>
        <%if(session.getAttribute("registrato") != null && (boolean)session.getAttribute("registrato")==false) {%>
				Username o Password errati
		<%}%>
        <form action="loginServlet" method="post">
            <label>
                Username <br>
                <input type="text" name="username" id="username"> <br>
            </label>
            <label>
                Password <br>
                <input type="password" name="password" id="password"> <br>
            </label>
            
           	<p id="show-pwd" class="show-pwd">Mostra password</p>
           	
            <button type="submit" disabled id="submit-btn">Login</button>
        </form>

        <div class="registration">Non hai un account? <a href="Registration.jsp">Registrati</a></div> <!-- link senza ./ -->
    </main>
</body>
<style>
	h1{
		color: red;
		font-size: x-larger;
	}
</style>
</html>