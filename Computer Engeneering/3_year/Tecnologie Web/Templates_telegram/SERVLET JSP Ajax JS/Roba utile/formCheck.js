//Controllo che tutti i campi abbiano qualcosa
function isAllCompiled(form) {
	for (var i = 0; i < form.elements.length; i++) {
		if (isBlank(form.elements[i]))
			return false;
	}
	return true;
}

//Controllo solo caratteri alfanumerici e tra 3 e 20 caratteri
function checkAndCall(str, resultDiv) {
	if (!/[^a-zA-Z]/.test(str.value) && /.{3,20}/.test(str.value)) {
		eseguiRichiesta("./filterServlet?stringa="+str.value, str.value, resultDiv);
	}
}


//Elimina dal campo "targa" qualsiasi carattere non alfanumerico
var allowedchars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
function checkTarga( targa, results ){
	var stringa = targa.value;
	if (stringa != null && stringa.length != 0) {
		for (var i=0; i<stringa.length; i++)
			if (allowedchars.indexOf(stringa.charAt(i)) < 0) {
				targa.value = stringa.substr(0, i);
				break;
			}
//		stringa = stringa.replace(/[a-z0-9]/gi, "");
//		targa.value = stringa;
	}
	if (targa.value != null && targa.value.length >= 7) {
		eseguiConcessionaria("./cercaMacchine?targa="+targa.value.toLowerCase(), targa.value.toLowerCase(), results)
		targa.value = "";
	}
}

//Form non vuota + email valida
function validateLoginForm(form) {
	for (var i = 0; i < form.elements.length; i++) {
		if (isBlank(form.elements[i]))
			return false;
	}

	if (!validEmail(form.elements.usermail))
		return false;

	return true;
}

//Campo non vuoto
function isBlank(myField) {
    var result = false;
    if (myField.value!=null && myField.value.trim() == "") {
        alert("Please enter a value for the '" + myField.name + "' field.");
        myField.focus();
        result = true;
    }
    return result;
}

// Check for "valid" email: not empty, has "@" sign and "."
function validEmail(myField) {
	var result = false;
	if (!isBlank(myField)) {
		var tempstr = new String(myField.value);
		var aindex = tempstr.indexOf("@");
		if (aindex > 0) {
			var pindex = tempstr.indexOf(".", aindex);
			if ((pindex > aindex + 1) && (tempstr.length > pindex + 1)) {
				result = true;
			} else {
				result = false;
			}
		}
	}
	if (!result) {
		alert("Please enter a valid email address in the form: yourname@yourdomain.com");
		myField.focus();
	}
	return result;
}

//Controllo e invio via JSON
function ajaxJSONStringify(form) {
	for (var i = 0; i < form.elements.length; i++) {
		if (isBlank(form.elements[i]))
			return;
	}
	// altri check
	var url = "ajaxservlet?param=...";
	// oppure
	var message = new Object();
	message.javaClass = "esame.beans.AjaxMessage";
	message.message = "ciao";
	var url2 = JSON.stringify(message);
	esegui(url, myGetElementById("response"));
	//url del tipo "nomeServlet?nomeparametro=valore"
	//dove valore puo' essere il valore serializzato via JSON
}

//Controlla se il campo myField contiene almeno un carattere presente in forbidden
function containsForbiddenCharacters(myField, forbidden) {
	var stringa = myField.value;
	for (var i = 0; i<stringa.length; i++){
		if(forbidden.indexOf(forbidden[i])!=-1)
			return true;
	}
	return false;
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

//Controlla numero + almeno minLen cifre
function numberLengthError(myField, minLen) {
	var error = numberError(myField);
	if (!error) {
		if (myField.value.trim().length < minLen)
			alert("Please enter a number of at least" + minLen + " digits '"
					+ myField.name + "' field.");
		myField.focus();
		error = true;
	}
	return error;
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

//Controlla che il numero sia float positivo
function positiveFloatError(myField) {
	var error = numberError(myField);
	if (!error) {
		var number = parseFloat(myField.value.trim());
		if (number < 0) {
			alert("Please enter a positive value in the '" + myField.name
					+ "' field.");
			myField.focus();
			error = true;
		}
	}
	return error;
}

//Controlla che il numero sia float e compreso tra [minumium, maximum]
function numberInRangeError(myField, minimum, maximum) {
	var error = numberError(myField);
	if (!error) {
		var number = parseFloat(myField.value.trim());
		if (number < minimum || number > maximum) {
			alert("Please enter a value between " + minimum + " and " + maximum
					+ " in the '" + myField.name + "' field.");
			myField.focus();
			error = true;
		}
	}
	return error;
}

//Controlla che l'URL in field sia valido
function validURL(field) {
	var regex = /(http|https):\/\/(\w+:{0,1}\w*)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%!\-\/]))?/;
	if(!regex .test(url.value)) {
		alert("Please enter valid URL.");
		url.value="";
		return false;
	} 
	return true;
}

//Controlla che la password sia valida
//Parametri personalizzabili
function validPwd(field) {
    var stringa = field.value;
    var numeri = 0;
    var maiuscola = 0;
    var speciale = 0;
    for (var i = 0; i < stringa.length; i++) {
        var c = stringa[i];
        if (c >= '0' && c <= '9')
            numeri++;
        else if (c >= 'A' && c <= 'Z')
            maiuscola++;
        else if ("!?$".indexOf(c) != -1)
            speciale++;
    }
    if(numeri<2||maiuscola<1||speciale<1){
        alert("Pwd deve contenere 2 numeri, 1 lettera maiuscola e un carattere speciale tra !, $ e ?")
				return false;
		} else
			return true;
}

//Setta eventListener all'evento indicato a tutte le celle di una tabella
function setListeners(table) {
    for (var i = 0; i < table.children.length; i++) {
        var tr = table.children[i];
        if (tr.tagName == "TR") {
            for (var j = 0; j < tr.children.length; j++) {
                var td = tr.children[j];
                if (td.tagName == "TD") {
                    td.addEventListener("click", new Function("show('" + td.innerText + "');"));
                }
            }
        }
    }
}

//Setta attributi alle immagini
function show(name) {
    for (var i = 1; i <= 4; i++) {
        var img = myGetElementById("img" + i);
        img.setAttribute("src", name + "-" + i + ".jpg");
        img.setAttribute("alt", name + "-" + i + ".jpg");
    }
}

// crea tante caselle di testo per filename e tante caselle di risultato quante il valore del field n
function spawnTextBoxes(n) {
	var div = myGetElementById('div');
	div.innerHTML="";
	if (checkConstraintNumber(n)) {
		for (var i =0; i<parseInt(n.value); i++) {
			var input = document.createElement('input');
			input.setAttribute('type', 'text');
			input.setAttribute('name', 'filename');
			input.setAttribute('onkeyup', 'esegui()');
			div.appendChild(input);

			var resUl = document.createElement('p');
			resUl.setAttribute('id', 'result' + i);
			div.appendChild(resUl);

			div.appendChild(document.createElement('br'));
		}
	}
}