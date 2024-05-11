/*
 * Funzione che genera una lista XHTML 
 * con gli item presi dai feed RSS scaricati in formato JSON
 */
function parsificaJSON( jsonText ) {
   
	var result = JSON.parse(jsonText);
	if(result != -1)
    	return result;
}

 // funzione di callback
function callback( theXhr, theElement ) {
	
	// verifica dello stato
	if ( theXhr.readyState === 2 ) {
	    	theElement.innerHTML = "Richiesta inviata...";
	}
	else if ( theXhr.readyState === 3 ) {
    		theElement.innerHTML = "Ricezione della risposta...";
	}
	else if ( theXhr.readyState === 4 ) {

		// verifica della risposta da parte del server
        if ( theXhr.status === 200 ) {
  			theElement.innerHTML = parsificaJSON(theXhr.responseText); // operazione avvenuta con successo
        } else {
        	
        	theElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />"; // errore di caricamento
        	theElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
        }
	}
}


// non usa AJAX; per browser legacy
function toUpperCaseIframe() {
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio");
}


// usa tecniche AJAX attrverso la XmlHttpRequest fornita in theXhr
function toUpperCaseAJAX(theElement, theXhr, theXhr2, corrText, corrFileName) {
    
	theXhr.onreadystatechange = function() { callback(theXhr, theElement); }; // impostazione controllo e stato della richiesta
	
	//METODO POST
	try {
		theXhr.open("post", "TextServlet", true);
	}
	catch(e) {
		alert(e);
	}

	theXhr.setRequestHeader("connection", "close"); // rimozione dell'header "connection" come "keep alive"
	
	var argument = "fileName="+corrFileName+"&text="+corrText;
	theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	theXhr.send(argument); // invio richiesta alla jsp/servlet
}


// scarica un contenuto xml dall'uri fornito e lo aggiunge al contenuto dell'elemento e del dom 
// gestisce sia AJAX che il mancato supporto ad AJAX 
function toUpperCase(e) {
	
	var 
		ok = false;
		corrText = MyGetElementById("text").value;
		corrFileName = MyGetElementById("fileName").value;
		
	if(corrText.length >= 1000 && corrText.length <= 2000 && corrFileName.length > 0)
		ok = true;
	else
		ok = false;
		
	if(ok) {
		var xhr = myGetXmlHttpRequest(); // assegnazione oggetto XMLHttpRequest
		var xhr2 = myGetXmlHttpRequest();

		if (xhr) 
			toUpperCaseAJAX(e,xhr, xhr2, corrText, corrFileName); 
		else 
			toUpperCaseIframe(); 
	} else
		alert("the inputs text cannot be empty!");	
	
}

