//funzioni per l'asta della carta
$("#compra").on({
	click: function(){
		let offerta = $("#offerta").val();
		console.log(carta);
		
		if(isNaN(offerta) || Number.isInteger(offerta) || parseInt(offerta)<=0){
			$(this).val("");
			return;
		}else{
			let xhr = new XMLHttpRequest();
			
        	if(xhr) {
            	$("#ajax").show();
            	
            	xhr.onreadystatechange = function() {
                	callback(xhr);
            	}

            	try {
                	xhr.open("get", "service?offerta=" + offerta, true);
            	} catch(e) {
                	alert(e);
                	return;
            	}

            	xhr.send(null);
        	} else {
            	$("#ajax").text("Impossibile eseguire la richiesta, browser troppo vecchio");
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
            
            alert(result.message);
        } else {
            $("#ajax").text("Impossibile eseguire la richiesta.");
        }
    }
}