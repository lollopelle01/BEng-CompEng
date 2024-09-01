var socket = new WebSocket("ws://localhost:8080/SK_Es3_23_12_2020/actions");//come settare il server a cui spedire i messaggi!!!!

function send( data) {//metodo per mandare un messaggio 
    var json = JSON.stringify(data);
    socket.send(json);
}


socket.onmessage =  function (event){  //metodo per ricevere un messaggio
	 var message = JSON.parse(event.data);//event.data={msg,num} || event.data={error}

			 //log sarà l'elemento del dom (div) dove stampo il risultato
		 	var log = document.getElementById("result");
			log.value = "";
			console.log(message);
			if(message.error!==undefined){//è stato ricevuto un campo error
				stampaError(message.error)
				return
			}
			else{
				log.value+="Result:"
				log.value += message.msg;
				log.value += "\n";
				log.value += message.num;
			}
}


function stampaError(msg){
	var log = document.getElementById("error");
	log.value = "";
	log.value+="Errore:"
	log.value += "<strong>"+msg+"</strong>";
	log.value += "<br>";
}

function iscriviti(){
	var operazione="iscrizione"
	var data={
		operazione:operazione,
		giocatore: ""
	}
	send(data);
}

function avvia(){
	var operazione="avvia"
		var data={
		operazione:operazione,
		giocatore: ""
	}
	send(data);
}


function abbandona(){
	var operazione="abbandona"
		var data={
		operazione:operazione,
		giocatore: ""
	}
	send(data);
}
