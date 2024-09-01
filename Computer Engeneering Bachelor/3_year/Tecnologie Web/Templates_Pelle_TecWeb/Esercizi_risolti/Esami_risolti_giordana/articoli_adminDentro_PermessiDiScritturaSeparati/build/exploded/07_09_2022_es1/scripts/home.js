$(document).on({
    ready : function() {
        $("#start-main").hide();
        $("#overlay").show();
    }
})

$("#fname").on({
    keyup : function(e) {
        if(e.shiftKey) {
            return;
        }
        let fname = $(this).val();
        if(fname.length > 65) {
            $(this).val(fname.substring(0, 64));
        } else if(fname.charAt(fname.length - 1) == "%") {
            fname = fname.replaceAll("%", "");
            let xhr = new XMLHttpRequest();
            if(xhr) {
                xhr.onreadystatechange = function() {
                    callback(xhr)
                }

                try {
                    xhr.open("post", "fileManager", true);
                } catch(e) {
                    alert(e);
                    return;
                }

                xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

                let arguments = "fname=" + fname;
                xhr.send(arguments)
            } else {
                $("#start-container").html("Impossibile eseguire la richiesta. Browser troppo vecchio.");
            }
        }
    }
})

let article;

function callback(xhr) {
    if(xhr.readyState === 2) {
        $("#start-container").html("Richiesta inviata...");
    } else if(xhr.readyState === 3) {
        $("#start-container").html("Attesa risposta...");
    } else if(xhr.readyState === 4) {
        if(xhr.status === 200) {
            $("#overlay").hide();
            $("main").show();

            let response = JSON.parse(xhr.responseText);
            article = response.name;
            $("#article").val(response.text);
        } else {
            $("#start-container").html("Impossibile eseguire la richiesta");
        }
    }
}

$("#request-btn").on({
    click : function() {
        let xhr = new XMLHttpRequest();
        if(xhr) {
            xhr.onreadystatechange = function() {
                accessResponse(xhr)
            }

            try {
                xhr.open("get", "access?article=" + article, true);
            } catch(e) {
                alert(e);
                return;
            }

            xhr.send(null);
        } else {
        
        }
    }
})

function accessResponse(xhr) {
    if(xhr.readyState === 2) {
        $("#start-container").html("Richiesta inviata...");
    } else if(xhr.readyState === 3) {
        $("#start-container").html("Attesa risposta...");
    } else if(xhr.readyState === 4) {
        if(xhr.status === 200) {
            let response = JSON.parse(xhr.responseText);
            if(response.type != "error") {
                $("#article").val(response.article.text);

                if(response.result) {
                    $("#release-btn").show();
                    $("#request-btn").hide();
                    $("#text").prop("disabled", false);
                    $("#append-btn").prop("disabled", false);
                } else {
                    $("#release-btn").hide();
                    $("#request-btn").show();
                    $("#text").prop("disabled", true);
                    $("#append-btn").prop("disabled", true);
                }
            }
        } else {
            $("#start-container").html("Impossibile eseguire la richiesta");
        }
    }
}

$("#release-btn").on({
    click : function() {
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
})

function releaseResponse(xhr) {
    if(xhr.readyState === 2) {
        $("#start-container").html("Richiesta inviata...");
    } else if(xhr.readyState === 3) {
        $("#start-container").html("Attesa risposta...");
    } else if(xhr.readyState === 4) {
        if(xhr.status === 200) {
            let response = JSON.parse(xhr.responseText);
            if(response.type != "error") {
                $("#article").val(response.article.text);

                $("#release-btn").hide();
                $("#request-btn").show();
                $("#text").prop("disabled", true);
                $("#append-btn").prop("disabled", true);
            }
        } else {
            $("#start-container").html("Impossibile eseguire la richiesta");
        }
    }
}

$("#append-btn").on({
    click : function() {
        let text = $("#text").val();

        let xhr = new XMLHttpRequest();
        if(xhr) {
            xhr.onreadystatechange = function() {
                appendCallback(xhr)
            }

            try {
                xhr.open("post", "append?article=" + article, true);
            } catch(e) {
                alert(e);
                return;
            }

            xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

            let arguments = "article=" + article + "&text=" + text;

            xhr.send(arguments);
        } else {
        
        }
    }
})

function appendCallback(xhr) {
    if(xhr.readyState === 2) {
        $("#start-container").html("Richiesta inviata...");
    } else if(xhr.readyState === 3) {
        $("#start-container").html("Attesa risposta...");
    } else if(xhr.readyState === 4) {
        if(xhr.status === 200) {
            let response = JSON.parse(xhr.responseText);
            if(response.result) {
                $("#article").val(response.article.text);
            } else {
                alert("Non hai i permessi di scrittura!");
                $("#release-btn").hide();
                $("#request-btn").show();
                $("#text").prop("disabled", true);
                $("#append-btn").prop("disabled", true);
            }
        } else {
            $("#start-container").html("Impossibile eseguire la richiesta");
        }
    }
}