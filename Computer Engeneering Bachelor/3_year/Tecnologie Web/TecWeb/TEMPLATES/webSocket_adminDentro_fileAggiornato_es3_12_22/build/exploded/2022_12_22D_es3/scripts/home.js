const socket = new WebSocket("ws://localhost:8080/2022_12_22D_es3/actions");

function send( data) {
    var json = JSON.stringify(data);
    socket.send(json);
}


socket.onmessage =  function (event){
	
	var message = JSON.parse(event.data);
	
	
	if(message.message == "ok"){
		console.log(message.message);
		
		$("#text").val(message.chat);
	}
	else{
		alert(message.message);
	}
}


//-----------------------------------------//

$("#iscriviti").on("click", function(){

	let id = $("#session").val();
	console.log("l'utente " + id + " desidera unirsi al server");
	
    var Request = {};
    Request.id = id;
    Request.message = "utente";
    
    $("#iscriviti").hide();
    
    console.log("invio messaggio");
    
    send(Request);
            
})