const socket = new WebSocket("ws://localhost:8080/26_01_2022_es2/actions");
let matrix;
let somma1=0;
let somma2=0;
let controllo=false;

function send( data) {
    var json = JSON.stringify(data);
    socket.send(json);
}


socket.onmessage =  function (event){
	
	var message = JSON.parse(event.data);
	
	console.log(message.valore);
	
	matrix[message.row][message.col] = message.valore;
	
	$("#" + message.row + "_" + message.col).val(""+message.valore);
	
	console.log(message.message);
	
	if(message.message == "piena"){
		let xhr = new Array(2);
		let matrice = "";
		
		for(let i=0; i<3; i++){
			for(let j=0; j<3; j++){
				matrice += matrix[i][j] +",";
			}
		}
		
		console.log(matrice);
		
		for(let i=0; i<2; i++){
			xhr[i] = new XMLHttpRequest();
			
			if(xhr[i]) {

	            xhr[i].onreadystatechange = function() {
	                callback(xhr[i]);
	            }

	            try {
	                xhr[i].open("get", "service?matrice=" + matrice, true);
	            }
	            catch(e) {
	                alert(e);
	            }

	            xhr[i].send(null);

	        } else {
	            $("#ajax_message").text("Impossibile eseguire l'operazione. Browser troppo vecchio 1!")
	        }
		}

        
	}
	
}

let arrivato=false;

function callback(xhr) {
	if ( xhr.readyState === 2 ) {
        $("#ajax_message").text("Richiesta inviata...")
    } else if ( xhr.readyState === 3 ) {
        $("#ajax_message").text("Attesa risposta...")
    } else if ( xhr.readyState === 4 ) {
        if ( xhr.status === 200 ) {
            $("#ajax_message").text("")
            console.log(xhr.responseText);
            
            if(!arrivato){
            	//siamo al primo arrivo del controllo
            	controllo = JSON.parse(xhr.responseText).esito;
            	console.log(controllo);
            	
            	if(controllo)
            		somma1 = JSON.parse(xhr.responseText).somma;
            	
            	arrivato=true;
            }
            else{
            	if(controllo){
            		controllo = JSON.parse(xhr.responseText).esito;
                	console.log(controllo);
                	
                	if(controllo){
                		somma2 = JSON.parse(xhr.responseText).somma;
                		if(somma1==somma2){
                			alert("CONGRATULAZIONI, HAI CREATO UN QUADRATO MAGICO");
                			alert("la tua somma per ogni riga, colonna e diagonale vale " + somma1);
                		}
                		else
                			alert("Non è un quadrato magico");
                	}
                	else
                		alert("Non è un quadrato magico");
            	}
            	else{
            		//nel caso il primo controllo sia stato false posso dare già l'esito negativo al client
            		alert("Non è un quadrato magico");
            	}
            }

        } else {
            $("#ajax_message").text("Impossibile eseguire l'operazione. Browser troppo vecchio 2!")
        }
    } else {
        $("#ajax_message").text("Impossibile eseguire l'operazione.")
    }
}

//-----------------------------------------//


$(document).on({
    ready : function() {
        for(let i=0; i<3; i++){
            $("table").append("<tr id='row_"+ i +"'></tr>");
            for(let j=0; j<3; j++){
                $("#row_" + i).append("<td><input type='text' id='"+ i + "_" + j +"'></td>");
            }
        }
        
        matrix=new Array(3);
        for(let i=0; i<3; i++){
            matrix[i] = new Array(3);
        }
        
        for(let i=0; i<3; i++){
        	for(let j=0; j<3; j++){
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

    if(matrix[row][col] != ""){
        alert("Non è possibile modificare questa cella perchè gia piena");
        console.log(matrix);
        console.log(row);
        console.log(col);
        
        $(this).val("" + matrix[row][col]);
        return;
    }
    else{
        if(isNaN(val) || parseInt(val)<= 0 ){
            alert("Non è possibile accettare questo valore");
            $(this).val("");
            return;
        }
        else{
        	let no = false;
        	
        	for(let i=0; i<3; i++){
        		for(let j=0; j<3; j++){
        			if(matrix[i][j] == val)
        				no = true;
        		}
        	}
            if(no){
                alert("Non è possibile accettare questo valore, perchè gia presente");
                $(this).val("");
                return;
            }
            else{
                matrix[row][col] = val;
                var Request = {};
                Request.row=row;
                Request.col=col,
                Request.valore=val;
                

                send(Request);
            }
        }
    }
})