// Aggiungo controllo e invio dati senza premere tasti
$("#check_out").on({
	focusout : function() {
		
		// Controllo di ID_Hotel
		if($("#id_albergo") > $("#numero_hotel")){ 
			alert("L'id dell'albergo è troppo grande");
			return; 
		}
			
		// Controllo di date
		if($("#check_in") > $("#check_out") || new Date().getDayOfYear() > $("#check_in")){ 
			alert("Check-out e/o Check-in insensati");
			return; 
		}
		
		// Invio richiesta con AJAX
		let xhr = new XMLHttpRequest();

        if(xhr) {

            xhr.onreadystatechange = function() { callback(xhr); }

            try {
                xhr.open("get", "service?id_hotel=" + $("#id_albergo") + "&check_in=" + $("#check_in") + "&check_out=" + $("#check_out"), true);
            }
            catch(e) {
                alert(e);
            }

            xhr.send(null);

        } else {
            $("#ajax_message").text("Impossibile eseguire l'operazione. Browser troppo vecchio!")
        }
		
	}
})

//in questa funzione dobbiamo eliminare un carattere random dal testo in modo client-side, per poi rmandare un altra volta la richiesta alla servlet
function callback(xhr) {
	if ( xhr.readyState === 2 ) {
        $("#ajax_message").text("Richiesta inviata...")
    } else if ( xhr.readyState === 3 ) {
        $("#ajax_message").text("Attesa risposta...")
    } else if ( xhr.readyState === 4 ) {
        if ( xhr.status === 200 ) {
        	// Risposta ricevuta
            risposta(xhr.responseText);
        } else {
            $("#ajax_message").text("Impossibile eseguire l'operazione.")
        }
    }
}

// Gestione risposta
function risposta( jsonText ){
	// Estraggo valori da risposta
	var prenotazione = JSON.parse(jsonText);
	
	// Inserisco valori di risposta
	$("#id_risp").val(prenotazione.id_hotel)
	$("#checkin_risp").val(prenotazione.check_in)
	$("#checkout_risp").val(prenotazione.check_out)
	$("#prezzo_finale").val(prenotazione.prezzo_finale)
	
	if ($("#prezzo_finale") < 0){alert("Posti esauriti");}
	
	// Rendo visibile la risposta
	$("#risposta").css("display", "block");
}	