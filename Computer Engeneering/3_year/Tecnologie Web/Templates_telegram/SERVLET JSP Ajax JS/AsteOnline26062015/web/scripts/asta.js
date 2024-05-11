
function parsificaJSON( jsonText ) {
	
	var something = JSON.parse(jsonText);
	var risultato = something.nome + ", offerta attuale: " + something.bestBid + ", offerente: " + something.nomeUtente + " " + something.cognomeUtente;
	var field = myGetElementById("offerta");
	field.setAttribute("min", something.bestBid);
	field.setAttribute("value", something.bestBid);
	return risultato;

}// parsificaJSON()


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
		        	var linea = parsificaJSON(theXhr.responseText);
		        	
		        	resDiv.innerHTML = "<p>"+linea+"</p>";
						
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

function riceviAsta(resDiv) {
	eseguiRichiesta("./jsonastaServlet", resDiv);
}

function submitOffer(form, resDiv) {
	if (positiveFloatError(form.offerta))
			return false;
	var offerta = new Object();
	offerta.javaClass = "beans.OffertaAjax";
	offerta.offerta = form.offerta.value;
	var string = JSON.stringify(offerta);
	eseguiRichiesta("./jsonastaServlet?offerta="+string, resDiv);
	return false;
}

//Controlla che il numero sia float positivo
function positiveFloatError(myField) {
	var error = numberError(myField);
	if (!error) {
		var number = parseFloat(myField.value.trim());
		if (number < 0) {
			alert("Please enter a positive value in the '" + myField.name
					+ "' field.");
			myField.focus();
			error = true;
		}
	}
	return error;
}

//Controlla che il campo sia un numero
function numberError(myField) {
	var result = false;
	if (myField.value.trim() == "" || isNaN(myField.value.trim())) {
		alert("Please enter a numeric value for the '" + myField.name
				+ "' field.");
		myField.focus();
		result = true;
	}
	return result;
}