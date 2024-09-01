function controllaParole(ricercaField) {
	if (!isBlank(ricercaField)) {
		if( /^[a-z]+$/i.test(ricercaField.value.trim()) || /^[a-z]+ [a-z]+$/i.test(ricercaField.value.trim())) {
			return true;
		} else {
			alert("Ammesse al massimo 2 parole");
	        myField.focus();
	        result = true;
		}
	}
	return false;
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