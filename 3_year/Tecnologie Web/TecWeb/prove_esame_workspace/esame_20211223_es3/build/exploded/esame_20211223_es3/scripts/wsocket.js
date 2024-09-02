// Inizializziamo in memoria una chat
var chat = [];

// Creazione della socket
const socket = new WebSocket("ws://localhost:8080/Template_WebSocket/actions");

// Invio dei dati al server
function myFunction()
{
	// Estrapolazione dei dati
	var nomeMittente = $("#name_mittente").val();
	var testoMittente = $("#testo_mittente").val();

	// Controllo dei dati 
	// - - - 

	// Incapsulamento dei dati
	var richiesta = {
		nome: nomeMittente,
		testo: testoMittente
	};
	
	// Invio dei dati
	socket.send(JSON.stringify(richiesta));
	
}

// Per quando riceviamo messaggi dal server, su nostra richiesta
socket.onmessage =  function (event){
	
	console.log("[socket.onmessage]", event.data);
	
	// Estraiamo il messaggio
	var risposta = JSON.parse(event.data);

	// Gestione della risposta
	chat.push(risposta);
	$("#testo_chat").val("")
	chat.forEach( function (msg){
		messaggio = "\nNome: " + msg.nome + 
					"\nTesto:\n" + msg.testo +
					"\n------------------------"; 
		// Scrivo in append
		$("#testo_chat").val($("#testo_chat").val() + messaggio);
	});
	
}
