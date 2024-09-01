<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./styles/login.css">
    <script src="./scripts/jquery-1.12.3.min.js"></script>
    <script src="./scripts/login.js" defer></script>
    <title>Login</title>
</head>
<body>

    <%
		String message = "";
        if(session.getAttribute("logged") != null && ((String) session.getAttribute("logged")).equals("Logged.")) {
            response.sendRedirect("./home.jsp");
        } else if(session.getAttribute("logged") != null){
        	message = (String) session.getAttribute("logged");
        }
    %>
    <div class="header">
        <h1>LOGIN</h1>
    </div>
    <main>
        <form action="login" method="post">
            <label>
                Username <br>
                <input type="text" name="username" id="username"> <br>
            </label>
            <label>
                Password <br>
                <input type="password" name="password" id="password"> <br>
            </label>
            <p id="show-pwd" class="show-pwd">Mostra password</p>
            <button type="submit" id="submit-btn">Login</button>
            <div class="message"><%= message %></div>
        </form>

        <div class="registration">Non hai un account? <a href="registration.jsp">Registrati</a></div><!-- senza il ./ -->
    </main>
</body>
</html>