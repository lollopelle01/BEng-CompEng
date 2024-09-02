
function parsificaJSONArray( jsonText ) {
	
	var something = JSON.parse(jsonText);
	var risultato = "";
	for (var i = 0; i < something.length; i++) {
		if (something[i] != null)
			risultato += "<li>" + something[i] + "</li>";
	}
	risultato += "<br/><p>Numero righe = "+something.length+"</p>";
	return risultato;

}// parsificaJSON()


/*
 * Funzione di callback
 */
function eseguiCallback( theXhr, targa, resDiv ) {

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
		        	var linee = parsificaJSONArray(theXhr.responseText);
		        	
		        	resDiv.innerHTML = "<h3>Trovate le seguenti righe:</h3><br/><ul>"+linee+"</ul>";
						
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
}// caricaFeedIframe()


/*
 * Usa tecniche AJAX attraverso la XmlHttpRequest fornita in theXhr
 */
function eseguiAJAX(uri, targa, resDiv, xhr){ 
    
	// impostazione controllo e stato della richiesta
	xhr.onreadystatechange = function() { eseguiCallback(xhr, targa, resDiv); };

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

} // eseguiProdottoAJAX()

function eseguiAJAXPost(uri, body, target, xhr) {

	xhr.onreadystatechange = function() {
		callback(xhr, target);
	};

	// impostazione richiesta asincrona in POST del file specificato
	try {
		xhr.open("post", uri, true);
	} catch (e) {
		// Exceptions are raised when trying to access cross-domain URIs
		alert(e);
	}

	// rimozione dell'header "connection" come "keep alive"
	xhr.setRequestHeader("connection", "close");
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	// invio richiesta (es: body="fname=Henry&lname=Ford")
	xhr.send(body);
}

function eseguiRichiesta(uri, stringa, resDiv) {

	// assegnazione oggetto XMLHttpRequest
	var xhr = myGetXmlHttpRequest();

	if ( xhr ) 
		eseguiAJAX(uri, stringa, resDiv, xhr);
	else 
		eseguiIframe(uri,resDiv);

}// eseguiProdotto()