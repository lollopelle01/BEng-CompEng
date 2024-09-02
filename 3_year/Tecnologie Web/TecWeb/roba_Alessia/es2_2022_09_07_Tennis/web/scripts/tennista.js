var tennistiTab = new Array();
var cognome;

function tennisResp(jsonText) {
   
    var resp = JSON.parse(jsonText);
   
   	var res = myGetElementById("res");
   
   	console.log(resp.status);
   
   	if(resp.status == false) {
		res.innerHTML = '<p>' + 'Non partecipa agli US Open 2022' + '</p>';
		return;
	}
   
	console.log(resp)
   	tennistiTab = resp.tabTenn;
   	
   	for(var i = 0; i < tennistiTab.length; i++) {	
		//tennista trovato
		if(tennistiTab[i].cognome == cognome) {
			
			res.innerHTML = '<p>' 	+ 'Tennista: ' + tennistiTab[i].cognome + '<br>'
									+ 'Ranking: ' + tennistiTab[i].ranking + '<br>'
									+ 'Vittorie nel 2022: ' + tennistiTab[i].win + '<br>'
									+ 'Sconfitte nel 2022: ' + tennistiTab[i].lose + '<br>'
									+ 'Titoli: ' + tennistiTab[i].titoli + '<br>'
							+ '</p>';
		}
	}
}// parsificaJSON()



// -------------------------------------------------------------------------
//  ------------------------ Funzione di callback --------------------------
// ------------ necessaria per definire operazioni rispetto lo -------------
// --------------------------- lo stato dell'xml ---------------------------
// -------------------------------------------------------------------------
//copiarla così come sta
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
			tennisResp(theXhr.responseText);   //se la servlet ha eseguito con successo il metodo doGet, allora "traduco" la sua risposta
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
function tennisIframe() {
	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio")
	
	// non riesco tuttavia a intervenire per parsificarlo! è il browser che renderizza il src del iframe!
}// caricaFeedIframe()


// -------------------------------------------------------------------------
//  ---------------- Funzione di invio doGet alla servlet ------------------
// -------------------------------------------------------------------------
function tennisAJAX(theXhr) {
    
	theXhr.onreadystatechange = function() { callback(theXhr); };   //invio
	
	// impostazione richiesta asincrona in GET del file specificato
	try {
        //devi modificare l'url (il secondo parametro, in base ai valori)
		theXhr.open("post", "servletCerca", true);
	}
	catch(e) {
		// Exceptions are raised when trying to access cross-domain URIs 
		alert(e);
	}

	var argument = "cognome="+cognome;
	
	// invio richiesta
	theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	theXhr.send(argument);
	

} // caricaFeedAJAX()


// -------------------------------------------------------------------------
// -------------- Funzione richiamata dal jsp automaticamente --------------
// ------------------- quando finisco di inserire i dati -------------------
// -------------------------------------------------------------------------
/* nel jsp avrò un input con evento onfocusout="myFunction()" che invocherà
 * la fuzione sotto. Questa dipende tutta dal testo del problema ma
 * generalmente serve a:
 *      - controllare parametri inseriti negli input dall'utente
 *      - creare variabile myGetXmlHttpRequest()
 *      - controllare il valore di essa e:
 *          1. caso successo -> invoco funzione per inviare doGet (reservationAJAX in questo caso)
 *          2. caso errore -> invoco funzione per incompatibilità con il server (reservationIframe() in questo caso) */ 
function sendAJAX(){
	
	cognome = myGetElementById("cognome").value;
	
	console.log("cognome: " +  cognome);
	
	if(cognome == null || cognome == "" || cognome == " " || cognome.isEmpty || cognome.isBlank) {
		alert("Inserisci il cognome del tennista!");
		return;
	}
	
	if(tennistiTab != null && tennistiTab != undefined) {
		//prendo l'arrei e cerco il tennista
		for(var i = 0; i < tennistiTab.length; i++) {
			
			//tennista trovato
			if(tennistiTab[i].cognome == cognome) {
				console.log("trovato lato client");
				var res = myGetElementById("res");
				res.innerHTML = '<p>' 	+ 'Tennista: ' + tennistiTab[i].cognome + '<br>'
										+ 'Ranking: ' + tennistiTab[i].ranking + '<br>'
										+ 'Vittorie nel 2022: ' + tennistiTab[i].win + '<br>'
										+ 'Sconfitte nel 2022: ' + tennistiTab[i].lose + '<br>'
										+ 'Titoli: ' + tennistiTab[i].titoli + '<br>'
								+ '</p>';
				return;	
			}
		}
	} 

	var	xhr = myGetXmlHttpRequest();
	//controllo su xhr
	
	if (xhr) 
		tennisAJAX(xhr); 
	else 
		tennisIframe(); 
}