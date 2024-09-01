let matrix;

$(document).on({
    ready : function() {
        matrix = []
        for(let i = 0; i < 6; i++) {
            matrix[i] = [];
            $("table").append("<tr id='row_" + i + "'></tr>");
            for(let j = 0; j < 6; j++) {
                $("#row_" + i).append("<td><input type='text' id='" + i + "_" + j + "'></td>");
                matrix[i][j] = "";
            }
        }
    }
})

let results;
let xhr;
$(document).on("change", "input", function() {
    let val = $(this).val();
    if(isNaN(val) || !Number.isInteger(Number(val)) || val == 0) {
        alert("Inserire solo numeri interi diversi da 0!");
        $(this).val("");
    } else {
        let id = $(this).prop("id").split("_");
        let row = id[0];
        let col = id[1];

        matrix[row][col] = val;

        let complete = true;
        for(let i = 0; i < 6 && complete; i++) {
            for(let j = 0; j < 6 && complete; j++) {
                if(matrix[i][j] == "") {
                    complete = false;
                }
            }
        }

        //Se la matrice è completa si manda la richiesta
        if(complete) {
            let submatrix = [];
            for(let i = 0; i < 6; i++) {
                submatrix[i] = [];
                for(let j = 0; j < 6; j++) {
                    submatrix[i][j] =  j < 6 - i ? matrix[i][j] : 0;
                }
            }
            //Funziona - sottomatrice 1 fino alla diagonale
            
            let submatrix2 = [];
            submatrix2[0] = [];
            for(let i = 0; i < 6; i++) {
                submatrix2[0][i] = 0;
            }
            for(let i = 1; i < 6; i++) {
                submatrix2[i] = [];
                for(let j = 5; j >= 0; j--) {
                    submatrix2[i][j] =  j >= 6 - i ? matrix[i][j] : 0;
                }
            }

            let request = {
                submatrix_1 : submatrix,
                submatrix_2 : submatrix2
            }

            results = [-1, -1];

            xhr = new Array(2);
            for(let i = 0; i < 2; i++) {
                xhr[i] = new XMLHttpRequest();

                xhr[i].onreadystatechange = function() {callback(xhr[i], i)}

                try {
                    xhr[i].open("post", "service", true);
                } catch(e) {
                    alert(e);
                }

                xhr[i].setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

                request.index = i;
                let arguments = "request=" + JSON.stringify(request);
                xhr[i].send(arguments);
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

            if(result.message != "") {
                xhr[0].abort();
                xhr[1].abort();
                alert("Richiesta terminata dall'amministratore!");
                return;
            }
            
            results[i] = result.result;
            console.log(results[0] + " " + results[1]);

            if(results[0] != -1 && results[1] != -1) {
                alert(results[0] && results[1] ? "MATRICE SIMMETRICA" : "MATRICE NON SIMMETRICA");
            }
        } else {
            $("#ajax").text("Errore di caricamento della risposta")
        }
    }
}