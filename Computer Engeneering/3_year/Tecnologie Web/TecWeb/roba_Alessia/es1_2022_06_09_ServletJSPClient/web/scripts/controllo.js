const numbers = "0123456789";
const letters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM ";
var testo;

function respondJson(){
	
	var json = myGetElementById("rispDef");
	
	var rispDef = JSON.parse(json.value);
	
	var res = document.getElementById("risultato");
	res.innerHTML += '<p>' + 'Il nuovo testo corrisponde a: ' + '<br>'
							+ rispDef.testo + '<br>'
							+ 'Parole maiuscole trovate: ' + rispDef.count
					+ '</p>';
}

function send(){
	
	var jsonObject = '{"testo":\"' + testo + '\"}';
	
	var input = myGetElementById("formatoJSON");
	input.value = jsonObject;
		
	var invia = myGetElementById("toServlet");
	invia.submit();
	
}

function myfunction(){
	
	var contenuto = myGetElementById("text_area");
	testo = contenuto.value;
	
	var c = testo[testo.length-1];
	
	if(!numbers.includes(c) && !letters.includes(c)) {
		alert("Sono ammessi solo caretteri alfanumerici!");
		testo =  testo.substring(0, testo.length - 1);
		contenuto.value = testo;
		return;
	}
	
	var input = myGetElementById("send");
	if(testo.length > 100) {
		input.style.display = 'block';
	} else {
		input.style.display = 'none';
	}	
}