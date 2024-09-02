let text;
let target = "";

$("#target").on({
    keyup : function() {
        target = $(this).val();
        let admittedChars = "abcdefghijklmnopqrstuvwxyz";
        admittedChars += admittedChars.toUpperCase();

        for(let i = 0; i < target.length; i++) {
            if(!admittedChars.includes(target.charAt(i))) {
                alert("Sono consentiti solo caratteri alfabetici!");
                $(this).val("");
            }
        }
    }
})


$("#fname").on({
    change : function() {
        text = $(this).val();
        
        let admittedChars2 = "abcdefghijklmnopqrstuvwxyz.$";
        admittedChars2 += admittedChars2.toUpperCase();

        for(let i = 0; i < text.length; i++) {
            if(!admittedChars2.includes(text.charAt(i))) {
                alert("Sono consentiti solo caratteri alfabetici!");
                $(this).val("");
                return;
            }
        }
        
        let lastChar = text.charAt(text.length-1);
        console.log("Text ultimo carattere: " + lastChar);
        text = text.substring(0, text.length-1);
        	
        if(text.length > 1 && target.length >= 1 && lastChar=="$" ){
        	
        	
        		console.log("Invio testo: " + text);
        		console.log("Invio target: " + target);
        		
            	let xhr = new XMLHttpRequest();
                if(xhr) {
                    $("#ajax").show();
                    xhr.onreadystatechange = function() {
                        callback(xhr);
                    }

                    try {
                        xhr.open("get", "service?fname=" + text + "&target=" + target, true);
                    } catch(e) {
                        alert(e);
                        return;
                    }

                    xhr.send(null);
                } else {
                    $("#ajax").text("Impossibile eseguire la richiesta, browser troppo vecchio");
                }
        		
       }
        
    }//function
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
            if(result.message == "File not found") {
                alert("File non esistente!");
            } else {
            	if(result.message == "Error on JSP"){
                    alert("errore nella jsp!");
            	}
            	else{
                    alert("Occorrenze della stringa target nel testo: " + result.counter);
                    alert("Testo modificato: " + result.text);
                    
                    $("#testo").text(result.text);
                    $("#occorrenze").text(result.counter);
            	}
            }
        } else {
            $("#ajax").text("Impossibile eseguire la richiesta.");
        }
    }
}