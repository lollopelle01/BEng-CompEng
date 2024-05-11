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
    <title>Registration</title>
</head>
<body>
<%
		if(session.getAttribute("signed") != null && (boolean)session.getAttribute("signed")) {
			response.sendRedirect("./login.jsp");
		}
	%>
    <main>
        <h1>REGISTRATION</h1>
        <form action="registrazione" method="post">
            <label>
                Email <br>
                <input type="text" name="email" id="email"> <br>
            </label>
            <label>
                Password <br>
                <input type="password" name="password" id="password"> <br>
            </label>
            
           	<p id="show-pwd" class="show-pwd">Mostra password</p>
           	
            <button type="submit" disabled id="submit-btn">Registration</button>
        </form>

        <div class="registration">Hai gia un account? <a href="login.jsp">Accedi</a></div>
    </main>
</body>
<style>
	h1{
		color: red;
		font-size: x-larger;
	}
</style>
</html>