
function parsificaXml( xml ) {
	
	var c = xml.getElementsByTagName("contatore");
	var	items = xml.getElementsByTagName("parola");
	
	var result = "";
	result += "<br>Sono state trovate "+c+" parole che iniziano per s, eccole:<br>";
	
	result += "<ul>"

	for (var a=0; a<items.length; a++) { 
		result += '<li> <strong>' + items[a] +'</strong> <br/> </li>'; 
	}

	result += "</ul>";
	
    return result;
}

function callback( theXhr) {
	
	if ( theXhr.readyState === 2 ) {
	    	theElement.innerHTML = "Richiesta inviata...";
	}
	else if ( theXhr.readyState === 3 ) {
    		theElement.innerHTML = "Ricezione della risposta...";
	}
	else if ( theXhr.readyState === 4 ) {

        if ( theXhr.status === 200 ) {
	
			if ( theXhr.responseXML )
				theElement=document.getElementById("risultato");
				theElement.innerHTML = parsificaXml(theXhr.responseXML);
  			
        } else {
        	
        	theElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />"; // errore di caricamento
        	theElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
        }
	}
}



function elaboraTextIframe() {
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio");
}


function elaboraTextAJAX(xhr, text) {
    
	xhr.onreadystatechange = function() { callback(xhr); }; 
	
	try {
		theXhr.open("post", "J1.jsp", true);
	}
	catch(e) {
		alert(e);
	}

	theXhr.setRequestHeader("connection", "close");
	theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	var argument = "text="+text;
	theXhr.send(argument); 
}


function elaboraText(text) {
	
	var xhr = myGetXmlHttpRequest(); 
	
	if (xhr) 
		elaboraTextAJAX(xhr, text); 
	else 
		elaboraTextIframe(); 
}

function checkText() {
	
	var text = MyGetElementById("myText").value;  //string
	
	if(text.length >= 100 && text.charAt(text.length-1) == '&') {
		elaboraText(text);
	}
}
