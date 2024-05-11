$(document).on("click", "button", function() {
    let article = $(this).prop("id");

    let xhr = new XMLHttpRequest();
    
        if(xhr) {
            xhr.onreadystatechange = function() {
                releaseResponse(xhr)
            }
            try {
                xhr.open("get", "release?article=" + article, true);
            } catch(e) {
                alert(e);                    
                return;
            }
    
            xhr.send(null);
        } else {
        
        }
    }
)
    
function releaseResponse(xhr) {
    if(xhr.readyState === 2) {
        $("#start-container").html("Richiesta inviata...");
    } else if(xhr.readyState === 3) {
        $("#start-container").html("Attesa risposta...");
    } else if(xhr.readyState === 4) {
        if(xhr.status === 200) {
            let response = JSON.parse(xhr.responseText);
            if(response.type != "error") {
                alert("PERMESSO DI SCRITTURA NEGATO");
                window.location.reload();
            }
        } else {
            $("#start-container").html("Impossibile eseguire la richiesta");
        }
    }
}