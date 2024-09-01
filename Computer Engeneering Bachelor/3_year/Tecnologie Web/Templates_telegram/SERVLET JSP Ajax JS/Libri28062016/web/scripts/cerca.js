//Campo non vuoto
function isBlank(myField) {
    var result = false;
    if (myField.value!=null && myField.value.trim() == "") {
        result = true;
    }
    return result;
}

function cerca(form, resDiv) {
	var nomi = new Array();
	nomi[1] = form.nomeAutore_1.value;
	nomi[2] = form.nomeAutore_2.value;
	nomi[3] = form.nomeAutore_3.value;
	
	resDiv.innerHTML="";
	for (var i=0; i<nomi.length; i++) {
		if (nomi[i]!= null && nomi[i].trim() != "")
			eseguiRichiesta("./dispatcherServlet?nomeautore="+nomi[i].trim(), resDiv);
	}
	
}


function parsificaJSONArray( jsonText ) {
	
	var something = JSON.parse(jsonText);
	var risultato = "";
	for (var i = 0; i < something.length; i++) {
		if (something[i] != null)
			risultato += "<li>Titolo: " + something[i].titolo +", Autore: " +something[i].autore + ", Editore: " +something[i].editore + ", ISBN: "+something[i].isbn + "</li>";
	}
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
		        	var linee = parsificaJSONArray(theXhr.responseText);
		        	
		        	resDiv.innerHTML += linee;
						
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