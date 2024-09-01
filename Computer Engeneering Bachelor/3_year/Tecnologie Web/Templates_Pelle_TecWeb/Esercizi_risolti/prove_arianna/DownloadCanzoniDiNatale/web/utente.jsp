<%@ page session="true"%>
<html>
   <head>
		
      <title>Canzoni di natale</title>     
	  <link type="text/css" href="styles/default.css" rel="stylesheet"></link>
	  
	  <!-- script necessari: -->
   		<script type="text/javascript" src="scripts/utils.js"></script>
		<script type="text/javascript" src="scripts/download.js"></script>
		
   </head>
<body>

	<h2>Download di canzoni natalizie</h2>
	
	<p>Inserisci il numero di download concorrenti desiderati</p><br>
	
	<label for="num" >Number:</label><br>
	<input type="text" id="num" name="num" size="2" onKeyup="checkNum();" >

	<p>Lista dei file scaricati</p><br/>
	<textarea id="fileresult" rows="1000" cols="1000"></textarea>
	
</body>
</html>

