
var sessionStartTime;
var countRequest;
var itemNodes = new Array();
var count = 0;

function verifica( response ) {
   
	var items = JSON.parse(response);
	
	itemNodes[count] = new Object();
	itemNodes[count].valore = items.valore;  // false/true
	itemNodes[count].somma = items.somma;
	count++;
	
	if (count === 3){
		
		var result = myGetElementById('result');
		
		if (itemNodes[0].valore === false || itemNodes[1].valore === false || itemNodes[2].valore === false)
			result.innerHTML = '<p>FALSE</p><br/>';
		else
			result.innerHTML =  '<p>TRUE</p><br/><p>' + itemNodes[0].somma + '</p>'; 
	}
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
  			verifica(theXhr.responseText); // operazione avvenuta con successo
  			
        } else {
        	
        	theElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />"; // errore di caricamento
        	theElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
        }
	}
}



function quadratomagicoIframe() {
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio");
}


function quadratoMagicoAJAX(xhr1, xhr2, xhr3, values) {
    
	xhr1.onreadystatechange = function() { callback(xhr1); }; 
	xhr2.onreadystatechange = function() { callback(xhr2); }; 
	xhr3.onreadystatechange = function() { callback(xhr3); }; 
	

	try {
		xhr1.open("post", "S1", true); // somma righe uguali
		xhr2.open("post", "J1.jsp", true); // somma colonne uguali
		xhr3.open("post", "S2", true); // somma diagonali uguali
	}
	catch(e) {
		alert(e);
	}

	xhr1.setRequestHeader("connection", "close");
	xhr1.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr2.setRequestHeader("connection", "close");
	xhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr3.setRequestHeader("connection", "close");
	xhr3.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	var argument = "values="+values;
	
	xhr1.send(argument);
	xhr2.send(argument);
	xhr3.send(argument); 
}


function quadratoMagico(values) {
	
	var xhr1 = myGetXmlHttpRequest(); 
	var xhr2 = myGetXmlHttpRequest(); 
	var xhr3 = myGetXmlHttpRequest(); 

	if (xhr1) 
		quadratoMagicoAJAX(xhr1, xhr2, xhr3, values); 
	else 
		quadratomagicoIframe(); 
}

function checkRequest() {
	var now = new Date().getTime();
	
	var now_h = Math.round(now / (1000 * 60 * 60)); 			 //trasformo in ore
	var sessionStartTime_h = Math.round(sessionStartTime / (1000 * 60 * 60));  //trasformo in ore
	
	if( (now_h - sessionStartTime_h) >= 24 &&  countRequest >= 10)
		return true;
	else
		return false;
	
}

function checkMatrix() {
	
	if(checkRequest()) {
		alert("Hai esaurito le richieste della giornata");
		return;
	}
	
	var 
		m;
		matrix = new Array(25);
		
	// tutti i campi sono compilati e sono interi
	for(var i=0; i<25; i++) {
		m = MyGetElementById("a"+i).value;
		if(m == '' || m.isNan()) 
			return;
		else
			matrix[i] = m;	
	}
	
	var count = 0;
	
	//non sono ripetuti
	for(var i=0; i<25; i++) {
		for(var j=0; j<25; j++) {
			if(matrix[i] == matrix[j])
				count++;
		}
		//ho controllato un elemento
		if(count != 1) {
			alert("Non sono ammessi valori ripetuti");
			return;
		}
		else
			count = 0;
	}
	
	var result = "";
	
	for(var i=0; i<25; i++) {
		result = result + matrix[i] + '@'; //a0@a1@a2@a3....
	}
	
	countRequest++;
	quadratoMagico(result);
}

function requestCounter() {
	sessionStartTime = new Date().getTime();
	countRequest = 0;
}
