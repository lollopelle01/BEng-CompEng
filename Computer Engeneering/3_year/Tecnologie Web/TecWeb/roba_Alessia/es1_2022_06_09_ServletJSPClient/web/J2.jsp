<!-- STRUTTURA PAGINA HTML -->
<%@ page session="true"%>

<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->
<%@ page import="java.util.*"%>
<%@ page import="Beans.Json"%>
<%@ page import="com.google.gson.Gson"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>
	
		<script type="text/javascript" src="scripts/controllo.js"></script>
        <script type="text/javascript" src="scripts/utils.js"></script>
	
		<title> J2 </title>
	
		</head>
    <body>
    	<div id="risultato"></div>
        <div style="display: none">
        	<%
	       		
	       		Gson g = new Gson();
        		
        		Json rispS1 = (Json) request.getAttribute("json");
        		
        		String text = rispS1.getTesto();
	       		
	       		int count = -1;
	       		
	       		if(text != null) {
	       			count = 0;
	       			for(int i = 0; i < text.length(); i++) {
		      			String car = text.charAt(i) + "";
		      			if(!(car == " ") && car.equals(car.toUpperCase())) {
		      				count++;
		      			}
		      		}
	       		}
	       		
	       		rispS1.setCount(count);
	       		
		   		if(count != -1) {
		   	%>			
		   		<input type="text" id="rispDef" value='<%=g.toJson(rispS1)%>'/>
		   		<script>this.respondJson();</script>
		   	<%	
		   		}
		   	%>
       		
        </div>
	</body>
</html>


