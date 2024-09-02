
const socket = new WebSocket("ws://localhost:8080/tw_esame/actions");

socket.onmessage =  function (event){
	
	var message = event.data;
	document.getElementById("risultato").value += message+("\n");
	
}

function iscriviti()
{
  socket.send("Iscrivimi");
	
}

function invia(){
	var messaggio = document.getElementById("testo").value;
	
	socket.send(messaggio);
}
