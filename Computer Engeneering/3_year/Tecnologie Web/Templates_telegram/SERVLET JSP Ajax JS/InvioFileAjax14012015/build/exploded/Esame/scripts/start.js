function checkAndSend(form, resDiv) {
	for (var i=0; i<form.numerofiles.value; i++) {
		if (!isBlank(myGetElementById("file"+i)) && myGetElementById("file"+i).value.substr(0,2) != "C:") {
			alert("Invalid file path");
			return;
		}
	}
	if (isAllCompiled(form)) {
		for (var i=0; i<form.numerofiles.value; i++) {
			var fileInput = myGetElementById("file"+i);
			var file = fileInput.files[0];
			
			sendFile(file, i);
			
		    
		}
	}
}

function sendFile(file, i) {
	var reader = new FileReader();
	reader.onload = function() {
		var fileContent = reader.result;
	    var fileString = JSON.stringify(fileContent);
		var myI = i;
		var resDiv = myGetElementById("result"+myI);
		eseguiRichiesta("./addizioneServlet", resDiv, "file="+fileString);
    }
	reader.readAsText(file);
}

//Controllo che tutti i campi abbiano qualcosa
function isAllCompiled(form) {
	for (var i = 0; i < form.elements.length; i++) {
		if (isBlank(form.elements[i]))
			return false;
	}
	return true;
}

//Campo non vuoto
function isBlank(myField) {
    var result = false;
    if (myField.value!=null && myField.value.trim() == "") {
        result = true;
    }
    return result;
}

function spawnInputFiles(numerofiles, resDiv) {
	if (!positiveIntError(numerofiles)) {
		resDiv.innerHTML="";
		for (var i=0; i<numerofiles.value; i++) {
			resDiv.innerHTML+="<input type='file' name='file"+i+"' id='file"+i+"' onchange='checkAndSend(myGetElementById(\"invio_files\"), myGetElementById(\"result"+i+"\"))'/><div id='result"+i+"'></div><br/>";
		}
	}
}

//Controlla che il campo sia un numero
function numberError(myField) {
	var result = false;
	if (myField.value.trim() == "" || isNaN(myField.value.trim())) {
		alert("Please enter a numeric value for the '" + myField.name
				+ "' field.");
		myField.focus();
		result = true;
	}
	return result;
}

//Controlla che il numero sia intero positivo
function positiveIntError(myField) {
	var error = numberError(myField);
	if (!error) {
		var number = parseInt(myField.value.trim());
		if (number < 0) {
			alert("Please enter a positive value in the '" + myField.name
					+ "' field.");
			myField.focus();
			error = true;
		}
	}
	return error;
}


/*
 * Funzione di callback
 */
function eseguiCallback( theXhr, resDiv ) {

	// verifica dello stato
	if ( theXhr.readyState === 2 ) {
    	// non faccio niente
    	// theElement.innerHTML = "Richiesta inviata...";
	}// if 2
	else if ( theXhr.readyState === 3 ) {
    	// non faccio niente
		// theElement.innerHTML = "Ricezione della risposta...";
	}// if 3
	else if ( theXhr.readyState === 4 ) {
		// verifica della risposta da parte del server
	        if ( theXhr.status === 200 ) {
	        	// operazione avvenuta con successo	
		        if ( theXhr.responseText ) {
		        	
		        	resDiv.innerHTML = JSON.parse(theXhr.responseText);
						
				}
				else {
			    	// non faccio niente
				}
	        }
	        else {
	        	// errore di caricamento
	        	// non faccio niente nemmeno qui
	        }
	}// if 4
} // prodottoCallback();

/*
 * Imposta il contenuto disponibile presso theUri
 * come src di un iframe all'interno dell'elemento theHolder del DOM
 * Non usa AJAX; per browser legacy
 */
function eseguiIframe(theUri,theHolder) {
	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	theHolder.innerHTML = '<iframe src="' + theUri + '" width="50%" height="50px">Il tuo browser non supporta gli iframe</iframe>';
	// non riesco tuttavia a intervenire per parsificarlo! e' il browser che renderizza l'src dell'iframe!
}

function eseguiAJAXPost(uri, body, target, xhr) {

	xhr.onreadystatechange = function() {
		eseguiCallback(xhr, target);
	};

	// impostazione richiesta asincrona in POST del file specificato
	try {
		xhr.open("post", uri, true);
	} catch (e) {
		// Exceptions are raised when trying to access cross-domain URIs
		alert(e);
	}

	// rimozione dell'header "connection" come "keep alive"
	xhr.setRequestHeader("connection", "close");
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

	// invio richiesta (es: body="fname=Henry&lname=Ford")
	xhr.send(body);
}

function eseguiRichiesta(uri, resDiv, body) {

	// assegnazione oggetto XMLHttpRequest
	var xhr = myGetXmlHttpRequest();

	if ( xhr ) 
		eseguiAJAXPost(uri, body, resDiv, xhr);
	else 
		eseguiIframe(uri,resDiv);

}