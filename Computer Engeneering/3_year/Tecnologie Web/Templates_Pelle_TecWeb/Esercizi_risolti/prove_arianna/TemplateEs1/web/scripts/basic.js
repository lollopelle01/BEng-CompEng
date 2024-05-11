
function printResult( stringa ) {
   
	var result = stringa.split("@");
	var resElement;
	for(var i =0; i<result.length; i++)
		{
		resElement = document.getElementById("r"+i);
		resElement.value = result[i];
		}
	// Ottengo la lista delle sezioni del docuemento

	for(var i=0; i<8; i++)
		{
			var elementA = document.getElementById("a"+i);
			var elementB = document.getElementById("b"+i);
			elementA.value = '';
			elementB.value = '';
		}
}


function parsificaJSON( jsonText ) {
   
	// variabili di funzione
	var

		// Otteniamo la lista degli item dall'RSS 2.0 di edit
		items = JSON.parse(jsonText);

		// Predisponiamo una struttura dati in cui memorrizzare le informazioni di interesse
		itemNodes = new Array(),

		// la variabile di ritorno, in questo esempio, e' testuale, sarà uno stringone
		risultato = "";

	// ciclo di lettura degli elementi
	for (    var a = 0, b = items.length;    a < b;   a++   ) {
		// [length al posto di push serve per evitare errori con vecchi browser]
		itemNodes[a] = new Object();
		itemNodes[a].title = items[a].title;
		itemNodes[a].description = items[a].description;
		itemNodes[a].link = items[a].link;
	}// for ( items )

	// non resta che popolare la variabile di ritorno
	// con una lista non ordinata di informazioni

	// apertura e chiusura della lista sono esterne al ciclo for 
	// in modo che eseguano anche in assenza di items
	risultato = "<ul>";

	for( var c = 0; c < itemNodes.length; c++ ) {
		risultato += '<li class="item"><strong>' + itemNodes[c].title +'</strong><br/>';
		risultato += itemNodes[c].description +"<br/>";
		risultato += '<a href="' + itemNodes[c].link + '">approfondisci</a><br/></li>';
	};

	// chiudiamo la lista creata
	risultato += "</ul>";

     // restituzione dell'html da aggiungere alla pagina
     return risultato;

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
  			theElement.innerHTML = parsificaJSON(theXhr.responseText); // operazione avvenuta con successo
  			//element.innerText = METTE COME NUOVA STRINGA , += METTE IN APPEND 
  			// se non è un oggetto json:  
  			// theElement.innerHTML = theXhr.responseText;
  			
  			// MA potrebbe comunque richiedere una funzione di stampa corretta
  			// printResult(theXhr.responseText);
  			
        } else {
        	
        	theElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />"; // errore di caricamento
        	theElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
        }
	}
}



function mainFunctionIframe() {
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio");
}


function mainFunctionAJAX(theUri, theElement, theXhr) {
    
	theXhr.onreadystatechange = function() { callback(theXhr, theElement); }; // impostazione controllo e stato della richiesta
	
	//potrei dover settare piu servlet
	
	//METODO GET
	try {
		theXhr.open("get", theUri, true); // impostazione richiesta asincrona in GET del file specificato
	}
	catch(e) {
		alert(e);
	}

	theXhr.setRequestHeader("connection", "close"); // rimozione dell'header "connection" come "keep alive"

	theXhr.send(null); // invio richiesta alla jsp/servlet
	
	//METODO POST
	try {
		theXhr.open("post", "Servlet/Jsp", true);
	}
	catch(e) {
		alert(e);
	}

	theXhr.setRequestHeader("connection", "close");
	theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	var argument = "mode="+mode+"&aVal="+aVal+"&bVal="+bVal;
	
	theXhr.send(argument); // invio richiesta alla jsp/servlet
}


function mainFunction(uri,e) {
	
	//potrei dover prendere dei parametri dal documento e passarli alla funzione
	
	//potrei dover controllare dei valori inseriti con una checkValue
	
	//potrei dover passare i miei argomenti in formato json: var json= JSON.stringify(testo)
	
	var xhr = myGetXmlHttpRequest(); // assegnazione oggetto XMLHttpRequest
	
	//potrei dover assegnare piu xhr se è richiesta concorrenza di servlet

	if (xhr) 
		mainFunctionAJAX(uri,e,xhr);  //spesso passa anche i parametri qui
	else 
		mainFunctionIframe(uri,e); 

}

function checkValues() {
	//di solito se ok, sblocco qualche pulsante
	if(ok) {
		var currentValue = MyGetElementById("secret");
		currentValue.style.display = 'block';
	}
	
	//oppure invio subito qualcosa
}
