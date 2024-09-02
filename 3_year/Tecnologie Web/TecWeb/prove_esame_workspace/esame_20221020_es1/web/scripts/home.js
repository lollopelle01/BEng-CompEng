function richiestaDiAggiornamento(flag){
    richiestaAJAX("get", "/service", "flag="+flag);
}

function gestisciRispostaAJAX(responseText){
    // Traduco JSON in testo
    var response = JSON.parser(responseText);

    // Inserisco il risultato nel tag
    $("ajax_message").val(response);
}

/**
 * TEMPLATE: invio della richiesta
 * 
 * @param {*} metodo --> get , post
 * @param {*} link --> /nome_server
 * @param {*} parametri --> par1=par1_value&par2=par2_value
 */
function richiestaAJAX(metodo, link, parametri){
    let xhr = new XMLHttpRequest();

    if(xhr) {

        xhr.onreadystatechange = function() {
            callback(xhr);
        }

        try {
            if(metodo=="get"){xhr.open(metodo, link + "?" + parametri, true);} // GET
            else{xhr.open(metodo, link, true);} // POST
        }
        catch(e) {
            alert(e);
        }

        if(metodo=="get"){xhr.send(null);} // GET
        else{xhr.send(parametri);} // POST

    } else {
        $("#ajax_message").text("Impossibile eseguire l'operazione. Browser troppo vecchio!")
    }
}
 
// TEMPLATE: stato della risposta --> viene usata in automatico
function callback(xhr) {
	if ( xhr.readyState === 2 ) {
        $("#ajax_message").text("Richiesta inviata...")
    } else if ( xhr.readyState === 3 ) {
        $("#ajax_message").text("Attesa risposta...")
    } else if ( xhr.readyState === 4 ) {
        if ( xhr.status === 200 ) {
            gestisciRispostaAJAX(xhr.responseText);
        } else {
            $("#ajax_message").text("Impossibile eseguire l'operazione.")
        }
    }
}