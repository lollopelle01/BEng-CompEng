// Creazione della socket
const socket = new WebSocket("ws://localhost:8080/Template_WebSocket/actions");

// Invio dei dati al server
function myFunction()
{
	// Estrapolazione dei dati
	// - - -

	// Controllo dei dati 
	// - - - 

	// Incapsulamento dei dati
	var richiesta = "iscrivimi"
	
	// Invio dei dati
	socket.send(richiesta);
	
}

// Per quando riceviamo messaggi dal server, su nostra richiesta
socket.onmessage =  function (event){
	
	console.log("[socket.onmessage]", event.data);
	
	// Estraiamo il messaggio
	var risposta = event.data;

	// Gestione della risposta
	//chat.push(risposta);
	$("#testo").val( $("#testo").val() + risposta );
	
}
