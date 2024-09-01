$("#text").on({
    change : function() {
        let text = $(this).val();
        let admittedChars = "abcdefghijklmnopqrstuvwxyz$ ";
        admittedChars += admittedChars.toUpperCase();

        let lastChar = text.charAt(text.length -1);
        console.log(lastChar);
        
        if(!admittedChars.includes(lastChar) ){
        	alert("Sono consentiti solo caratteri alfabetici!");
            $(this).val(text.substring(0, text.length-1));
        }
        
        if(lastChar == "$" || text.length == 5000) {
            let xhr = new XMLHttpRequest();
            if(xhr) {
                $("#ajax").show();
                xhr.onreadystatechange = function() {
                    callback(xhr);
                }

                try {
                    xhr.open("get", "service?text=" + text, true);
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
            
            if(result.message=="Success."){
                $("#text").val(result.text+"");
                $("#counter").val(result.counter+"");
            }
        } else {
            $("#ajax").text("Impossibile eseguire la richiesta.");
        }
    }
}