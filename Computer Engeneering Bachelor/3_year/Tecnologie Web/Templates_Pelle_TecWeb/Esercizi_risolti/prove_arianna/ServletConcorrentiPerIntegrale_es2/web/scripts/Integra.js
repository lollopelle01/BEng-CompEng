
// variabili globali
var q1 =-1;
var q2 =-1;
var q3 =-1;
var q4 =-1;
var requestCounter = 0;
var sessionStartTime = -1;
var lastCall = -1;

function final( partial ) {
	
	if(q1 == -1) 
		q1 = partial;
	else if (q2 == -1) 
		q2 = partial;
	else if (q3 == -1) 
		q3 = partial;
	else {
		
		q4 = partial;
		result = q1+q2+q3+q4;
		final = myGetElementById('final');
		final.value = result;
		
		q1 =-1;
		q2 =-1;
		q3 =-1;
		q4 =-1;
	}
}

 // funzione di callback
function callback( theXhr, theElement ) {
	
	if ( theXhr.readyState === 2 ) {
	    	theElement.innerHTML = "Richiesta inviata...";
	}
	else if ( theXhr.readyState === 3 ) {
    		theElement.innerHTML = "Ricezione della risposta...";
	}
	else if ( theXhr.readyState === 4 ) {

        if ( theXhr.status === 200 ) {
  			final(theXhr.responseText); // operazione avvenuta con successo
 
        } else {
        	
        	theElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />"; // errore di caricamento
        	theElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
        }
	}
}


// non usa AJAX; per browser legacy
function myIntegralIframe() {
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio");
}


// usa tecniche AJAX attrverso la XmlHttpRequest fornita in theXhr
function myIntegralAJAX(xhr1, xhr2, xhr3, xhr4) {
    
    xhr1.onreadystatechange = function() { callback(xhr1); };
    xhr2.onreadystatechange = function() { callback(xhr2); };
    xhr3.onreadystatechange = function() { callback(xhr3); };
    xhr4.onreadystatechange = function() { callback(xhr4); };
	

	//METODO POST
	try {
		xhr1.open("post", "IntegralCalculator", true);
		xhr2.open("post", "IntegralCalculator", true);
		xhr3.open("post", "IntegralCalculator", true);
		xhr4.open("post", "IntegralCalculator", true);
	}
	catch(e) {
		alert(e);
	}

	xhr1.setRequestHeader("connection", "close"); 
	xhr2.setRequestHeader("connection", "close"); 
	xhr3.setRequestHeader("connection", "close"); 
	xhr4.setRequestHeader("connection", "close"); 
	
	xhr1.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr2.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr3.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr4.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	
	x = MyGetElementById("x").value;
	y = MyGetElementById("y").value;
	
	interval = x-y;
	quarter_interval = interval/4;
	
	x_temp = x;
	y_temp = x + quarter_interval;

	var argument = "x="+x_temp+"&y="+y_temp;
	xhr1.send(argument); 
	
	x_temp = y_temp +1; 
	y_temp = y_temp + quarter_interval;
	
	var argument = "x="+x_temp+"&y="+y_temp;
	xhr2.send(argument);
	
	x_temp = y_temp +1; 
	y_temp = y_temp + quarter_interval;
	
	var argument = "x="+x_temp+"&y="+y_temp;
	xhr3.send(argument);
	
	x_temp = y_temp +1; 
	y_temp = y;
	
	var argument = "x="+x_temp+"&y="+y_temp;
	xhr4.send(argument);
}

function closeSession() {
	
	requestCounter = 10;
	lastCall = 60000;
	alert('Sessione scaduta');
}

function sessionCounter() {
	
	sessionStartTime = new Date().getTime();
	lastCall = sessionStartTime;
	setTimeout(closeSession(), 1000*60*60); //1h in millisecondi
}


// gestisce sia AJAX che il mancato supporto ad AJAX 
function myIntegral() {
	
	var actualCall = new Date().getTime();
	var actualCall_min = Math.round(actualCall / (1000 * 60)); 	//trasformo in minuti
	var lastCall_min = Math.round(lastCall / (1000 * 60)); 			//trasformo in minuti
	
	if(requestCounter <= 5 && (actualCall_min - lastCall_min) <= 10) {
		lastCall = actualCall;
		requestCounter++;
		
		if(chekcXY()) {
		
		var 
		xhr1 = myGetXmlHttpRequest(); 
		xhr2 = myGetXmlHttpRequest();
		xhr3 = myGetXmlHttpRequest();
		xhr4 = myGetXmlHttpRequest();
	
		if (xhr1) 
			myIntegralAJAX(xhr1, xhr2, xhr3, xhr4); 
		else 
			myIntegralIframe();
		
		} else 
			alert("I valori di input devono essere compilati");
		
	} else 
		alert("sessione terminata");
}

function checkXY() {
	
	var
		x = MyGetElementById("x").value;
		y = MyGetElementById("y").value;
	
	if(x.length > 0 && y.length > 0)
		return true;
	else 
		return false;
}
