var checkV = false;	//se inserita vocale
var checkC = false;	//se inserita consonante
const vocali = "aeiou";
const cons = "qwrtypsdfghjklzxcvbnm";


function corrispond(jsonText) {
	
	var thread = JSON.parse(jsonText);
	

    //--------------- variabili che cambiano a seconda del testo d'esame ------------ 
	// Ottengo la lista delle sezioni del docuemento
	var res = myGetElementById("res");
	
	res.innerHTML = thread;
	
	res.style.display="block";
}

function callback( theXhr ) {

	if ( theXhr.readyState === 2 ) {
	    //theElement.innerHTML = "Richiesta inviata...";
	}
	else if ( theXhr.readyState === 3 ) {
    	//theElement.innerHTML = "Ricezione della risposta...";
	}
	else if ( theXhr.readyState === 4 ) {
		// verifica della risposta da parte del server
		if ( theXhr.status === 200 ) {
			// operazione avvenuta con successo
			corrispond(theXhr.responseText);   //se la servlet ha eseguito con successo il metodo doGet, allora "traduco" la sua risposta
			//location.reload();
		}// if 200

		 else {
        	// errore di caricamento
		 	alert("Impossibile effettuare l'operazione richiesta.");
            //theElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />";
            //theElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
        }// else (if ! 200)
	}// if 4
}


function corrispnIframe() {
	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio")
	
	// non riesco tuttavia a intervenire per parsificarlo! è il browser che renderizza il src del iframe!
}

function corrispAJAX(theXhr, parola) {
	
	theXhr.onreadystatechange = function() { callback(theXhr); };   //invio
	
	// impostazione richiesta asincrona in GET del file specificato
	try {
        //devi modificare l'url (il secondo parametro, in base ai valori)
		theXhr.open("post", "servletCorrisp", true);
	}
	catch(e) {
		// Exceptions are raised when trying to access cross-domain URIs 
		alert(e);
	}

	var argument = "parola="+parola+"&ambiente="+vocali;
	
	// invio richiesta
	theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	theXhr.send(argument);
	
	
}


function myfunction(){
	
	var contenuto = myGetElementById("contenuto");
	var testo = contenuto.value;
	
	var testo = contenuto.value;
	var c = testo[testo.length-1];
	
	if(c == '%') {
		var	xhr = myGetXmlHttpRequest();
		if (xhr) {
			testo = testo.substring(0, testo.length - 1);
			corrispAJAX(xhr, testo); 
		}
		else 
			corrispnIframe(); 
	}
	
	
	
}