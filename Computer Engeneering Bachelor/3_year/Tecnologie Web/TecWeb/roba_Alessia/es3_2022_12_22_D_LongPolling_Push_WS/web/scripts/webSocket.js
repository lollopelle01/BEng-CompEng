//al caricamento della pagina, init la web socket
window.onload = init;

//dichiariamo posizione websocket nel progetto
var socket = new WebSocket("ws://localhost:8080/ServerPushLongPolling/WebSocketJava");

//alla onmessage invio mex alla web socket java (nell'annotazione)
socket.onmessage = onMessage;


function onMessage(event) {
	
	var msg = JSON.parse(event.data);
	console.log(msg);
	
	if(msg.action == "iscrizione"){
       iscrizioneNews(msg);
    } else if(msg.action == "news"){
       printNews(msg);
    } else if(msg.action == "admin"){
       adminOperation(msg);
    } else {
		alert("Errore con la connessione! Prego aggiorare la pagina")
	}  
}

function printNews(msg) {
	var textarea = myGetElementById("news");
	textarea.innerHTML 	+= 	msg.news;
}


function adminOperation(msg) {
  
  var textarea = myGetElementById("news");
  textarea.style.display = 'none';
  
  var utenti = msg.usersOrdinati;
  console.log(utenti);
  
  var content = myGetElementById("iscrizione");
  content.innerHTML 	+= '<table>'
  					+	'<tr>'
						+		'<td>Sessione</td>'
						+		'<td>Time</td>'
						+	'</tr>';
  
  for(var i = 0; i < utenti.length; i++) {
		console.log(utenti[i]);
		/*content.innerHTML 	+= 	'<tr>'
							+		'<td>'utenti[i].nome</td>'
							+		'<td>Time</td>'
							+	'</tr>';*/
	}
}


function iscrizioneNews(msg) {    
	
  	var content = document.getElementById("iscrizione");
 	content.innerHTML = '<h3>Iscrizione avvenuta con successo!</h3>';
 	
 	if(msg.news != undefined && msg.news != null) {
	 	var textarea = myGetElementById("news");
		textarea.innerHTML 	+= 	msg.news;
 	}
 	
 	myGetElementById("addIscrizione").disabled = true;
 	myGetElementById("nickname").disabled = true;
 	myGetElementById("password").disabled = true;

}

function addIscrizione() {
	
	//prendo il nickname
    var nickName = myGetElementById("nickname").value;
    var pssw = myGetElementById("password").value;
    
    if(nickName.isEmpty || nickName.isBlank || pssw.isEmpty || pssw.isBlank) {
		alert("Inserisci le tue credenziali!");
		return;
	}
    
    //resetto il form di inserimento nikname
    document.getElementById("addIscrizione").reset();
    
    var IscrizioneAction = {
		action: "iscrizione",
		nickname: nickName,
		password: pssw,
  	};
    
    //invio dati alla socket
   	socket.send(JSON.stringify(IscrizioneAction));    
}


//init sempre tutta uguale
function init() {
	
    document.getElementById("iscrizione").innerHTML = "";
    document.getElementById("news").childNodes=[];
}
