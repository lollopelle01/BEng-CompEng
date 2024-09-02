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
        alert("Please enter a value for the '" + myField.name + "' field.");
        myField.focus();
        result = true;
    }
    return result;
}