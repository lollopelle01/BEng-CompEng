//VARIABILE GLOBALE, MI SERVE PER CAPIRE QUANDO SONO ARRIVATO A 3 FILE SELEZIONATI
var risultato = 0;
var counterResults = 0; 
var totFiles = 0;

function parsifica( jsonText ) {
   
	    // variabili di funzione
		var resParziale = JSON.parse(jsonText).cambiati;
		risultato += resParziale;	
		counterResults++;
		if ( counterResults== 2) {
	    	var element = myGetElementById("result");
		    element.innerText = risultato;               //element.innerText = METTE COME NUOVA STRINGA , += METTE IN APPEND 
		}
}  // parsificaJSON()


/*
 * Funzione di callback
 */
function callback( theXhr ) {

	if ( theXhr.readyState === 2 ) {
	    	//theElement.innerHTML = "Richiesta inviata...";
	} // if 2
	else if ( theXhr.readyState === 3 ) {
    	//	theElement.innerHTML = "Ricezione della risposta...";
	} // if 3
	else if ( theXhr.readyState === 4 ) {
		// verifica della risposta da parte del server
		if ( theXhr.status === 200 ) {
			// operazione avvenuta con successo
			parsifica(theXhr.responseText);
			
			//location.reload();
		} // if 200

		 else {
	        	// errore di caricamento
			 alert("Impossibile effettuare l'operazione richiesta.");
//	        	theElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />";
//	        	theElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
	        } // else (if ! 200)
	} // if 4

} // callback();


function countingframe() {
	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio")
	// non riesco tuttavia a intervenire per parsificarlo! è il browser che renderizza il src del iframe!
}// caricaFeedIframe()


function calcolaCambiAJAX(estx, esty, xhr, element, filename) {
	// impostazione controllo e stato della richiesta
	xhr.onreadystatechange = function() { callback(xhr, element); };
	// impostazione richiesta asincrona in GET
	// del file specificato
	try {
		xhr.open("post", "FIleManager", true);
	}
	catch(e) {
		// Exceptions are raised when trying to access cross-domain URIs 
		alert(e);
	}
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	// invio richiesta
	xhr.send(JSON.stringify({testo:element, x:parseInt(estx), y:parseInt(esty), filename:filename}));
}


function myFunction()
{   
	var testo = myGetElementById('area_testo').value;
	var filename = myGetElementById('filename').value;
	
	if( testo.length < 100 ) {   
		alert("Errore, ci sono meno di 1000 caratteri");
	    return;
	}
	
    var delta = (testo.length)/2;
	var x1 = 0;
	var x2 = delta;
	var x3 = testo.length;       // +nomeVariabile mi fa la conversione da String a int
	
	//PER FARE 4 RICHIESTE CONCORRENTI DEVO CREARE 4 OGGETTI xhr
        var xhr1 = myGetXmlHttpRequest();
        var xhr2 = myGetXmlHttpRequest();
        
		if ( xhr1 ) 
			calcolaCambiAJAX(x1, x2, xhr1, testo, filename);     
		if ( xhr2 )  
		    calcolaCambiAJAX(x2, x3, xhr2, testo, filename);     
		else 
			countingframe(); 
}

