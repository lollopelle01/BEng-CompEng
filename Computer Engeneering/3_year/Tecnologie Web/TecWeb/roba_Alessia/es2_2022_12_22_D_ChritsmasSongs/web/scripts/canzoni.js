function songs(jsonText) {
   
   var risp = JSON.parse(jsonText);
   console.log(risp);
   
   var res = myGetElementById("res");
   res.innerHTML 	= '<p>Canzoni scaricate dal server:</p>'
   						
   var songs = risp.downloadedSongs;
   for(var i = 0; i < songs.length; i++) {
	   res.innerHTML	+= 	'<table>'
		   				+		'<tr>'
		    			+			'<td width="300px">' + songs[i].nome  + '</td>'
		    			+			'<td width="100px">' + songs[i].min  + '</td>'
		    			+			'<td width="100px">' + songs[i].formato  + '</td>'
		   				+			'<td width="100px">' + songs[i].binario  + '</td>'
		   				+ 		'</tr>'
	    				+	'</table>';
   }
}

function callback( theXhr ) {

	if ( theXhr.readyState === 2 ) {
	    	//theElement.innerHTML = "Richiesta inviata...";
	}// if 2
	else if ( theXhr.readyState === 3 ) {
    	//	theElement.innerHTML = "Ricezione della risposta...";
	}// if 3
	else if ( theXhr.readyState === 4 ) {
		// verifica della risposta da parte del server
		if ( theXhr.status === 200 ) {
			// operazione avvenuta con successo
			songs(theXhr.responseText);   //se la servlet ha eseguito con successo il metodo doGet, allora "traduco" la sua risposta
			//location.reload();
		}// if 200

		 else {
	        	// errore di caricamento
			 alert("Impossibile effettuare l'operazione richiesta.");
                //theElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />";
                //theElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
	        }// else (if ! 200)
	}// if 4
} // callback();


function requestSongsIframe() {
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio")
}

// -------------------------------------------------------------------------
//  --------------- Funzione di invio doPost alla servlet ------------------
// -------------------------------------------------------------------------
function requestSongsAJAX(theXhr, numero) {
    
    console.log(numero);
	theXhr.onreadystatechange = function() { callback(theXhr); };   //invio
	
	// impostazione richiesta asincrona in GET del file specificato
	try {
        //devi modificare l'url (il secondo parametro, in base ai valori)
		theXhr.open("post", "servletS1", true);
	}
	catch(e) {
		// Exceptions are raised when trying to access cross-domain URIs 
		alert(e);
	}

	var argument = "numero="+numero;
	
	// invio richiesta
	theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	theXhr.send(argument);
} // caricaFeedAJAX()



// -------------------------------------------------------------------------
function myfunction(){
	
	var input = myGetElementById("numero");
	var numero = input.value;
	
	if(numero != 3 && numero != 4) {
		alert("Inserisci un numero pari a 3 o a 4!");
		input.value = "";
		return;
	}
	
	input.disabled = true;
	
	var	xhr = myGetXmlHttpRequest();
	if (xhr) 
		requestSongsAJAX(xhr, numero); 
	else 
		requestSongsIframe(); 
		
}