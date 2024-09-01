<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="./scripts/utils.js"></script>
    <script src="./scripts/jquery-1.12.3.min.js"></script>
    <script src="./scripts/home.js" defer></script>
    <title>Document</title>
</head>
<body>
<%
		if(session.getAttribute("logged") == null || !(boolean)session.getAttribute("logged")) {
			response.sendRedirect("./login.jsp");
		}
%>
    <h1>TELEFONO SENZA FILI</h1>
    <textarea name="text" id="text" cols="30" rows="10"></textarea>
    <div id="ajax_message"></div>
    
    <style>
    	h1{
    		color: red;
    		font-size: x-large;
    		text-align: center;
    	}
    	textarea{
    		backgorund-color: aquamarine;
    		color: black;
    		height: 40%;
    		width: 100%;
    		top: 150px;
    	}
    	body{
    		background-color: black;
    	}
    </style>
</body>
</html>