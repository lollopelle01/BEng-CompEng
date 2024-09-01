const socket = new WebSocket("ws://localhost:8080/07_TecWeb_solved/actions");

socket.onmessage =  function (event){
	
	var message = event.data;
	
	 if(message.includes("Iscrizione")) {
	 	var iscritto = document.getElementById("iscritto");
	 	iscritto.value = message.valore;
	 	return;
	 }
	 else if(message.includes("aggiornamento")) {
		var agg = document.getElementById("aggiornamenti");
	 	agg.value += message+("\n");
	 	return;
	}
}

function iscrizione() {
	socket.send("iscrivimi");
	document.getElementById("aggiornamenti").value = "";
}