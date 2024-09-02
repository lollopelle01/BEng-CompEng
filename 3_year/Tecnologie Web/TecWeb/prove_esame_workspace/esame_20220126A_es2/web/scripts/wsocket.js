// Creazione della socket
const socket = new WebSocket("ws://localhost:8080/Template_WebSocket/actions");

// Per quando riceviamo messaggi dal server, su nostra richiesta
socket.onmessage =  function (event){
	
	console.log("[socket.onmessage]", event.data);
	
	// Estraiamo il messaggio
	var risposta = JSON.parse(event.data);

	if(risposta.type == "valore_singolo"){
		// inserisco valore
		var i = risposta.riga;
		var j = risposta.colonna;
		$("#" + i + "_" + j).val(risposta.valore);
		$("#" + i + "_" + j).setAttribute("readonly", "true"); // Il valore non sarà sovrascrivibile

		// Notifichiamo il client
		$("#msg").append("Inserimento concorrente: val[" + i +"]" + "[" + j + "] = " + risposta.valore + "\n");
	}
	else{ //risposta.type == "matrice" 

		// Notifichiamo il client
		$("#msg").append("Matrice completata\n");

		// creo matrice finale
		let matrix = new Array(3);
		for(var i=0; i<3; i++){matrix[i] = new Array(3);}

		// Estraggo matrice finale
		for(var i=0; i<3; i++){
			for(var j=0; j<3; j++){
				matrix[i][j] = parseInt($("#" + i + "_" + j).val());
			}
		}

		/** LA MATRICE È PIENA **/
		// Procediamo a valutarne le proprietà --> scriviamo alle 2 servlet
		richiestaAJAX("get", "/service1", "matrix=" + matrix, 1);
		richiestaAJAX("get", "/service2", "matrix=" + matrix, 2);

	}

}
