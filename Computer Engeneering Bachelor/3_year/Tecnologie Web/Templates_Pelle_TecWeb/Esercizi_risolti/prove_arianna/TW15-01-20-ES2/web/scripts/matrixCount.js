

function printResult( stringa ) {
   
	var result = stringa.split("@");
	var resElement;
	
	for(var i =0; i<result.length; i++) {
		resElement = document.getElementById("r"+i);
		resElement.value = result[i];
	}

	//reset delle matrici
	for(var i=0; i<8; i++) {
		var elementA = document.getElementById("a"+i);
		var elementB = document.getElementById("b"+i);
		elementA.value = '';
		elementB.value = '';
	}
}

function callback( theXhr ) {

	if ( theXhr.readyState === 2 ) {
	    	//theElement.innerHTML = "Richiesta inviata...";
	}
	else if ( theXhr.readyState === 3 ) {
    	//	theElement.innerHTML = "Ricezione della risposta...";
	}
	else if ( theXhr.readyState === 4 ) {

		if ( theXhr.status === 200 ) {
			printResult(theXhr.responseText);
			
			//location.reload();
		} else 
		 	alert("Impossibile effettuare l'operazione richiesta.");
	}
} 

function countingIframe() {
	Alert("Impossibile effettuare l'operazione, il tuo browser è troppo vecchio");
}

function countingMatrixAJAX( theXhr, mode, aVal, bVal) {
    
	theXhr.onreadystatechange = function() { callback(theXhr); };

	try {
		theXhr.open("post", "MatrixCalculator", true);
	}
	catch(e) {
		alert(e);
	}

	theXhr.setRequestHeader("connection", "close");
	theXhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	
	var argument = "mode="+mode+"&aVal="+aVal+"&bVal="+bVal;
	theXhr.send(argument);
}

function myFunction(caller) {
	
	var mode = caller.getAttribute("id");
	
	//creo due stringhe con i valori delle matrici A e B
	var aValues = "";
	var bValues = "";
	for(var i=0; i<8;i++) {
		aValues = aValues + MyGetElementById("a"+i).value+"@";
		bValues = bValues + MyGetElementById("b"+i).value+"@";
	}
	
	var	xhr = myGetXmlHttpRequest();

	if (xhr) 
		countingMatrixAJAX(xhr, mode, aValues, bValues); 
	else 
		countingIframe(); 
}

function checkMatrix()
{
	var nota= false;
	var notb= false;
	
	for(var i =0; i<8; i++) {
		var currentValue = MyGetElementById("a"+i).value;
		if(currentValue == '' || isNaN(currentValue))
			nota=true;
	}
	for(var i =0; i<8; i++) {
		var currentValue = MyGetElementById("b"+i).value;
		if(currentValue == '' || isNaN(currentValue))
			notb=true;
	}
	
	if(!notb && !nota) {
		var currentValue = MyGetElementById("secret");
		currentValue.style.display = 'block';
	}
}
