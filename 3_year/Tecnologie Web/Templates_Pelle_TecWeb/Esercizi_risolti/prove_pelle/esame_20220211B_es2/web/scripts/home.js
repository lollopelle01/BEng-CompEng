//============================================================================================

/* GESTIONE AJAX */

/**
 * TEMPLATE: gestione della risposta
 * 
 * @param {*} responseText --> risposta da gestire
 * @param {*} type --> 1, 2, 3... dipende dal numero di richesta
 */
function gestisciRispostaAJAX(responseText, type){
    // Traduco JSON in testo
    var response = JSON.parser(responseText);

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

        case 3 : 
        { // risposta 3
            risp3 = response;
        }
        break;
    }

    if(risp1!=null && risp2!=null && risp3!=null){ // analizziamo le risposta
        if(risp1.flag && risp2.flag && risp3.flag){ // tutte true
            console.log("Da /service: somma righe è " + risp1.somma)
            console.log("Da /service: somma colonne è " + risp2.somma)
            console.log("Da /service: somma diagonali è " + risp3.somma)

            alert("La matrice è un cubo magico");
        }
        else{ // anche solo una false
            alert("La matrice nonè sun cubo magico");      
        }
    }
    
    // Inserisco il risultato nel tag
    // $("ajax_message").val(response);
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

//============================================================================================

/* GESTIONE JAVASCRIPT */

let matrix;
let risp1, risp2, risp3;

$(document).on({
    ready : function() {
        for(let i=0; i<5; i++){
            $("#tabella").append("<tr id='row_"+ i +"'></tr>");
            for(let j=0; j<5; j++){
                $("#row_" + i).append("<td><input type='text' id='"+ i + "_" + j +"'></td>");
            }
        }
        
        matrix=new Array(5);
        for(let i=0; i<5; i++){
            matrix[i] = new Array(5);
        }
        
        for(let i=0; i<5; i++){
        	for(let j=0; j<5; j++){
        		matrix[i][j] = "";
        	}
        }
    }
})


//controlliamo client side che non ci siano numeri ripetuti
$(document).on("change", "input", function(){
    let cell = $(this).prop("id").split("_");
    let row = cell[0];
    let col = cell[1];

    let val = $(this).val();

    if(isNaN(val) || parseInt(val)<= 0 ){
    	alert("Non è possibile accettare questo valore");
        $(this).val("");
        return;
    }
    else{
        let no = false;
        	
        for(let i=0; i<5; i++){
        	for(let j=0; j<5; j++){
        		if(matrix[i][j] == val)
        			no = true;
        	}
        }
        if(no){
        	alert("Non è possibile accettare questo valore, perchè gia presente");
            $(this).val("");
            return;
        }
       
        matrix[row][col] = val;
        
        //controllo se matrice è piena//
        let piena = 0;
        
        for(let i=0; i<5; i++){
        	for(let j=0; j<5; j++){
        		if(matrix[i][j] != "")
        			piena++;
        	}
        }
        if(piena == 25){
			
        	//servlet controlla le righe
			richiestaAJAX("get", "/service", "matrix="+matrix, 1);
			
			//jsp controlla le colonne//
			richiestaAJAX("get", "/service.jsp", "matrix="+matrix, 2);
			
			//servlet 2 controlla le diagonali
			richiestaAJAX("get", "/service2", "matrix="+matrix, 3);
			
        }
        
        
    }
})