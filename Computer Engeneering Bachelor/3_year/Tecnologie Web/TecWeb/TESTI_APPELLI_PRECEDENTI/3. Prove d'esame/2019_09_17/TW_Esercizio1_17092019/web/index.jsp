<%@ page session="true"%>
<html>
   <head>
		
      <title>Start Web Application</title>
		<link type="text/css" href="styles/default.css" rel="stylesheet"></link>
		<script type="text/javascript" src="scripts/utils.js"></script>
		<script type="text/javascript" src="scripts/hotel.js"></script>
   </head>
   <body>
	Benvenuto utente
	<div>
	
      Inserisci il codice dell'Hotel <input type="text" size="2" id="codice"><br>
      Check-In <input type="text" size="2" id="checkin"><br>
 	  Check-Out <input type="text" size="2" id="checkout" onfocusout="myFunction()"><br>
 	  <input type="hidden" id="n" value=<%=this.getServletContext().getAttribute("n") %>>
    </div>  
    <div id="ris" style="display: none;">
    	<form action="Booking" method="POST">
    		Hotel: <input type="text" size="2" id="risCod" name="risCod" readonly><br>
    		Notti: <input type="text" size="2" id="risNotti" readonly><br>
    		Prezzo: <input type="text" size="2" id="risPrezzo" readonly><br>
    		<input type="hidden" id="risCI" name="risCI" value="">
    		<input type="hidden" id="risCI" name="risCO" value="">
    		<input type="submit" value="finalizza">
    	</form>  	
    </div>
   </body>
</html>

