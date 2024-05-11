let result1;
let result2;
let result3;

let somma1;
let somma2;
let somma3;

let arrivati = 0;

function callback1(xhr) {
	if ( xhr.readyState === 2 ) {
        $("#ajax_message").text("Richiesta inviata...")
    } else if ( xhr.readyState === 3 ) {
        $("#ajax_message").text("Attesa risposta...")
    } else if ( xhr.readyState === 4 ) {
        if ( xhr.status === 200 ) {
            $("#ajax_message").text("")
            console.log(xhr.responseText);
            let message = JSON.parse(xhr.responseText).message;
            
            if(message == "Richiesta interrotta dall'amministratore"){
            	alert(message);
            	console.log("Richiesta 1: " + message);
            }
            else{
            	result1 = JSON.parse(xhr.responseText).result;
            	console.log("arrivata risposta1: " + result3);
            	
                
                if(result1){
                	somma1 = JSON.parse(xhr.responseText).somma;
                	console.log("arrivata risposta1: " + somma1);
                }
                
                //controllo sugli altri risultati
                if(arrivati==3){
                	if(result1 && result2 && result3){
                		if(somma1 == somma2 && somma1 == somma3){
                			alert("CONGRATULAZIONI è UN QUADRATO MAGICO");
                		}
                		else
                			alert("NON è UN QUADRATO MAGICO");
                	}
                	else
            			alert("NON è UN QUADRATO MAGICO");
                }
                else
                	arrivati++;
            }
        } else {
            $("#ajax_message").text("Impossibile eseguire l'operazione. Browser troppo vecchio 1!")
        }
    } else {
        $("#ajax_message").text("Impossibile eseguire l'operazione.1")
    }
}

function callback2(xhr) {
	if ( xhr.readyState === 2 ) {
        $("#ajax_message2").text("Richiesta inviata...")
    } else if ( xhr.readyState === 3 ) {
        $("#ajax_message2").text("Attesa risposta...")
    } else if ( xhr.readyState === 4 ) {
        if ( xhr.status === 200 ) {
            $("#ajax_message2").text("")
            console.log(xhr.responseText);
            let message2 = JSON.parse(xhr.responseText).message;
            
            if(message2 == "Richiesta interrotta dall'amministratore"){
            	alert(message2);
            	console.log("Richiesta 2: " + message2);
            }
            else{
            	result2 = JSON.parse(xhr.responseText).result;
                console.log("arrivata risposta2: " + result2);
                if(result2){
                	somma2 = JSON.parse(xhr.responseText).somma;
                	console.log("arrivata risposta2: " + somma2);
                }
                
                //controllo sugli altri risultati
                if(arrivati==3){
                	if(result1 && result2 && result3){
                		if(somma1 == somma2 && somma1 == somma3){
                			alert("CONGRATULAZIONI è UN QUADRATO MAGICO");
                		}
                		else
                			alert("NON è UN QUADRATO MAGICO");
                	}
                	else
            			alert("NON è UN QUADRATO MAGICO");
                }
                else
                	arrivati++;
            }

        } else {
            $("#ajax_message2").text("Impossibile eseguire l'operazione. Browser troppo vecchio 2!")
        }
    } else {
        $("#ajax_message2").text("Impossibile eseguire l'operazione.2")
    }
}

function callback3(xhr) {
	if ( xhr.readyState === 2 ) {
        $("#ajax_message3").text("Richiesta inviata...")
    } else if ( xhr.readyState === 3 ) {
        $("#ajax_message3").text("Attesa risposta...")
    } else if ( xhr.readyState === 4 ) {
        if ( xhr.status === 200 ) {
            $("#ajax_message3").text("")
            console.log(xhr.responseText);
            let message3 = JSON.parse(xhr.responseText).message;
            
            if(message3 == "Richiesta interrotta dall'amministratore"){
            	alert(message3);
            	console.log("Richiesta 3: " + message3);
            }
            else{
            	result3 = JSON.parse(xhr.responseText).result;
            	console.log("arrivata risposta3: " + result3);
            	
                if(result3){
                	somma3 = JSON.parse(xhr.responseText).somma;
                	console.log("arrivata risposta3: " + somma3);
                }
                
                //controllo sugli altri risultati
                if(arrivati==3){
                	if(result1 && result2 && result3){
                		if(somma1 == somma2 && somma1 == somma3){
                			alert("CONGRATULAZIONI è UN QUADRATO MAGICO");
                		}
                		else
                			alert("NON è UN QUADRATO MAGICO");
                	}
                	else
            			alert("NON è UN QUADRATO MAGICO");
                }
                else
                	arrivati++;
            }
            

        } else {
            $("#ajax_message3").text("Impossibile eseguire l'operazione. Browser troppo vecchio 3!")
        }
    } else {
        $("#ajax_message3").text("Impossibile eseguire l'operazione.3")
    }
}

//---------------------------------------------------------------------------------------------------------//
let matrix;

$(document).on({
    ready : function() {
        for(let i=0; i<5; i++){
            $("#tabella").append("<tr id='row_"+ i +"'></tr>");
            for(let j=0; j<5; j++){
                $("#row_" + i).append("<td><input type='text' id='"+ i + "_" + j +"'></td>");
            }
        }
        
        matrix=new Array(5);
        for(let i=0; i<5; i++){
            matrix[i] = new Array(5);
        }
        
        for(let i=0; i<5; i++){
        	for(let j=0; j<5; j++){
        		matrix[i][j] = "";
        	}
        }
    }
})


//controlliamo client side che non ci siano numeri ripetuti
$(document).on("change", "input", function(){
    let cell = $(this).prop("id").split("_");
    let row = cell[0];
    let col = cell[1];

    let val = $(this).val();

    if(isNaN(val) || parseInt(val)<= 0 ){
    	alert("Non è possibile accettare questo valore");
        $(this).val("");
        return;
    }
    else{
        let no = false;
        	
        for(let i=0; i<5; i++){
        	for(let j=0; j<5; j++){
        		if(matrix[i][j] == val)
        			no = true;
        	}
        }
        if(no){
        	alert("Non è possibile accettare questo valore, perchè gia presente");
            $(this).val("");
            return;
        }
       
        matrix[row][col] = val;
        
        //controllo se matrice è piena//
        let piena = 0;
        
        for(let i=0; i<5; i++){
        	for(let j=0; j<5; j++){
        		if(matrix[i][j] != "")
        			piena++;
        	}
        }
        if(piena == 25){
        	let xhr1 = new XMLHttpRequest();
        	let xhr2 = new XMLHttpRequest();
        	let xhr3 = new XMLHttpRequest();
			
        	//servlet controlla le righe
			if(xhr1) {

	            xhr1.onreadystatechange = function() {
	                callback1(xhr1);
	            }

	            try {
	                xhr1.open("get", "service?matrice=" + matrix, true);
	            }
	            catch(e) {
	                alert(e);
	            }

	            xhr1.send(null);

	        } else {
	            $("#ajax_message").text("Impossibile eseguire l'operazione. Browser troppo vecchio 1!")
	        }
			
			//jsp controlla le colonne//
			if(xhr2) {

	            xhr2.onreadystatechange = function() {
	                callback2(xhr2);
	            }

	            try {
	                xhr2.open("get", "service.jsp?matrice=" + matrix, true);
	            }
	            catch(e) {
	                alert(e);
	            }

	            xhr2.send(null);

	        } else {
	            $("#ajax_message").text("Impossibile eseguire l'operazione. Browser troppo vecchio 1!")
	        }
			
			//servlet 2 controlla le diagonali
			if(xhr3) {

	            xhr3.onreadystatechange = function() {
	                callback3(xhr3);
	            }

	            try {
	                xhr3.open("get", "service2?matrice=" + matrix, true);
	            }
	            catch(e) {
	                alert(e);
	            }

	            xhr3.send(null);

	        } else {
	            $("#ajax_message").text("Impossibile eseguire l'operazione. Browser troppo vecchio 1!")
	        }
			
			
        }
        
        
    }
})