/*function myFunction()
{
	
	var text = document.getElementById("myText");
	var fileName = document.getElementById("fileName");
	
	
	
	if(text.value.length > 0 && fileName.value.length > 0)
		{
		
		// assegnazione oggetto XMLHttpRequest
			var	xhr = myGetXmlHttpRequest();
			var xhr2 = myGetXmlHttpRequest();
			if ( xhr ) 
				countingAJAX(xhr, xhr2, fileName.value, text.value); 
			else 
				countingIframe(); 
		}else
			{
				alert("the inputs text cannot be empty!");		
			}
	
		

}*/



function counter( jsonText ) {
   
	// variabili di funzione
	
	
		var conteggio = JSON.parse(jsonText);
		// Ottengo la lista delle sezioni del docuemento
		
		if(conteggio.value != -1)
			{
			var myRes = document.getElementById("result");
			
			myRes.value = conteggio.value;
			}
		
		

		
	
	
     

}// parsificaJSON()





/*
 * Funzione di callback
 */
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
			counter(theXhr.responseText);
			
			//location.reload();
		}// if 200

		 else {
	        	// errore di caricamento
			 alert("Impossibile effettuare l'operazione richiesta.");
//	        	theElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />";
//	        	theElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
	        }// else (if ! 200)
	}// if 4

} // callback();








function countingIframe() {
	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio")
	
	// non riesco tuttavia a intervenire per parsificarlo! è il browser che renderizza il src del iframe!
}// caricaFeedIframe()



function countingAJAX( theXhr, theXhr2, fileName, text) {
    
	
	var file = fileName;
	var primaMeta = text.substr(0, (text.length/2));
	var secondaMeta = text.substr(primaMeta.length, (text.length - primaMeta.length));
	// impostazione controllo e stato della richiesta
	theXhr.onreadystatechange = function() { callback(theXhr); };
	theXhr2.onreadystatechange = function() { callback(theXhr); };
	// impostazione richiesta asincrona in GET
	// del file specificato
	try {
		theXhr.open("post", "CounterDispatcher", true);
		theXhr2.open("post", "CounterDispatcher", true);
	}
	catch(e) {
		// Exceptions are raised when trying to access cross-domain URIs 
		alert(e);
	}

	// rimozione dell'header "connection" come "keep alive"
	theXhr.setRequestHeader("connection", "close");
	theXhr2.setRequestHeader("connection", "close");
	var argument = "testo="+primaMeta+"&file="+file;
	// invio richiesta
	//
	theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	theXhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	theXhr.send(argument);
	argument = "testo="+secondaMeta+"&file="+file;
	// invio richiesta
	theXhr2.send(argument);

} // caricaFeedAJAX()

function myFunction()
{
	
	var text = document.getElementById("myText");
	var fileName = document.getElementById("fileName");
	
	
	
	if(text.value.length > 0 && fileName.value.length > 0)
		{
		
		// assegnazione oggetto XMLHttpRequest
			var	xhr = myGetXmlHttpRequest();
			var xhr2 = myGetXmlHttpRequest();
			if ( xhr ) 
				countingAJAX(xhr, xhr2, fileName.value, text.value); 
			else 
				countingIframe(); 
		}else
			{
				alert("the inputs text cannot be empty!");		
			}
	
		

}