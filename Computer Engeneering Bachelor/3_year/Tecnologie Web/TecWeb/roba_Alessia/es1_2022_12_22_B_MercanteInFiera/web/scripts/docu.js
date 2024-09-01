function workOnResponse(jsonText) {
   
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

}

function callback( theXhr ) {

	if ( theXhr.readyState === 2 ) {
	    //theElement.innerHTML = "Richiesta inviata...";
	}
	else if ( theXhr.readyState === 3 ) {
    	//theElement.innerHTML = "Ricezione della risposta...";
	}
	else if ( theXhr.readyState === 4 ) {
		// verifica della risposta da parte del server
		if ( theXhr.status === 200 ) {
			// operazione avvenuta con successo
			workOnResponse(theXhr.responseText);   //se la servlet ha eseguito con successo il metodo doGet, allora "traduco" la sua risposta
			//location.reload();
		} else {
	        // errore di caricamento
			alert("Impossibile effettuare l'operazione richiesta."); 
	    }
	}
}


function requestErrorIframe() {
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio")
}


function sendRequestAJAX(theXhr, codHotel, checkin, checkout) {
   	theXhr.onreadystatechange = function() { callback(theXhr); };   //invio
	try {
    	theXhr.open("get", "Booking?codice="+codHotel+"&checkin="+checkin+"&checkout="+checkout, true);
	}
	catch(e) {
		alert(e);
	}
	theXhr.setRequestHeader("connection", "close");	
	theXhr.send(null);
}

function sendRequestAJAX(theXhr) {
    
	theXhr.onreadystatechange = function() { callback(theXhr); };   //invio
	try {
    	theXhr.open("post", "servletCerca", true);
	}
	catch(e) {
		alert(e);
	}

	var argument = "cognome="+cognome;
	
	theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	theXhr.send(argument);
}

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
		sendRequestAJAX(xhr, codHotel, checkin, checkout); 
	else 
		requestErrorIframe(); 
		
}