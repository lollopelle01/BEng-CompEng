const socket = new WebSocket("ws://localhost:8080/07_TecWeb_solved/actions");

function send(data) {
	
    var json = JSON.stringify(data);
    socket.send(json);
}

socket.onmessage =  function (event) {
	
	var message = JSON.parse(event.data);
	
 	var chat = document.getElementById("chat");
 	chat.value += message+("\n");
 	return;
}

function sendMsg() {
	var msg = document.getElementById("msg").value;
	
	// empty check
	if (msg.length == 0){
		alert("Il messaggio non può essere nullo");
		return;
	}
	
	socket.send(msg);
}

function connect() {
	socket.send("connect me");
	document.getElementById("divChat").style.display = "block";
	document.getElementById("chat").value = "";
}

