$("#num").on({
    change : function() {
        let num = $(this).val();

        console.log(num);
        
        if(isNaN(num) || Number.isInteger(num) || (parseInt(num)!=3 && parseInt(num)!= 4) ){
			alert("valore immesso non accetabile, ritenta");
			$(this).val("");
			return;
		}
		else{
			let xhr = new XMLHttpRequest();
            if(xhr) {
                $("#ajax").show();
                xhr.onreadystatechange = function() {
                    callback(xhr);
                }

                try {
                    xhr.open("get", "service?num=" + num, true);
                } catch(e) {
                    alert(e);
                    return;
                }

                xhr.send(null);
                console.log("Inviata nuova richiesta al server");
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
            
            let messaggi = result.message;
            let info = result.info;
            
            for(let i=0; i<info.length; i++){
				$("#tabella").append("<tr><td>"+ info[i].titolo +"</td><td>"+ info[i].dimensione +"</td><td>"+ info[i].formato +"</td><td>"+ info[i].contenuto +"</td></tr>");
			}
			
			for(let j=0; j<messaggi.lenth; j++){
				console.log(messaggi[j]);
			}
        } else {
            $("#ajax").text("Impossibile eseguire la richiesta.");
        }
    }
}