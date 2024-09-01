$("#fname").on({
    keyup : function() {
        let text = $(this).val();
        let admittedChars = "abcdefghijklmnopqrstuvwxyz. ";
        admittedChars += admittedChars.toUpperCase() + "0123456789";

        for(let i = 0; i < text.length; i++) {
            if(!admittedChars.includes(text.charAt(i))) {
                alert("Sono consentiti solo caratteri alfanumerici!");
                $(this).val("");
            }
        }

        if(text.charAt(text.length - 1) == " ") {
            let xhr = new XMLHttpRequest();
            if(xhr) {
                $("#ajax").show();
                xhr.onreadystatechange = function() {
                    callback(xhr);
                }

                try {
                    xhr.open("get", "service?fname=" + text, true);
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
            if(result.message == "File not found") {
                alert("File non esistente!");
            } else {
                alert("Caratteri maiuscoli: " + result.counter);
                alert("Testo modificato: " + result.text);
            }
        } else {
            $("#ajax").text("Impossibile eseguire la richiesta.");
        }
    }
}