function sendJSON(form, resDiv) {
	if (!isAllCompiled(form))
		return false;
	var libro = new Object();
	libro.javaClass = "beans.Libro";
	libro.autore = form.autore.value;
	libro.titolo = form.titolo.value;
	libro.editore = form.editore.value;
	libro.isbn = form.isbn.value;
	
	eseguiRichiesta("./salvaNuovoLibroServlet?libro="+JSON.stringify(libro), resDiv);
	
	return true;
}

//Controllo che tutti i campi abbiano qualcosa
function isAllCompiled(form) {
	for (var i = 0; i < form.elements.length; i++) {
		if (isBlank(form.elements[i]))
			return false;
	}
	return true;
}

//Campo non vuoto
function isBlank(myField) {
    var result = false;
    if (myField.value!=null && myField.value.trim() == "") {
        alert("Please enter a value for the '" + myField.name + "' field.");
        myField.focus();
        result = true;
    }
    return result;
}



/*
 * Funzione di callback
 */
function eseguiCallback( theXhr, resDiv ) {

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
		        	
		        	resDiv.innerHTML = "<h3>"+theXhr.responseText+"</ul>";
						
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
} // prodottoCallback();

/*
 * Imposta il contenuto disponibile presso theUri
 * come src di un iframe all'interno dell'elemento theHolder del DOM
 * Non usa AJAX; per browser legacy
 */
function eseguiIframe(theUri,theHolder) {
	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	theHolder.innerHTML = '<iframe src="' + theUri + '" width="50%" height="50px">Il tuo browser non supporta gli iframe</iframe>';
	// non riesco tuttavia a intervenire per parsificarlo! e' il browser che renderizza l'src dell'iframe!
}


/*
 * Usa tecniche AJAX attraverso la XmlHttpRequest fornita in theXhr
 */
function eseguiAJAX(uri, resDiv, xhr){ 
    
	// impostazione controllo e stato della richiesta
	xhr.onreadystatechange = function() { eseguiCallback(xhr, resDiv); };

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

function eseguiRichiesta(uri, resDiv) {

	// assegnazione oggetto XMLHttpRequest
	var xhr = myGetXmlHttpRequest();

	if ( xhr ) 
		eseguiAJAX(uri, resDiv, xhr);
	else 
		eseguiIframe(uri,resDiv);

}