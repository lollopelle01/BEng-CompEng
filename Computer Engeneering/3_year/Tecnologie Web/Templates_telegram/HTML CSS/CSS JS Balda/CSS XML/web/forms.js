function checkboxChanged(newStatus, target) {
    target.disabled=!newStatus;
}

function validateForm(form) {
    if (isBlank(form.elements.cognomenome) || form.elements.sesso.value=="" || form.elements.uni.value=="" || (form.elements.lavoratore.checked && form.elements.desc.value==""))
        return false;
    alert("Login OK");
    return true;
}

function checkName(field) {
    var stringa = field.value;;
    for (var i = 0; i < stringa.length; i++) {
        var c = stringa[i];
        if (c >= '0' && c <= '9'){
            alert("numero nel nome")
        }
    }
}

function checkCAP(field) {
    var stringa = field.value;
    if (stringa.length!=5) {
        alert("cap non lungo 5 numeri")
    }
    for (var i = 0; i < stringa.length; i++) {
        var c = stringa[i];
        if (!(c >= '0' && c <= '9')) {
            alert("carattere illegale nel CAP")
            return;
        }
    }
}

function isBlank(myField) {
    // Check for non-blank field
    var result = false;
    if (myField.value && myField.value.trim() == "") {
        alert("Please enter a value for the '" + myField.name + "' field.");
        myField.focus();
        result = true;
    }
    return result;
}