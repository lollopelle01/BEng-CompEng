function receiveMessage(uri) {

	var xhr = myGetXmlHttpRequest();

	if ( xhr ) {

		xhr.onreadystatechange = function() { callbackMessage(xhr); };

		// impostazione richiesta asincrona in GET del file specificato
		try {
			xhr.open("get", uri, true);
		}
		catch(e) {
			// Exceptions are raised when trying to access cross-domain URIs 
			alert(e);
		}

		// rimozione dell'header "connection" come "keep alive"
		xhr.setRequestHeader("connection", "close");

		// invio richiesta
		xhr.send(null);
	}

}

function callbackMessage( theXhr ) {

	// verifica dello stato
	if ( theXhr.readyState === 2 ) {
    	// non faccio niente
    	// theElement.innerHTML = "Richiesta inviata...";
	}// if 2
	else if ( theXhr.readyState === 3 ) {
    	// non faccio niente
		// theElement.innerHTML = "Ricezione della risposta...";
	}// if 3
	else if ( theXhr.readyState === 4 ) {
		// verifica della risposta da parte del server
	        if ( theXhr.status === 200 ) {
	        	// operazione avvenuta con successo	
		        if ( theXhr.responseText ) {
		        	var res = parsificaJSON(theXhr.responseText);
		        	// usarlo
		        	var target = document.createElement("div");
		        	target.setAttribute("class", "floating-message");
		        	target.innerHTML = res;
		        	target.addEventListener("click", function() {this.remove()});
		        	document.body.appendChild(target);
				}
				else {
			    	// non faccio niente
				}
	        }
	        else {
	        	// errore di caricamento
	        	// non faccio niente nemmeno qui
	        }
	}// if 4
} 
