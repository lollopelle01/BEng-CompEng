
function parsificaJSON( response ) {
   
	var risultato = JSON.parse(response);
	
	var textarea = MyGetElementById("fileChanged").value;
	var conteggio = MyGetElementById("conteggio").value;
	
	textarea.innerHTML = risultato.text;
	conteggio.innerHTML = risultato.conteggio;
	
	// oppure se voglio usare solo un <div id="result"> </div> faccio così:
	// var risultato = "<br>Numero di maiuscole: "+risultato.conteggio+"<br>";
	// risultato += "<br>Testo: "+risultato.text+"<br>"
}


function callback( theXhr, theElement ) {
	
	// verifica dello stato
	if ( theXhr.readyState === 2 ) {
	    	theElement.innerHTML = "Richiesta inviata...";
	}
	else if ( theXhr.readyState === 3 ) {
    		theElement.innerHTML = "Ricezione della risposta...";
	}
	else if ( theXhr.readyState === 4 ) {

		// verifica della risposta da parte del server
        if ( theXhr.status === 200 ) {
  			parsificaJSON(theXhr.responseText); 
  			
        } else {
        	
        	theElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />"; // errore di caricamento
        	theElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
        }
	}
}

function mainFunctionIframe() {
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio");
}


function changeTextAJAX(xhr, filename) {
    
	xhr.onreadystatechange = function() { callback(xhr); }; 
	
	try {
		theXhr.open("post", "S1", true);
	}
	catch(e) {
		alert(e);
	}

	xhr.setRequestHeader("connection", "close");
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	var argument = "filename="+filename;
	theXhr.send(argument); 
}


function changeText(filename) {

	var xhr = myGetXmlHttpRequest();

	if (xhr) 
		changeTextAJAX(xhr, filename); 
	else 
		changeTextIframe(); 
}

function checkName() {
	
	var filename = MyGetElementById("fileName").value;
	var c, ok = false;
	
	for(var i=0; i<filename.length-1; i++) {
		c = filename[i];
		if( (c >= 'a'  && c <= 'b') || (c >= 'A'  && c <= 'Z') || (c >= '0'  && c <= '9')) {
			ok = true;
		} else {
			ok = false;
			alert("Filename must contains only alphabetic or numeric characters");
			return;
		}
	}
	
	if(ok && filename[filename.length-1] == ' ') {
		changeText(filename);
	} else
		return;
}
