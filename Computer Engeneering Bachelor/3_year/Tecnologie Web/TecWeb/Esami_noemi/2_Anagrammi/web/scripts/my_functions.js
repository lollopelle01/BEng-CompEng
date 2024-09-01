function controlla(){
	var testo = document.getElementById("testo").value;
	var controllo = /^[a-zA-Z $]+$/gi;
	var test = controllo.test(testo);
	if (test){
		carica(xhr1, "Servlet1", document.getElementById("risultato1"));
		carica(xhr2, "Servlet2", document.getElementById("risultato2"));
	}
}

function abort1(){
	xhr1.abort();
}

function abort2(){
	xhr1.abort();
}