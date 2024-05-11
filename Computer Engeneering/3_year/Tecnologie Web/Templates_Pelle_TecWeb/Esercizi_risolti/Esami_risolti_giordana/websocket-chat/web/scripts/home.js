const socket = new WebSocket("ws://localhost:8080/14_04_2022_es3/actions");

function send( data) {
    var json = JSON.stringify(data);
    socket.send(json);
}


socket.onmessage =  function (event){
	
	var message = JSON.parse(event.data);
	
	let messaggio = message.message;
	
	if(messaggio == "chat terminata"){
		console.log(messaggio);
		alert("la chat ha superato il tempo limite");
	}
	else{
		if(messaggio == "Operazione impossibile, messaggio proibito"){
			console.log(messaggio);
		}
		else{
			let chat = message.chat;
			console.log("aggiorno la chat");
			$("#chat").val(chat);
		}
	}
}


//-----------------------------------------//
/*
$(document).on({
	ready: function(){
		var Request = {};
		Request.message = "avvio"
		send(Request);
	}
})
*/

$(document).on("click", "button", function(){
	let text = $("#text").val();
	console.log("Utente ha scritto: " + text);

    var Request = {};
    Request.text = text;
    Request.message = "normale";
    console.log("invio messaggio");
    $("#text").val("");
    
    send(Request);
            
})