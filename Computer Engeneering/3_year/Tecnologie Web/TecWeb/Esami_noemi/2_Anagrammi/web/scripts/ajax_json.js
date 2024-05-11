var xhr1 = myGetXmlHttpRequest();
var xhr2 = myGetXmlHttpRequest();

function callback( theXhr, myElement ) {


	if ( theXhr.readyState === 2 ) {
	    	theElement.innerHTML = "Richiesta inviata...";
	}
	else if ( theXhr.readyState === 3 ) {
    		theElement.innerHTML = "Ricezione della risposta...";
	}
	else if ( theXhr.readyState === 4 ) {
	        if ( theXhr.status === 200 ) {
	        	
      			myElement.innerHTML = theXhr.responseText;
      			
	        }
	        else {
	        	myElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />";
	        	myElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
	        }
	}
}


function carica(xhr, uri, myElement) {

	xhr.onreadystatechange = function() { callback(xhr, myElement); };
	var testo = myGetElementById("testo").value;
	//se il metodo è post, bisogna aggiungere linea seguente
	//xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	
	try {
		xhr.open("get", uri+'?testo='+testo, true);
		//xhr.open("post", uri, true);
        //xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	}
	catch(e) {
		alert(e);
	}
	
	//xhr.send(JSON.stringify({category}));
	xhr.send(null);
}
