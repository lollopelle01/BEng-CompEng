$("#btn-conto_tavolo").on({
	click: function(){
		
		let xhr1 = new XMLHttpRequest();
        if(xhr1) {
            $("#ajax1").show();
            xhr1.onreadystatechange = function() {
                callback1(xhr1);
            }

            try {
                xhr1.open("get", "service?op=1", true);
            } catch(e) {
                alert(e);
                return;
            }

            xhr1.send(null);
        } else {
            $("#ajax1").text("Impossibile eseguire la richiesta, browser troppo vecchio");
        }
		
	}
})

$("#btn-conto_personale").on({
	click: function(){
		
		let xhr2 = new XMLHttpRequest();
        if(xhr2) {
            $("#ajax2").show();
            xhr2.onreadystatechange = function() {
                callback2(xhr2);
            }

            try {
                xhr2.open("get", "service?op=2", true);
            } catch(e) {
                alert(e);
                return;
            }

            xhr2.send(null);
        } else {
            $("#ajax2").text("Impossibile eseguire la richiesta, browser troppo vecchio");
        }
		
	}
})


function callback1(xhr) {
    if(xhr.readyState === 2) {
        $("#ajax1").text("Richiesta inviata...");
    } else if(xhr.readyState === 3) {
        $("#ajax1").text("Ricezione risposta...");
    } else if(xhr.readyState === 4) {
        if(xhr.status === 200) {
            $("#ajax1").hide();
            console.log(xhr.responseText);
            let result = JSON.parse(xhr.responseText);
            
            if(result.message == "operazione non riconosciuta") {
                alert(result.message);
            } else {
                let totale = result.totale;
                $("#conto_tavolo").val(totale);
            }
        } else {
            $("#ajax1").text("Impossibile eseguire la richiesta.");
        }
    }
}

function callback2(xhr) {
    if(xhr.readyState === 2) {
        $("#ajax2").text("Richiesta inviata...");
    } else if(xhr.readyState === 3) {
        $("#ajax2").text("Ricezione risposta...");
    } else if(xhr.readyState === 4) {
        if(xhr.status === 200) {
            $("#ajax2").hide();
            console.log(xhr.responseText);
            let result = JSON.parse(xhr.responseText);
            
            if(result.message == "operazione non riconosciuta") {
                alert(result.message);
            } else {
                let totale = result.totale;
                $("#conto_personale").val(totale);
            }
        } else {
            $("#ajax2").text("Impossibile eseguire la richiesta.");
        }
    }
}