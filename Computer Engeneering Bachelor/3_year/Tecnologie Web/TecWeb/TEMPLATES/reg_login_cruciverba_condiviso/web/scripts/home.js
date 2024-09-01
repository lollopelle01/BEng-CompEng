$("input").on({
	change: function(){
		let id= $(this).prop("id");
		console.log(id);
		let char = $(this).val();
		console.log(char);
		
		if(char.charAt(0) == ' ')
			char = char.substring(1, 2);
		
		let admittedChars = "abcdefghijklmnopqrstuvwxyz";
		admittedChars = admittedChars.toUpperCase();
		
		if(!admittedChars.includes(char) || char.length !=1){
			alert("Inserisci solo caratteri alfabetici maiuscoli, max dim = 1");
			$(this).val(" ");
		}
		else{
			let xhr = new XMLHttpRequest();
            if(xhr) {
                $("#ajax").show();
                xhr.onreadystatechange = function() {
                    callback(xhr);
                }

                try {
                    xhr.open("get", "service?id=" + id + "&char=" + char, true);
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
            
            if(result.message == "Invalide parameter") {
                alert("Errore nell'invio dei dati");
            } else {
            	if(result.message == "carattere aggiunto correttamente"){
            		console.log(result.message);
            	}
            	else
            		alert(result.message);
            	
            	if(result.message_colonna != "")
            		alert(result.message_colonna);
            	
            }
        } else {
            $("#ajax").text("Impossibile eseguire la richiesta.");
        }
    }
}