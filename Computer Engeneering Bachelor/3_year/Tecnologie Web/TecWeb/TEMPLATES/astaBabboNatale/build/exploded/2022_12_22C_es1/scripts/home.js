
function callback(xhr) {
    if(xhr.readyState === 2) {
        $("#ajax").text("Richiesta inviata...");
    } else if(xhr.readyState === 3) {
        $("#ajax").text("Ricezione risposta...");
    } else if(xhr.readyState === 4) {
        if(xhr.status === 200) {
            $("#ajax").hide();
            console.log(xhr.responseText);
            let result = JSON.parse(xhr.responseText);
            if(result.message == "Invalide parameter") {
                alert("Errore nell'invio della carta!");
            } else {
                if(result.message == "Vendita  già in corso"){
                	alert("C'è gia una vendita di una carta!");
                }
                else{
                	//ricezione risultato
                	if(result.message == "ok"){
                		alert("iniziata la vendita per la carta "+ result.carta);
                	}
                }
            }
        } else {
            $("#ajax").text("Impossibile eseguire la richiesta.");
        }
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
