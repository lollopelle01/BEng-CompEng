var socket = new WebSocket("ws://localhost:8080/SK_Es3_23_12_2020/actions");//come settare il server a cui spedire i messaggi!!!!

function send( data) {//metodo per mandare un messaggio 
    var json = JSON.stringify(data);

    socket.send(json);
}


socket.onmessage =  function (event){  //metodo per ricevere un messaggio
	 var message = JSON.parse(event.data);//event.data={msg,num}
			console.log(event.data);
			 //log sarà l'elemento del dom (div) dove stampo il risultato
		 	var log = document.getElementById("lista");
			log.value = "SessionID\n";
			if(mess)
			message.forEach(element => {
				log.value+=element
				log.value += "\n";
			});
}

function visualizza(){
	var operazione="visualizza"
	send(operazione);
}

function elimina(){
	var operazione="elimina"
	send(operazione);
}

