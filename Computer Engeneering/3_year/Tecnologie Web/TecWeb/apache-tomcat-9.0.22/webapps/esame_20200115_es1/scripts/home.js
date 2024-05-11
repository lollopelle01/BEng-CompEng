let counter = 0;

$("#text").on({
    keyup : function() {
        let text = $(this).val();

        if(text == "") {
            return;
        }

        let lastChar = text.charAt(text.length - 1);

        //nel caso in cui l'ultimo carattere non sia alfanumerico e non sia neanche lo spazio:
        if(!lastChar.match(/[a-z]/g) && lastChar != " ") {
            alert("Carattere numerico inserito! Si prega di inserire solo caratteri alfabetici!");
            $(this).val(text.substring(0, text.length-1)); //resetto l'ultimo carattere

            return;
        }

        counter = text.length;
        console.log(counter);

        if(counter >= 100) {
            let xhr = new XMLHttpRequest();

            if(xhr) {

                xhr.onreadystatechange = function() {
                    callback(xhr);
                }

                try {
                    xhr.open("get", "service?text=" + text + "&counter=0", true);
                }
                catch(e) {
                    alert(e);
                }

                xhr.send(null);

            } else {
                $("#ajax_message").text("Impossibile eseguire l'operazione. Browser troppo vecchio!")
            }
        }
    }
})

//in questa funzione dobbiamo eliminare un carattere random dal testo in modo client-side, per poi rmandare un altra volta la richiesta alla servlet
function callback(xhr) {
	if ( xhr.readyState === 2 ) {
        $("#ajax_message").text("Richiesta inviata...")
    } else if ( xhr.readyState === 3 ) {
        $("#ajax_message").text("Attesa risposta...")
    } else if ( xhr.readyState === 4 ) {
        if ( xhr.status === 200 ) {
            $("#ajax_message").text("")
            console.log(xhr.responseText);

            let text = JSON.parse(xhr.responseText).text;

            let chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            let randomChar = chars.charAt(Math.floor(Math.random() * chars.length));
            let counter = 0;

            //conto il numero di occorrenze del carattere 
            for(let i = 0; i < text.length; i++) {
                if(text.charAt(i) == randomChar){
                    counter++;
                }
            }

            console.log(randomChar);
            text = text.replaceAll(randomChar, "");
            console.log(text)
            //ricordiamo di inserire il nuovo testo nella textarea per mandare la nuova richiesta 
            $("#text").val(text);

            let xhr2 = new XMLHttpRequest();

            if(xhr2) {

                xhr2.onreadystatechange = function() {
                    callback_finale(xhr2);
                }

                try {
                    xhr2.open("get", "service?text=" + text + "&counter=" + counter, true);
                } catch(e) {
                    // Exceptions are raised when trying to access cross-domain URIs 
                    alert(e);
                }
            
                // invio richiesta
                xhr2.send(null);
            } else {
                $("#ajax_message").text("Impossibile eseguire l'operazione. Browser troppo vecchio!")
            }
        } else {
            $("#ajax_message").text("Impossibile eseguire l'operazione.")
        }
    }
}

//ultima funzione di callback, arrivati qui abbiamo completato le 5 richieste di eliminazione del carattere, possiamo mostrare il risultato 
function callback_finale(xhr) {
	if ( xhr.readyState === 2 ) {
        $("#ajax_message").text("Richiesta inviata...")
    } else if ( xhr.readyState === 3 ) {
        $("#ajax_message").text("Attesa risposta...")
    } else if ( xhr.readyState === 4 ) {
        if ( xhr.status === 200 ) {
            $("#ajax_message").text("")
            console.log(xhr.responseText);
            $("#text").val(JSON.parse(xhr.responseText).text);

        } else {
            $("#ajax_message").text("Impossibile eseguire l'operazione. Browser troppo vecchio!")
        }
    } else {
        $("#ajax_message").text("Impossibile eseguire l'operazione.")
    }
}