<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true"%>

<html>
<head>
<title>Start Web Application</title>
<script src="<%= request.getContextPath() %>/scripts/home.js" type="text/javascript"></script>
</head>
<body>
      <fieldset style="float: left">
            <legend>contenuto</legend>
            <input type="number" id="numC" />
            <textarea readonly id="output"></textarea>
      </fieldset>  
      
      <fieldset style="float: right">
            <legend>admin login</legend>
            <form method="POST" action="<%= request.getContextPath() %>/pages/admin.jsp">
                  <label for="username">Username:</label> <input type="text" name="username" id="username"> <br>
                  <label for="password">Password:</label> <input type="password" name="password" id="password"> <br>
                  <input type="submit" value="Login">
            </form>
      </fieldset>  
</body>
</html>

