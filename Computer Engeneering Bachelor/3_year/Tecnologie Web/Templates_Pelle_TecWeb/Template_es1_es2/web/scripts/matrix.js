//============================================================================================
/* CREAZIONE Matrice */

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

//============================================================================================
/* GESTIONE Matrice */
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
        	// Richiese AJAX multiple
        }
        
        
    }
})