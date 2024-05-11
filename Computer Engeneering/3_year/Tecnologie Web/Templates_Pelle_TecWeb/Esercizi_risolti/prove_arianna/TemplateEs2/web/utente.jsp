<!-- pagina per la gestione di errori -->
<%@ page errorPage="../errors/failure.jsp"%>

<!-- accesso alla sessione -->
<%@ page session="true"%>

<!-- import di classi Java -->
<%@ page import="java.util.*"%>
<%@ page import="com.google.gson.Gson" %>

<html>
   <head>
		
      <title>Titolo della pagina</title>     
	  <link type="text/css" href="styles/default.css" rel="stylesheet"></link>
	  
	  <!-- script necessari: -->
   		<script type="text/javascript" src="scripts/utils.js"></script>
		<script type="text/javascript" src="scripts/basic.js"></script>
		
   </head>
<body>

	<div>
	Matrice B:<br>
		<input type="text" size="2" id="b0" onmouseout="checkMatrix()"> <input type="text" size="2" id="b1" onmouseout="checkMatrix()"><br>
		<input type="text" size="2" id="b2" onmouseout="checkMatrix()"> <input type="text" size="2" id="b3" onmouseout="checkMatrix()"><br>
	</div>
	
	<div>
	Matrice Risultato:<br>
		<input type="text" size="2" id="r0"> <input type="text" size="2" id="r1"><br>
		<input type="text" size="2" id="r2"> <input type="text" size="2" id="r3"><br>
		<input type="text" size="2" id="r4"> <input type="text" size="2" id="r5"><br>
		<input type="text" size="2" id="r6"> <input type="text" size="2" id="r7"><br>
	</div>
	
	
	<!-- area che deve essere riempita -->
	<textarea id="myText" name="myText" rows="7" cols="50" maxlength="2000" required></textarea><br><br>
	
	
	<!--  caso risultato readonly -->
	<div>
		<p>Il risultato del conteggio &egrave;: </p><input type="text" id="result" readonly>
	</div>
	
	
	
	<!-- bottone che compare in seguito a una funzione check -->
	<div id="secret" style=" display: none;">
		<input type="button" id="single" value="single" onclick="myFunction(this)"><input type="button" id="multi" value="multi" onclick="myFunction(this)">
	</div>
	
	<!-- altrimenti lo mostro da subito e però faccio una alert nel caso in cui i valori inseriti siano incorretti -->
	
	
	
	
	<div>
	<br>
		<input type="hidden" size="2" id="h0"> <input type="hidden" size="2" id="h3"><br>
		<input type="hidden" size="2" id="h2"> <input type="hidden" size="2" id="h5"><br>
		<input type="hidden" size="2" id="h4"> <input type="hidden" size="2" id="h6"><br>
		<input type="hidden" size="2" id="h6"> <input type="hidden" size="2" id="h7"><br>
	</div>
	
	
	<!-- messaggio di errore solo in caso qualcosa andasse storto -->
	<div>
		<input type="hidden" id="err" value="">
	</div>
	
</body>
</html>

