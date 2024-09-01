
var files = [];
var stop = false;

function parsificaJSON(jsonText, theElement) {
	
	//ogni servlet concorrente resistuisce un oggetto di tipo FileResult, oppure null
	
	var fileResult = JSON.parse(jsonText);  
	
	if(fileResult == null)    		//null, non ha scaricato niente, i file sono finiti
		stop = true;
	else 							//FileResult  		 
		files.push(fileResult);

	//stampa di tutti i file ricevuti
	risultato = "<ul>";
	for(var i=0; i<files.length; i++) {
		risultato += '<li class="file" ><strong>' + files[i].canzone +'</strong><br/>';
		risultato += files[i].length +"<br/>";
		risultato += files[i].formato +"<br/>";
		risultato += files[i].binario +"<br/>";
	};
	risultato += "</ul>";

    theElement.innerHTML = risultato;
    
    if(!stop)	
		download();
		
	// ognuna delle servlet che lavorano in concorrenza, se ci sono ancora file da trasferire,
	// ne richiama un'altra quando ha finito, quindi avrò sempre "i" servlet in parallelo
	// nel momento in cui una delle "i" servlet riporta null, allora sono finiti i file da trasferire
}

function callback( theXhr, theElement ) {
	
	if ( theXhr.readyState === 2 ) {
	    	theElement.innerHTML = "Richiesta inviata...";
	}
	else if ( theXhr.readyState === 3 ) {
    		theElement.innerHTML = "Ricezione della risposta...";
	}
	else if ( theXhr.readyState === 4 ) {

        if ( theXhr.status === 200 ) {
  			parsificaJSON(theXhr.responseText, theElement);
        } else {
        	
        	theElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />"; 
        	theElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
        }
	}
}


function download() {
    
    var theXhr = myGetXmlHttpRequest(); 
    var theElement = MyGetElementById("fileresult");
    theXhr.onreadystatechange = function() { callback(theXhr, theElement); };
    
	try {
		theXhr.open("post", "Downloader", true);
	}
	catch(e) {
		alert(e);
	}

	theXhr.setRequestHeader("connection", "close");
	theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	theXhr.send(null); 
}


function downloadConcorrente(num) {
	
	for(var i=0; i<num; i++) {
		download();
	}
}


function checkNum() {
	
	var num = MyGetElementById("num");
	num = Number.parseInt(num);
	
	if(num == 3 || num == 4) {
		downloadConcorrente(num);
	} else 
		alert("Il valore inserito non è valido...");
}
