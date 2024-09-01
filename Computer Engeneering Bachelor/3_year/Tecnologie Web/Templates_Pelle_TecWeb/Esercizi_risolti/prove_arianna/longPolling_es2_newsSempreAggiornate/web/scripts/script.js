let previousLetter = "";
let results = []


function checkResult(data, element) {
	
	if (!data)
		sendToServlet(previousLetter)
	
	results.push(data) // per aggiungere le nuove notizie a quelle già presenti
	
	let result = [];
	for ( const news of results ) {
		result.push("<h1>" + news.name + "</h1><br><p>" + news.content + "</p>");
	}

	element.innerHTML = result.join("<br>");

	sendToServlet(previousLetter);
	return;
}


function parsificaJSON(json, element) {
	
	var data = JSON.parse(json);
	checkResult(data, element);
}


function callback(theXhr, theElement) {
	
	// verifica dello stato
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


function sendToServlet(letter) {
	
	var theXhr = myGetXmlHttpRequest();
	var element = document.getElementById("ultimaora");
	
	theXhr.onreadystatechange = function() { callback(theXhr, element); }; 
	
	try {
		theXhr.open("post", "GetNews", true);
	}
	catch(e) {
		alert(e);
	}

	theXhr.setRequestHeader("connection", "close"); 
	theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	var argument = "letter=" + letter
	theXhr.send(argument);
}


function sendChar(elem) {
	previousLetter = elem.value.substring(0,1)
}


function init() {
	sendToServlet(previousLetter);  //prima chiamata con carattere vuoto
}
