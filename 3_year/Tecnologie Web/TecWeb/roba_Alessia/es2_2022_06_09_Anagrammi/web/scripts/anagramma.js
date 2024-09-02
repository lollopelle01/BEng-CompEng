const char = 'qwertyuiopasdfghjklzxcvbnm';

function win(e){
	
	var res = myGetElementById("res");
	
	if(e.value == 's1') {
		res.innerHTML += '<h3>' + 'Ha vinto la servlet S1!' + '</h3>';		
	} else {
		res.innerHTML += '<h3>' + 'Ha vinto la servlet S2!' + '</h3>';		
	}
}


function anagramma(jsonText) {
	
	var convert = JSON.parse(jsonText);
	
	if(convert.anagrammi.length == 10) {
		
		console.log("servlet: " + convert.servlet + " suoi anagrammi: " + convert.anagrammi);
		
		var res = myGetElementById("res");
		res.innerHTML += '<p>' + 'Servlet ' + convert.servlet;
		
		for(var i = 1; i < 11; i++) {
			res.innerHTML += 'Anagramma n. ' + i + ': ' + convert.anagrammi[i-1] + '<br>';
		}
		
		res.innerHTML += '<button onClick="win(this);" value="'+convert.servlet+'">Win</button></p>';
			
			
	} else {
		alert("Errore nel trovare gli anagrammi");
	}	
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
			console.log("ricevuto risposta");
			anagramma(theXhr.responseText);   //se la servlet ha eseguito con successo il metodo doGet, allora "traduco" la sua risposta
			//location.reload();
		}// if 200

		 else {
	        	// errore di caricamento
			 alert("Impossibile effettuare l'operazione richiesta.");
                //theElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />";
                //theElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
	        }// else (if ! 200)
	}// if 4
} // callback();



// -------------------------------------------------------------------------
//  --------- Funzione di controllo compatibilità con il browser -----------
// -------------------------------------------------------------------------
function anagrammaIframe() {
	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio")
	
	// non riesco tuttavia a intervenire per parsificarlo! è il browser che renderizza il src del iframe!
}// caricaFeedIframe()


// -------------------------------------------------------------------------
//  ---------------- Funzione di invio doGet alla servlet ------------------
// -------------------------------------------------------------------------
function anagrammaAJAX(theXhr1, theXhr2, parola) {
    
	// impostazione controllo e stato della richiesta
	theXhr1.onreadystatechange = function() { callback(theXhr1); };   //invio
	theXhr2.onreadystatechange = function() { callback(theXhr2); };   //invio
	
	try {
        //devi modificare l'url (il secondo parametro, in base ai valori)
		theXhr1.open("post", "servletS1", true);
		theXhr2.open("post", "servletS2", true);
	}
	
	catch(e) {
		// Exceptions are raised when trying to access cross-domain URIs 
		alert(e);
	}
	
	var argument1 = "parola="+parola+"&servlet="+"S1";
	var argument2 = "parola="+parola+"&servlet="+"S2";

	// rimozione dell'header "connection" come "keep alive"
	theXhr1.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	console.log("invio richiesta S1");
	theXhr1.send(argument1);
	
	theXhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	console.log("invio richiesta S2");
	theXhr2.send(argument2);
	

} // caricaFeedAJAX()


function myfunction(){
	
	var txt = document.getElementById("parola");
	var parola = txt.value;
	
	var c = parola.charAt(parola.length - 1);
	
	if(c == '$') {	
		parola = parola.substring(0, parola.length -1);
		
		if(parola.length > 8){
			alert("Hai inserito troppi caratteri!");
			return;
		}
		
		if(parola.length < 4){
			alert("Hai inserito pochi caratteri!");
			txt.value = "";
			return;
		}
		
		txt.value = "";
		
		var	xhr1 = myGetXmlHttpRequest();
		var	xhr2 = myGetXmlHttpRequest();

		if (xhr1 && xhr2) 
			anagrammaAJAX(xhr1, xhr2, parola); 
		else 
			anagrammaIframe(); 
	} else {
		if(!char.includes(c)) {
			alert("Carattere non ammesso! Inserisci solo lettere minuscole");
			txt.value = parola.substring(0, parola.length -1);
		}
	}	
}