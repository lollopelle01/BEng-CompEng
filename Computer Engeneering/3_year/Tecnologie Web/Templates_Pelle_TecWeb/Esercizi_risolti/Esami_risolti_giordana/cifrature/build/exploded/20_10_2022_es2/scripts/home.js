let xhr_vocale, xhr_consonante;

$("#text").on({
    keyup : function(e) {
        if (e.keyCode == 16) return;
        let text = $(this).val();
        let chars = "abcdefghijklmnopqrstuvwxyz%";
        let vocali = "aeiou";
        let consonanti = "bcdfghjklmnpqrstvwxyz";
        let esiste_vocale = false;
        let esiste_consonante = false;
        
        //controllo ci sia almeno una vocale ed una consonante
        for(let i = 0; i < text.length; i++) {
            if(vocali.includes(text.charAt(i))){
                esiste_vocale = true;
            }
        }
        
        for(let i = 0; i < text.length; i++) {
            if(consonanti.includes(text.charAt(i))){
                esiste_consonante = true;
            }
        }

        //controllo del testo
        for(let i = 0; i < text.length; i++) {
            if(!chars.includes(text.charAt(i))){
                $(this).val(text.slice(0, -1));
            }
        }

        //controllo della dimensione
        if(text.length > 20) {
            $(this).val(text.substring(0, 21));
            return;
        }

        if(text.charAt(text.length - 1) == "%" && text.length >= 6 && esiste_vocale && esiste_consonante) {
            text = text.slice(0, -1);
            if(text == "") {
                return;
            }

            console.log("Procedo con le richieste");

            xhr_vocale = new XMLHttpRequest();
            xhr_consonante = new XMLHttpRequest();
            if(xhr_vocale) {
                xhr_vocale.onreadystatechange = function() {callback(xhr_vocale, 1)}

                try {
                    xhr_vocale.open("post", "service1", true);
                } catch(e) {
                    alert(e);
                }

                xhr_vocale.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

                let arguments = "text=" + text;

                xhr_vocale.send(arguments);
            } else {
                $("#ajax").text("Impossibile eseguire la richiesta");
            }

            if(xhr_consonante) {
                xhr_consonante.onreadystatechange = function() {callback(xhr_consonante, 2)}

                try {
                    xhr_consonante.open("post", "service2", true);
                } catch(e) {
                    alert(e);
                }

                xhr_consonante.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

                let arguments = "text=" + text;

                xhr_consonante.send(arguments);
            } else {
                $("#ajax").text("Impossibile eseguire la richiesta");
            }
        }
    }
})

function callback(xhr, i) {
    if(xhr.readyState === 2) {
        $("#ajax").text("Invio richiesta...")
    } else if(xhr.readyState === 3) {
        $("#ajax").text("Attesa risposta...")
    } else if(xhr.readyState === 4) {
        if(xhr.status === 200) {
            let result = JSON.parse(xhr.responseText);
            $("#result-" + i).html("<ul id='ul-" + i + "'></ul>");

            for(let anagramma of result.anagrammi) {
                $("#ul-" + i).append("<li>" + anagramma + "</li>")
            }

            $("#result-" + i).append("<button class='correct-btn' id='correct-" + i + "'>CORRETTA</button>");
        } else {
            $("Errore di caricamento della risposta")
        }
    }
}

$(document).on("click", "#correct-1", function() {
    alert("Richiesta degli anagrammi che iniziano per consonante abortita!");
    xhr_consonante.abort();
})

$(document).on("click", "#correct-2", function() {
    alert("Richiesta degli anagrammi che iniziano per vocale abortita!");
    xhr_vocale.abort();
})
