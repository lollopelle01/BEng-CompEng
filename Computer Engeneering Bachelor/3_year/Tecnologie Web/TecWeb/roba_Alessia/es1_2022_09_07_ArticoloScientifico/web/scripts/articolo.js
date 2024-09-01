function abilita() {
	var contenuto = myGetElementById("contenuto");
	contenuto.disabled = false;
	
	var ask = myGetElementById("ask");
	ask.disabled = true;
	
	var let = myGetElementById("let");
	let.disabled = false;
}

function disabilita() {
	var contenuto = myGetElementById("contenuto");
	contenuto.disabled = true;
	
	var ask = myGetElementById("ask");
	ask.disabled = false;
	
	var let = myGetElementById("let");
	let.disabled = true;
}

function myfunction() {
	
	var contenuto = myGetElementById("contenuto");
	var testo = contenuto.value;
	
	var testo = contenuto.value;
	var c = testo[testo.length-1];
	
	if(c == '%') {
			
		testo = testo.substring(0, testo.length - 1);
		console.log(testo);
		
		if(testo.length == 0) {
			alert("Non è stato inserito alcun nome per l'articolo");
			contenuto.value = "";
			return;
		}
		
		document.getElementById("start").submit();
			
	} else {
		if(testo.length > 64) {
			alert("Non è possibile inserire altri caratteri, prego inviare la richiesta");
			testo =  testo.substring(0, testo.length - 1);
			contenuto.value = testo;
			return;
			
		}
	}
}