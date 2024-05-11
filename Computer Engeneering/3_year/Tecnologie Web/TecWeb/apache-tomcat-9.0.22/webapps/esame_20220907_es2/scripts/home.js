// ArrayList<Tennista>
let database = [];
var tennista = "";
var nome = "";

$("#invio").on({
    click : function() {
        nome = $("#tennista").val();

        if(nome == "") {
            return;
        }
        
        // Controllo di pre-fetching, analizzo il database e controllo il nome del tennista
        if (database != null && database != ""){ 
        	
        	for (var i=0; i<database.length; i++){
        		if(database[i].nome == nome){ // match effettuato --> risposta istantanea
        			
        			// Estraggo il risultato
            		tennista = database[i];
            		
            		// Estraiamo risposta testuale
            		var stringa = 	"Ranking: " + tennista.rank_atp + '<br>' +
            						"Titoli vinti: " + tennista.titoli_vinti + '<br>' +
            						"Partite vinte: " + tennista.partite_vinte + '<br>' +
            						"Partite perse: " + tennista.partite_perse;
            		
            		// Scriviamo risposta
            		$("#ajax_message").html('<p>' + stringa + '</p>');
                	return;
        		}
        	}
        }
        
        // Non avevamo il tennista, lo chiediamo al server
        let xhr = new XMLHttpRequest();

        if(xhr) {

            xhr.onreadystatechange = function() {
                callback(xhr);
            }

            try {
                xhr.open("get", "service?tennista=" + nome, true);
            }
            catch(e) {
                alert(e);
            }

            xhr.send(null);

        } else {
            $("#ajax_message").text("Impossibile eseguire l'operazione. Browser troppo vecchio!")
        }
    }
})

function gestioneRisposta(risposta_json){
	
	console.log("Risposta del server: " + risposta_json);
	
	if(JSON.parse(risposta_json) == -1){alert("Non partecipa agli US Open 2022"); return;}

	// Ricava il DB
	database = risposta_json;//JSON.parse(risposta_json);
	console.log("Ho parsato il database: " + database);
	
	// Isolo il tennista
	for(var i=0; i<database.length; i++){
		if(database[i].nome == nome){ tennista = database[i];}
	}
	console.log("Ho trovato il tennista: " + database[0].nome);
	
	// Estraiamo risposta testuale
	var stringa = 	"Ranking: " + tennista.rank_atp + '<br>' +
					"Titoli vinti: " + tennista.titoli_vinti + '<br>' +
					"Partite vinte: " + tennista.partite_vinte + '<br>' +
					"Partite perse: " + tennista.partite_perse;
	
	// Scriviamo risposta
	$("#ajax_message").html('<p>' + stringa + '</p>');
}

//in questa funzione dobbiamo eliminare un carattere random dal testo in modo client-side, per poi rmandare un altra volta la richiesta alla servlet
function callback(xhr) {
	if ( xhr.readyState === 2 ) {
        $("#ajax_message").text("Richiesta inviata...")
    } else if ( xhr.readyState === 3 ) {
        $("#ajax_message").text("Attesa risposta...")
    } else if ( xhr.readyState === 4 ) {
        if ( xhr.status === 200 ) {
        	
        	// Risposta ricevuta --> ArrayList<Tennista>
        	gestioneRisposta(xhr.responseText);
        	 
        } else {
            $("#ajax_message").text("Impossibile eseguire l'operazione.")
        }
    }
}