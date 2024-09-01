//le funzioni in questo file (come in tutti i file .js) sono scritte in ordine
//di chiamata ma decrescente. Dichiaro e definisco prima le funzioni che mi
//serviranno più tardi in modo tale da avere la certezza che siano state caricate
//dal mio web server.

// -------------------------------------------------------------------------
//  ------------ Funzione che mi trasforma il JSON della risposta ----------
// ------------ ottenuta dalla servlet Booking (con il metodo get) ---------
// ------------------------- in formato leggibile --------------------------
// -------------------------------------------------------------------------
//modificare solo il nome delle variabili e i nomi degli id nel jsp o html
function reservation( jsonText ) {
   
	// variabili di funzione
	var prenotazione = JSON.parse(jsonText);

    //--------------- variabili che cambiano a seconda del testo d'esame ------------ 
	// Ottengo la lista delle sezioni del docuemento
	var resultDiv = myGetElementById("n");
	var risCod = myGetElementById("risCod");
	var risNotti = myGetElementById("risNotti");
	var risPrezzo = myGetElementById("risPrezzo");
	
	risCod = prenotazione.codiceHotel;
	risNotti = prenotazione.checkOut - prenotazione.checkIn;
	risPrezzo = prenotazione.prezzo;
	myGetElementById("risCI").value = prenotazione.checkIn;
	myGetElementById("risCO").value = prenotazione.checkOut;
	resultDiv.style.display="block";

}// parsificaJSON()



// -------------------------------------------------------------------------
//  ------------------------ Funzione di callback --------------------------
// ------------ necessaria per definire operazioni rispetto lo -------------
// --------------------------- lo stato dell'xml ---------------------------
// -------------------------------------------------------------------------
//copiarla così come sta
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
			reservation(theXhr.responseText);   //se la servlet ha eseguito con successo il metodo doGet, allora "traduco" la sua risposta
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



// -------------------------------------------------------------------------
//  --------- Funzione di controllo compatibilità con il browser -----------
// -------------------------------------------------------------------------
function reservationIframe() {
	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio")
	
	// non riesco tuttavia a intervenire per parsificarlo! è il browser che renderizza il src del iframe!
}// caricaFeedIframe()


// -------------------------------------------------------------------------
//  ---------------- Funzione di invio doGet alla servlet ------------------
// -------------------------------------------------------------------------
function reservationAJAX(theXhr, codHotel, checkin, checkout) {
    
	// impostazione controllo e stato della richiesta
	theXhr.onreadystatechange = function() { callback(theXhr); };   //invio
	
	// impostazione richiesta asincrona in GET del file specificato
	try {
        //devi modificare l'url (il secondo parametro, in base ai valori)
		theXhr.open("get", "Booking?codice="+codHotel+"&checkin="+checkin+"&checkout="+checkout, true);
	}
	catch(e) {
		// Exceptions are raised when trying to access cross-domain URIs 
		alert(e);
	}

	// rimozione dell'header "connection" come "keep alive"
	theXhr.setRequestHeader("connection", "close");	
	theXhr.send(null);
	

} // caricaFeedAJAX()


// -------------------------------------------------------------------------
// -------------- Funzione richiamata dal jsp automaticamente --------------
// ------------------- quando finisco di inserire i dati -------------------
// -------------------------------------------------------------------------
/* nel jsp avrò un input con evento onfocusout="myFunction()" che invocherà
 * la fuzione sotto. Questa dipende tutta dal testo del problema ma
 * generalmente serve a:
 *      - controllare parametri inseriti negli input dall'utente
 *      - creare variabile myGetXmlHttpRequest()
 *      - controllare il valore di essa e:
 *          1. caso successo -> invoco funzione per inviare doGet (reservationAJAX in questo caso)
 *          2. caso errore -> invoco funzione per incompatibilità con il server (reservationIframe() in questo caso) */ 
function myfunction(){
	
	var numAlberghi = myGetElementById("num").value;
	var codHotel = myGetElementById("IdHotel").value;
	
	if(codHotel < 0 || codHotel > numAlberghi) {
		alert("Codice Hotel Errato");
		return
	}
	
	var checkIn = myGetElementById("CheckIn").value;
	var checkOut = myGetElementById("CheckOut").value;
	
	if(checkIn > checkOut) {
		alert("Il giorno di checkIn dev'essere più piccolo di quello del checkout");
		return
	}
	
	//una volta aver fatto un controllo su numero Hotel e data checkin e checkout,
	//richiamo la funzione per effettuare la doGet
	
	var	xhr = myGetXmlHttpRequest();
	//controllo su xhr
	
	if (xhr) 
		reservationAJAX(xhr, codHotel, checkin, checkout); 
	else 
		reservationIframe(); 
		
}