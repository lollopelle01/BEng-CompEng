let players = [];

$("#search-btn").on({
    click : function() {
        let surname = $("#surname").val();
        if(surname == "" || surname.length > 256) {
            return;
        }

        let player;
        players.map(p => {
            if(p.surname == surname) {
                player = p;
            }
        })

        if(player == null) {
            console.log("Richiesta al server");
            let xhr = new XMLHttpRequest();
            if(xhr) {
                xhr.onreadystatechange = function() {
                    callback(xhr);
                }

                try {
                    xhr.open("get", "service?surname=" + surname, true);
                } catch(e) {
                    alert(e);
                }

                xhr.send(null);
            } else {
                $("#ajax").text("Impossibile procedere con la richiesta. Browser troppo vecchio")
            }
        } else {
            console.log("Giocatore prefetched");

            $("#player-info").html("Cognome giocatore: " + player.surname + "<br>" + 
                                        "Ranking: " + player.ranking + "<br>" + 
                                        "Titoli: " + player.titles + "<br>" + 
                                        "Vinte: " + player.win + "<br>" + 
                                        "Perse: " + player.lost + "<br>"
            );
        }
    }
})

function callback(xhr) {
    if(xhr.readyState === 2) {
        $("#ajax").text("Richiesta inviata...")
    } else if(xhr.readyState === 3) {
        $("#ajax").text("Ricezione risposta...")
    } else if(xhr.readyState === 4) {
        if(xhr.status === 200) {
            $("#ajax").text("");

            let response = JSON.parse(xhr.responseText);

            if(response.message == "Player found") {
                $("#player-info").html("Cognome giocatore: " + response.player.surname + "<br>" + 
                                        "Ranking: " + response.player.ranking + "<br>" + 
                                        "Titoli: " + response.player.titles + "<br>" + 
                                        "Vinte: " + response.player.win + "<br>" + 
                                        "Perse: " + response.player.lost + "<br>"
                );

                players = response.prefetched;
                console.log(players);
            } else {
                $("#player-info").text(response.message);
            }
        } else {
            $("#ajax").text("Errore nell'esecuzione della richiesta");
        }
    }
}