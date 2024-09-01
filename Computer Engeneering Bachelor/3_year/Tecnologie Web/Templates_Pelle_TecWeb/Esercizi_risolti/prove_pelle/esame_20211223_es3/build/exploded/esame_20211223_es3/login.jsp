<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" />
    <link rel="stylesheet" href="./styles/style.css">
    <script src="./scripts/jquery-1.12.3.min.js"></script>
    <script src="./scripts/login_registrazione.js" defer></script>
    <title>Login</title>
</head>
<body style="background-color: white">

    <main>
        <h1>LOGIN</h1>
        <!-- MESSAGGIO x LOGIN -->
        <div id="login_msg"> 
            <% if(session.getAttribute("login_msg")!=null){%>
                <%= session.getAttribute("login_msg")%>
            <%}%>
        </div>

        <!-- LOGIN -->
        <form action="loginServlet" method="post">
            <!-- VALORI DA INSERIRE -->
            <label> Username <br>
                <input type="text" name="username" id="username"> <br>
            </label>
            <label> Password <br>
                <input type="password" name="password" id="password"> <br>
            </label>
            <!-- CONFERMA -->
           	<p id="show-pwd" class="show-pwd">Mostra password</p>
            <button type="submit" disabled id="submit-btn">Login</button>
        </form>


        <br/><br/><br/><br/>


        <h1>REGISTRAZIONE</h1>
        <!-- MESSAGGIO x REGISTRAZIONE -->
        <div id="registration_msg">
            <% if(session.getAttribute("registration_msg")!=null){%>
                <%= session.getAttribute("registration_msg")%>
            <%}%>
        </div>

        <!-- REGISTRAZIONE -->
        <form action="registrationServlet" method="post">
            <!-- VALORI DA INSERIRE -->
            <label> Username <br>
                <input type="text" name="username" id="username"> <br>
            </label>
            <label> Password <br>
                <input type="password" name="password" id="password"> <br>
            </label>
            <!-- CONFERMA -->
           	<p id="show-pwd" class="show-pwd">Mostra password</p>
            <button type="submit" disabled id="submit-btn">Registration</button>
        </form>



    </main>
</body>
<style>
	h1{
		color: red;
		font-size: x-larger;}
</style>
</html>