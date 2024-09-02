<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>


<!-- codice html restituito al client -->
<!DOCTYPE HTML PUBLIC>
<html>

<head>
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="-1" />
    <title>Login</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script><!-- libreria jquery -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/default.css" type="text/css" />
</head>
<body>

<%
String res="";
if((res=(String)request.getAttribute("result"))==null){
	res="";
}
%>

<p><%=res%></p>

 <h1>Log in</h1>
<form action="<%=request.getContextPath()%>/loginServlet" method="post">
  
  <div>
           <p>Log in using your username and password!</p>
            <label for="nome">Your username:</label><br>
            <input type="text" id="username" name="username" >

        </div>

        <div>
            <label for="password">Your password:</label><br>
            <input type="password" id="password" name="password">
        </div>
  
    <br><input type="submit" name="login" value="LOGIN">
	<div>
<a href='<%=request.getContextPath()%>/home.html?'>Vai alla pagina Principale</a>
<br>
</div>
	
  </form>      
</body>
</html>