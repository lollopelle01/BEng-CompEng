let campo = -1;
let giorno = -1;
let orario = -1;

$("#campo").on({
	change: function(){
		
		campo = $(this).val();
		console.log(campo);
		
		if(giorno!=-1 && orario!=-1){
			let xhr = new XMLHttpRequest();
	        if(xhr) {
	            $("#ajax").show();
	            xhr.onreadystatechange = function() {
	                callback(xhr);
	            }

				$("#tabella_soluzioni").hide();
	            try {
	                xhr.open("get", "service?campo=" + campo +"&giorno=" + giorno + "&orario=" + orario, true);
	            } catch(e) {
	                alert(e);
	                return;
	            }

	            xhr.send(null);
	        } else {
	            $("#ajax").text("Impossibile eseguire la richiesta, browser troppo vecchio, campo");
	        }
		}
		
	}
})


$("#giorno").on({
	change: function(){
		
		giorno = $(this).val();
		console.log(giorno);
		
		if(campo!= -1 && orario!= -1){
			let xhr = new XMLHttpRequest();
	        if(xhr) {
	            $("#ajax").show();
	            xhr.onreadystatechange = function() {
	                callback(xhr);
	            }
				
				$("#tabella_soluzioni").hide();
	            try {
	                xhr.open("get", "service?campo=" + campo +"&giorno=" + giorno + "&orario=" + orario, true);
	            } catch(e) {
	                alert(e);
	                return;
	            }

	            xhr.send(null);
	        } else {
	            $("#ajax").text("Impossibile eseguire la richiesta, browser troppo vecchio, campo");
	        }
		}
		
	}
})


$("#orario").on({
	change: function(){
		
		orario = $(this).val();
		console.log(orario);
		
		if(campo!= -1 && giorno != -1){
			let xhr = new XMLHttpRequest();
	        if(xhr) {
	            $("#ajax").show();
	            xhr.onreadystatechange = function() {
	                callback(xhr);
	            }

				$("#tabella_soluzioni").hide();
	            try {
	                xhr.open("get", "service?campo=" + campo +"&giorno=" + giorno + "&orario=" + orario, true);
	            } catch(e) {
	                alert(e);
	                return;
	            }

	            xhr.send(null);
	        } else {
	            $("#ajax").text("Impossibile eseguire la richiesta, browser troppo vecchio, campo");
	        }
		}
		
	}
})

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
            //--------------------------------------------------//
            if(result.message == "ERROR") {
                alert("Prenotazione non possibile perchè campo già ocupato, ecco 5 alternative");
                //aggiungi alla tabella
                
                $("#tabella_alternative").append("<tr><th>CAMPO</th><th>Giorno</th><th>Ora</th></tr>");
                
                let resp = result.soluzioni;
                console.log(resp);
                
                for(let i=0; i<resp.length; i++)
                $("#tabella_alternative").append("<tr><td>" + resp[i].campo + "</th><td>"+ resp[i].giorno +"</td><td>"+ resp[i].orario +"</td></tr>");
            } else {
				if(result.message=="temporanea"){
					alert("prenotazione avvenuta con successo, si prega di attendere il 2 partecipante");
				}
				else{
					alert("prenotazione avvenuta con successo, buona partita");
				}
            }
        } else {
            $("#ajax").text("Impossibile eseguire la richiesta.");
        }
    }
}
