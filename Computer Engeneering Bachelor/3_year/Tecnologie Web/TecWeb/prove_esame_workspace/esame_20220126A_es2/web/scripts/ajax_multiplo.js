//============================================================================================

/* GESTIONE AJAX */
// Utile se si vuole comunicare più volte tra lo stesso C-S

/**
 * TEMPLATE: gestione della risposta
 * 
 * @param {*} responseText --> risposta da gestire
 * @param {*} type --> 1, 2, 3... dipende dal numero di richesta
 */
function gestisciRispostaAJAX(responseText, type){
    // Traduco JSON in testo
    var response = JSON.parse(responseText);

    // Elaboro la response
    switch(type){
        case 1 : 
        { // risposta 1
            risp1 = response;
        }
        break;

        case 2 : 
        { // risposta 2
            risp2 = response;
        }
        break;

    }

    if(risp1!=null && risp2!=null ){ // analizziamo le risposta
        if(risp1.flag && risp2.flag ){ // tutte true
            console.log("Da /service: somma righe è " + risp1.somma)
            console.log("Da /service: somma colonne e diagonali è " + risp2.somma)

            alert("La matrice è un cubo magico");
        }
        else{ // anche solo una false
            alert("La matrice non è sun cubo magico");      
        }
    }
    
    // Inserisco il risultato nel tag
    // $("#ajax_message").val(response);
}

/**
 * TEMPLATE: invio della richiesta
 * 
 * @param {*} metodo --> get , post
 * @param {*} link --> /nome_server
 * @param {*} parametri --> par1=par1_value&par2=par2_value
 * @param {*} type --> 1, 2, 3... dipende dal numero di richesta
 */
function richiestaAJAX(metodo, link, parametri, type){
    let xhr = new XMLHttpRequest();

    if(xhr) {

        xhr.onreadystatechange = function() {
            callback(xhr, type);
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
 
/**
 * TEMPLATE indirizzamento gestione risposta AJAX
 * 
 * @param {*} xhr 
 * @param {*} type --> 1, 2, 3... dipende dal numero di richesta
 */
function callback(xhr, type) {
	if ( xhr.readyState === 2 ) {
        $("#ajax_message").text("Richiesta inviata...")
    } else if ( xhr.readyState === 3 ) {
        $("#ajax_message").text("Attesa risposta...")
    } else if ( xhr.readyState === 4 ) {
        if ( xhr.status === 200 ) {
            gestisciRispostaAJAX(xhr.responseText, type);
        } else {
            $("#ajax_message").text("Impossibile eseguire l'operazione.")
        }
    }
}