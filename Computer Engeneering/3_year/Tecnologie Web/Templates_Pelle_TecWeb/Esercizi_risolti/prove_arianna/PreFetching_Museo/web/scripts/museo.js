
var first = true;

var stanzan;
var stanzan1;
var stanzan2;

var n = 0;
var n1 = 0;
var n2 = 0;

function parsificaJSON( jsonText ) {
	
	var r = JSON.parse(jsonText);  //oggetto di tipo Risultato
	
	for(var i=0; i<r.length; i++) {
		
		if(r[i] != null ) {  //allora è una nuova stanza di cui fare il pre-fetching
			if(i==0)
				stanzan = r[i];
			if(i==1)
				stanzan1 = r[i];
			if(i==2)
				stanzan2 = r[i];
		}
	}
	//ora ho scaricato anche quelle che non avevo gia memorizzato
	
	var result = "Stanza n°"+stanzan.number+", opere:\n";
	for(var i=0; i<stanzan.opere.length; i++) {
		result += "-"+stanzan.opere[i].nome+" "+stanzan.opere[i].info+"\n";
	}
	
	return result;
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
  			theElement.innerHTML = parsificaJSON(theXhr.responseText);
        } 
	}
}


function visualizzaIframe() {
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio");
}

function visualizzaAJAX(theXhr, n, n1, n2) {
	
    var element = MyGetElementById("result").value;
	theXhr.onreadystatechange = function() { callback(theXhr, element); };
	
	try {
		theXhr.open("post", "S1", true);
	}
	catch(e) {
		alert(e);
	}

	theXhr.setRequestHeader("connection", "close");
	theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	var argument = "n="+n+"&n1="+n1+"&n2="+n2;
	
	theXhr.send(argument); 
}


function visualizza(stanza) {
	
	if(first) {
		n = stanza;
		n1 = stanza+1;
		n2 = stanza+2;
		first = false;
	} 
	else 
	{
		// -1: gia' in possesso, non devo scaricarla
		
		//memorizzo nelle giuste variabili quelle che ho gia'
		
		if(stanza == stanzan1.number) {  // ho già n e n+1
			n = -1;
			stanzan = stanzan1;
			n1 = -1;
			stanzan1 = stanzan2;
			n2 = stanza+2;
		} else if(stanza == stanzan2.number) {  // ho già n 
			n = -1;
			stanzan = stanzan2;
			n1 = stanza+1;
			n2 = stanza+2;
		} else {
			n = stanza;
			n1 = stanza+1;
			n2 = stanza+2;
		}
	}
	
	var xhr = myGetXmlHttpRequest(); 
	
	if (xhr) 
		visualizzaAJAX(xhr, n, n1, n2);
	else 
		visualizzaIframe(); 
}

function checkStanza() {

	var ok = false;
	var stanza = Number.parseInt(MyGetElementById("stanza").value);
	
	for(var i=0; i<=25; i++) {
		if(i == stanza) {
			ok = true;
			break;
		}
	}
	
	if(!ok)
		alert("La stanza deve essere valida...[0-25]");
	else 
		visualizza(stanza);
}
