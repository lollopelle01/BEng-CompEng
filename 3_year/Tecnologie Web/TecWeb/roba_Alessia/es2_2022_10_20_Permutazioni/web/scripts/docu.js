var checkV = false;	//se inserita vocale
var checkC = false;	//se inserita consonante
const vocali = "aeiou";
const cons = "qwrtypsdfghjklzxcvbnm";
	

function corrispond(jsonText) {
   
	// variabili di funzione
	var thread = JSON.parse(jsonText);

	var res = myGetElementById("res");
	res.innerHTML = "";
	
	console.log(thread.anagrammi);
	
	var anagrammi = [];
	anagrammi = thread.anagrammi;
	
	console.log(thread.time);
	
	res.innerHTML = '<p>' + 
						'Primo anagramma: ' + anagrammi[0] + '<br>' +
						'Secondo anagramma: ' + anagrammi[1] + '<br>' +
						'Terzo anagramma: ' + anagrammi[2] + '<br>' +
						'Quarto anagramma: ' + anagrammi[3] + '<br>' +
						'Quinto anagramma: ' + anagrammi[4] + '<br>' +
						'Tempo impiegato: ' + thread.time +
					'<p>';
	
	res.style.display="block";
	
	var contenuto = myGetElementById("contenuto");
	contenuto.value = '';

}// parsificaJSON()


function callback( theXhr ) {

	if ( theXhr.readyState === 2 ) {
	    	//theElement.innerHTML = "Richiesta inviata...";
	}// if 2
	else if ( theXhr.readyState === 3 ) {
    	//	theElement.innerHTML = "Ricezione della risposta...";
	}// if 3
	else if ( theXhr.readyState === 4 ) {
		// verifica della risposta da parte del server
		if ( theXhr.status === 200 ) {
			// operazione avvenuta con successo
			corrispond(theXhr.responseText);   //se la servlet ha eseguito con successo il metodo doGet, allora "traduco" la sua risposta
			//location.reload();
		}// if 200

		 else {
	        alert("Impossibile effettuare l'operazione richiesta.");
	     }// else (if ! 200)
	}// if 4
} // callback();


function corrispnIframe() {
	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio")
	
	// non riesco tuttavia a intervenire per parsificarlo! è il browser che renderizza il src del iframe!
}// caricaFeedIframe()


function corrispAJAX(theXhr, parola) {
    
	// impostazione controllo e stato della richiesta
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
	
	if(contenuto.value == '');
	else {
		if(c == '%') {
			
			testo = testo.substring(0, testo.length - 1);
			
			//controllo lunghezza
			if(testo.length < 5 || testo.length > 20) {
				alert("Lunghezza della parola non corretta, dev'essere compresa tra 5 e 20");
				checkV == false;
				checkC == false;
				contenuto.value = "";
				return;
			}
			
			//controllo se c'è vocale
			if(checkV == false) {
				alert("Non è stata inserita alcuna vocale");
				checkV == false;
				checkC == false;
				contenuto.value = "";
				return;
			}
			
			
			//controllo se c'è consonante
			if(checkC == false) {
				alert("Non è stata inserita alcuna consonante");
				checkV == false;
				checkC == false;
				contenuto.value = "";
				return;
			}
			
			//posso inviare	2 richieste
			if(checkC == true && checkV == true && testo.length > 4 && testo.length < 21) {
				var	xhr = myGetXmlHttpRequest();
				if (xhr)
					corrispAJAX(xhr, testo); 
				else 
					corrispnIframe(); 
			}	
		} else {
			if(vocali.includes(c)) {
				checkV = true;
			} else if(cons.includes(c)) {
				checkC = true;
			} else {
				alert("Prego inserire solo caratteri minuscoli alfabetici");
				testo =  testo.substring(0, testo.length - 1);
				contenuto.value = testo;
			}
		}
	}
}