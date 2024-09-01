//Controlla che tutti i campi della form abbiano un valore
function isFullForm(form) {
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
        alert("Please enter a value for the '" + myField.name + "' field.");
        myField.focus();
        result = true;
    }
    return result;
}

//Check for "valid" email: not empty, has "@" sign and "."
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