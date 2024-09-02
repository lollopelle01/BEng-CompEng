var Statistica = new Object();
Statistica.min = function (array) { return Math.min.apply(this, array); }
Statistica.max = function (array) { return Math.max.apply(this, array); }
Statistica.average = function (array) {
	var sum = 0;
	for (var i=0; i<array.length; i++) {
		sum += +array[i];
	}
	return sum/array.length;
}
Statistica.variation = function (array) {
	var average = this.average(array);
	var variationsum = 0;
	for (var i=0; i<array.length; i++) {
		variationsum += Math.abs(+array[i]-average);
	}
	return variationsum/array.length;
}

var validvotes;

function askVotes() {
	var votesString = prompt("Inserisci i tuoi voti (compresi tra 18 e 33) separati da spazi:");

	if (votesString == null) {
		var a = document.createElement('a');
		var linkText = document.createTextNode("Riprova!");
		a.appendChild(linkText);
		a.href = "../pages/esami.html";
		document.body.appendChild(a);
	}
	else if (votesString.length == 0) {
		var a = document.createElement('a');
		var linkText = document.createTextNode("Non hai scritto nulla, riprova!");
		a.appendChild(linkText);
		a.href = "../pages/esami.html";
		document.body.appendChild(a);
	}
	else {
		var p = document.createElement("p");
		p.appendChild(document.createTextNode("Stringa inserita: "+votesString));
		document.getElementById("body").appendChild(p);
		
		var votes = getVotes(votesString);

		if (validvotes) {
			document.write("Minimo: ");
			document.writeln(Statistica.min(votes));
			document.write("<br/>");
			
			document.write("Massimo: ");
			document.writeln(Statistica.max(votes));
			document.write("<br/>");
			
			document.write("Media: ");
			var average = Statistica.average(votes);
			document.write(Math.round(average*100)/100);
			switch (true) {
				case (average <= 20):
					document.write(", un po' bassa :) ");
					break;
				case (average > 20 && average <= 24):
					document.write(", accettabile :) ");
					break;
				case (average > 24 && average <= 27):
					document.write(", not bad!");
					break;
				case (average > 27 && average <= 29):
					document.write(", this is good man!");
					break;
				case (average > 29 && average <= 31):
					document.write(", this is top notch!!");
					break;
				case (average > 31):
					document.write(", this is fucking puuuurfect!!!");
					break;
				default:
					break;
			}
			document.writeln();
			document.write("<br/>");
			
			document.write("Variabilita': ");
			var variation = Statistica.variation(votes);
			document.write(Math.round(variation*100)/100);
			switch (true) {
				case (variation <= 2):
					document.write(", bassa");
					break;
				case (variation > 2 && variation <= 4):
					document.write(", media");
					break;
				case (variation > 4 && variation <= 6):
					document.write(", alta");
					break;
				case (variation > 6):
					document.write(", molto alta");
					break;
				default:
					break;
			}
			document.writeln();
			document.write("<br/>");
		}
	}
}

function getVotes(votesString) {
	var votes = votesString.split(" ");
	validvotes = true;
	for(var i=0; i<votes.length; i++) { votes[i] = +votes[i]; } 
	document.write("Voti: ");
	for (var i=0; i<votes.length; i++) {
		if (i!=0)
			document.write(", ");
		if (isNaN(votes[i]) || votes[i] > 33 || votes[i] < 18) {
			validvotes = false;
			document.write("voto non valido");
		}
		else
			document.write(votes[i]);
	}
	document.writeln();
	document.body.appendChild(document.createElement("br"));
	
	if (!validvotes) {
		var a = document.createElement('a');
		var linkText = document.createTextNode("Uno o piu' voti inseriti non sono validi, riprova!");
		a.appendChild(linkText);
		a.href = "../pages/esami.html";
		document.body.appendChild(a);
	}
	
	return votes;
	
}