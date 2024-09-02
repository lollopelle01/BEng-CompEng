const socket = new WebSocket("ws://localhost:8080/tw_esame/actions");

socket.onmessage =  function (event){
	
	var message ="";
	try {
		message = JSON.parse(event.data);
	} catch {
		message = event.data
	}
	document.getElementById("risultato").value += (message + "\n");
	
}

function gioca(){
	socket.send("gioca");
}
function cinquina(){
	socket.send("cinquina");
}
function decina(){
	socket.send("decina");
}
function tombola(){
	socket.send("tombola");
}
function abbandona(){
	socket.send("abbandona");
}