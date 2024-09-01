
function reservation( jsonText ) {
   
	// variabili di funzione
	
	
		var prenotazione = JSON.parse(jsonText);
		// Ottengo la lista delle sezioni del docuemento
		var resultDiv = document.getElementById("n");
		var risCod = document.getElementById("risCod");
		var risNotti = document.getElementById("risNotti");
		var risPrezzo = document.getElementById("risPrezzo");
		
		risCod = prenotazione.codiceHotel;
		risNotti = prenotazione.checkOut - prenotazione.checkIn;
		risPrezzo = prenotazione.prezzo;
		document.getElementById("risCI").value = prenotazione.checkIn;
		document.getElementById("risCO").value = prenotazione.checkOut;
		resultDiv.style.display="block";

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
			reservation(theXhr.responseText);
			
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


function reservationIframe() {
	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio")
	
	// non riesco tuttavia a intervenire per parsificarlo! è il browser che renderizza il src del iframe!
}// caricaFeedIframe()



function reservationAJAX( theXhr, codHotel, checkin, checkout) {
    
	
	// impostazione controllo e stato della richiesta
	theXhr.onreadystatechange = function() { callback(theXhr); };
	// impostazione richiesta asincrona in GET
	// del file specificato
	try {
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


function myFunction()
{
	
	var totHotel = document.getElementById("n").value;
	var codHotel = document.getElementById("codice").value;
	if(codHotel < 0 || codHotel >totHotel){
		alert("Codice Hotel Errato");
		return;
	}
		


	var checkin = document.getElementById("checkin").value;
	var checkout = document.getElementById("checkout").value;
	if(checkin >= checkout){
		alert("Errore nei giorni di checkin/out");
		return;
	}
	var	xhr = myGetXmlHttpRequest();

	if ( xhr ) 
		reservationAJAX(xhr, codHotel, checkin, checkout); 
	else 
		reservationIframe(); 
		
	
		

}