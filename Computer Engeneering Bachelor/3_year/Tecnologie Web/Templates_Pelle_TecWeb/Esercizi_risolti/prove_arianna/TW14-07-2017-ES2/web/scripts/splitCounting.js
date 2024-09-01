
function counter( jsonText ) {
	
	var conteggio = JSON.parse(jsonText);
	
	if(conteggio.value != -1) {
		var myRes = document.getElementById("result");
		myRes.value = conteggio.value;
	}
}

function callback( theXhr ) {

	if ( theXhr.readyState === 2 ) {
	    	//theElement.innerHTML = "Richiesta inviata...";
	}
	else if ( theXhr.readyState === 3 ) {
    	//	theElement.innerHTML = "Ricezione della risposta...";
	}
	else if ( theXhr.readyState === 4 ) {
		
		if ( theXhr.status === 200 ) {
			counter(theXhr.responseText); // operazione avvenuta con successo
			
		} else 
			 alert("Impossibile effettuare l'operazione richiesta.");
	}
}


function countingIframe() {
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio")
}

function countingAJAX( theXhr1, theXhr2, fileName, text) {
	
	var file = fileName;
	var primaMeta = text.substr(0, (text.length/2));
	var secondaMeta = text.substr(primaMeta.length, (text.length - primaMeta.length));

	theXhr1.onreadystatechange = function() { callback(theXhr1); };
	theXhr2.onreadystatechange = function() { callback(theXhr2); };

	try {
		theXhr1.open("post", "CounterDispatcher", true);
		theXhr2.open("post", "CounterDispatcher", true);
	}
	catch(e) { 
		alert(e);
	}

	theXhr1.setRequestHeader("connection", "close");
	theXhr2.setRequestHeader("connection", "close");
	
	theXhr1.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	theXhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	var argument = "testo="+primaMeta+"&file="+file;
	theXhr1.send(argument);
	
	argument = "testo="+secondaMeta+"&file="+file;
	theXhr2.send(argument);
} 

function myFunction() {
	
	var text = document.getElementById("myText");
	var fileName = document.getElementById("fileName");
	
	if(text.value.length > 0 && fileName.value.length > 0) {
		
		var	xhr1 = myGetXmlHttpRequest();
		var xhr2 = myGetXmlHttpRequest();
		if ( xhr1 ) 
			countingAJAX(xhr1, xhr2, fileName.value, text.value); 
		else 
			countingIframe(); 
	} else
		alert("the inputs text cannot be empty!");
	
}
