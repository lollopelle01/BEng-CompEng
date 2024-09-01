var m=0;   //righe
var n=0;   //colonne

//matrice rettangolare di dimensione 80x40 di numeri reali generati casualmente o letti da file lato client
function setJson() {
	cont=0;//var globale
	//inizializzo una matrice righexcolonne
	var righe=m;
	var colonne=n;

//	var num=document.getElementById("num").value;
	var num=2;
	if(num==null||num===undefined){
		alert("Non hai inserito il numero di richieste concorrenti")
		return
	}
	if(parseInt(num)!=1&&parseInt(num)!=2&&parseInt(num)!=4){
		alert("Non hai inserito un numero tra 1, 2 e 4")
		return
	}
		matrA=new Array(colonne);
	    matrB=new Array(colonne);
	for(var i=0;i<colonne;i++){
		matrA[i]=new Array(righe);
		matrB[i]=new Array(righe);
	  }
			for(var i=0; i<colonne; i++){
				for(var j=0; j<righe; j++){
					var valoreA = document.getElementById("a"+i+j).value;
					var valoreB = document.getElementById("b"+i+j).value;
					matrA[i][j]=valoreA
					matrB[i][j]=valoreB
				}
			}
			submatrA1=subMatrix(0,righe, 0,colonne/2, matrA)
			submatrB1=subMatrix(0,righe, 0,colonne/2, matrB)
			submatrA2=subMatrix(0,righe, colonne/2, colonne, matrA)
			submatrB2=subMatrix(0,righe, colonne/2, colonne, matrB)
			
			var data1={           //creo il dato da mandare in json (le 2 mezze matrici)
				matrA:submatrA1,
				matrB:submatrB1
			}
			var data2={            //creo il dato da mandare in json (le altre 2 mezze matrici)
				matrA:submatrA2,
				matrB:submatrB2
			}
			
			var json1 = JSON.stringify(data1);
			var json2 = JSON.stringify(data2);
			
			console.log("data1", data1)
			console.log("data2", data2)
			
		//	postData('CalculateServlet', JSON.stringify(data1));
		//	postData('CalculateServlet', JSON.stringify(data2));
			
			var	xhr1 = myGetXmlHttpRequest();
			var	xhr2 = myGetXmlHttpRequest();

			if ( xhr1 ) 
				countingMatrixAJAX(xhr1, json1); 
			if ( xhr2 )
			    countingMatrixAJAX(xhr2, json2); 
			else 
				countingIframe(); 	
			
}



// Example POST method implementation:
async function postData(url, data) {
  // Default options are marked with *
  var response =  await fetch(url, {
    method: 'POST', // *GET, POST, PUT, DELETE, etc.
   // mode: 'cors', // no-cors, *cors, same-origin
    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    credentials: 'same-origin', // include, *same-origin, omit
    headers: {
      'Content-Type': 'application/json'
      // 'Content-Type': 'application/x-www-form-urlencoded', 
    },
    redirect: 'follow', // manual, *follow, error
    referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    body: data // body data type must match "Content-Type" header
  });
	response = await response.json(); //for debugging
	parsifica(response);
}


function parsifica( obj ) {
		console.log(obj)
		var obj = JSON.parse(obj);   //ritorna un obj
		
		if(obj.message!==undefined){   //caso errore
			document.getElementById("resDIV").innerHTML=obj.message;
			return
		} 
		var res="";
		// restituzione dell'html da aggiungere alla pagina
		res+= "<strong>Result:</strong><br>";
		
		var matr = obj.matr
		
		for(var i=0;i<n;i++){
            for(var j=0;j<m;j++){
	        if(i!=n-1){
            res += "<input type='text' value="+matr[i][j]+" size='2'>"
            } else {
	        res += "<input type='text' value="+matr[i][j]+" size='2'><br>"
		   }
		  }
		}
	    document.getElementById("resDIV").innerHTML += res;
}  // parsifica

//Funzione di callback
function callback( theXhr ) {
	if ( theXhr.readyState === 2 ) {
	    	//theElement.innerHTML = "Richiesta inviata...";
	}// if 2
	else if ( theXhr.readyState === 3 ) {
    	//	theElement.innerHTML = "Ricezione della risposta...";
	}// if 3
	else if ( theXhr.readyState === 4 ) {
		// verifica della risposta da parte del server
		if ( theXhr.status === 200 ) {
			// operazione avvenuta con successo
			parsifica(theXhr.responseText);
			//location.reload();
		}// if 200

		 else {
	        	// errore di caricamento
			 alert("Impossibile effettuare l'operazione richiesta.");
//	        	theElement.innerHTML = "Impossibile effettuare l'operazione richiesta.<br />";
//	        	theElement.innerHTML += "Errore riscontrato: " + theXhr.statusText;
	        }// else (if ! 200)
	}// if 4
} // callback();


function countingIframe() {
	// qui faccio scaricare al browser direttamente il contenuto del feed come src dell'iframe.
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio")
	// non riesco tuttavia a intervenire per parsificarlo! è il browser che renderizza il src del iframe!
}// caricaFeedIframe()


function countingMatrixAJAX( theXhr, matr) {
	// impostazione controllo e stato della richiesta
	theXhr.onreadystatechange = function() { callback(theXhr); };
	// impostazione richiesta asincrona in GET
	// del file specificato
	try {
		theXhr.open("post", "CalculateServlet", true);
	}
	catch(e) {
		// Exceptions are raised when trying to access cross-domain URIs 
		alert(e);
	}
	// rimozione dell'header "connection" come "keep alive"
//	theXhr.setRequestHeader("connection", "close");
	var argument = "dati="+matr;
	// invio richiesta
	theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	theXhr.send(argument);
} // caricaFeedAJAX()


//m ed n valori presi dall'utente
function myFunction()
{
    var resA = "<p>Inserisci i valori della matrice A</p><br>";
	var resB = "<p>Inserisci i valori della matrice B</p><br>";
	m = parseInt(document.getElementById("m").value);
	n = parseInt(document.getElementById("n").value);
	
	for(var x=0; x<m; x++)    //ricostruisco le due matrici
		{
			for(var y=0; y<n; y++) {
				if(y!=n-1){    //se non sono all'ultima colonna non vado a capo
				resA += "R"+x+"C"+y+" : "+"<input type='text' id=a"+x+y+" size='2' onchange='checkMatrix(this);'>";
				resB += "R"+x+"C"+y+" : "+"<input type='text' id=b"+x+y+" size='2' onchange='checkMatrix(this);'>";
				} else {
				resA += "R"+x+"C"+y+" : "+"<input type='text' id=a"+x+y+" size='2' onchange='checkMatrix(this);'><br>";	
				resB += "R"+x+"C"+y+" : "+"<input type='text' id=b"+x+y+" size='2' onchange='checkMatrix(this);'><br>";
				}
			}
		}
		document.getElementById("myDIV").innerHTML = resA+resB;
}


function subMatrix(rigaIniziale, rigaLimite, colonnaIniziale, colonnaLimite, matrice){

  let col = colonnaLimite-colonnaIniziale;
  let row = rigaLimite-rigaIniziale;
  
  var subMatrix = new Array(col);
  for(var i=0; i<col; i++){
		subMatrix[i]=new Array(row); 
  }
  
  for(var i=0; i<=col; i++) {
     for(var j=0; j<=row; j++) {
      subMatrix[i][j] = matrice[colonnaIniziale][rigaIniziale];  
      rigaIniziale++;
     }    
     colonnaIniziale++;
  }
  return subMatrix;
}



//funzione che controlla se un numero è naturale
function isNaturalNumber(n) {
    n = n.toString(); // force the value incase it is not
    var n1 = Math.abs(n),
        n2 = parseInt(n, 10);
    return !isNaN(n1) && n2 === n1 && n1.toString() === n;
}

function checkMatrix(obj)
{
	var nota= true;   //controllo se la matrice è piena di valori 
	var notb= true;  
	///faccio il check sulla matrice A
	for(var x=0; x<m; x++)
	{
		for(var y=0; y<n; y++)
	    {
			var currentValue = document.getElementById(obj.id).value;		
			if(currentValue%2==0 || !isNaturalNumber(currentValue) || currentValue=='' || isNaN(currentValue))
			{
				nota=true;
				alert("Errore, il valore inserito non è un numero naturale dispari\n");
				return;
			}
		}
	}
	
	//faccio il check sulla matrice B
	for(var x=0; x<m; x++)
	{
		for(var y=0; y<n; y++)
	    {
			var currentValue = document.getElementById(obj.id).value;
			//matrice di interi dispari
			if(currentValue%2==0 || !isNaturalNumber(currentValue) || currentValue == '' || isNaN(currentValue))
			{
				notb=true;
				alert("Errore, il valore inserito non è un numero naturale dispari\n");
				return;
			}
		}
	}
	/*
	if(!notb && !nota)     //se entrambe le matrici sono piene
	{
		setJson();
	}
	*/
}