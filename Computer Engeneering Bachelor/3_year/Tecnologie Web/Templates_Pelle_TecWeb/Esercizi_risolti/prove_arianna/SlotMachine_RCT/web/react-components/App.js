'use strict';

var varglob;

class App extends React.Component {
	
   constructor(){
	    super();
	    this.state = {
			credito: 0,
		    num_giocate: 0,
	    }
	    
	    //settaggio event handlers     
	    this.aggiungi = this.aggiungi.bind(this);
	    this.start = this.start.bind(this);   
	    this.generaVocali = this.generaVocali.bind(this);
	    this.checkVocali = this.checkVocali.bind(this);
	    this.reset=this.reset.bind(this);
	}


	start() {

	     if(this.state.credito == 0 || this.state.credito < 5){
	        alert("Non hai abbastanza credito per giocare, minimo 5 monete");
	        return;  
	     }
	      
	     this.setState({
			credito : this.state.credito-5,
	     });  
	     document.getElementById("reset").removeAttribute("hidden");
	     
	     //attesa di due secondi
	     setTimeout(this.generaVocali, 2000);
	};
	
	
	generaVocali () {
		
		//estrazione random delle vocali
	     var vocali = ['a', 'e', 'i', 'o', 'u'];
	     var num;
	     
	     for(var i=1; i<=3; i++) {
			num = Math.floor(Math.random() * 5);
			document.getElementById("display"+i).value = vocali[num];
		}
	    
	    //colorazione backgroudColor sulla base delle vocali estratte
	    var element;
	    
	    for(var i=1; i<=3; i++) {
			element = document.getElementById("display"+i);
			if(element.value == 'a' || element.value == 'e')
				element.style.backgroundColor = 'green';
			else
				element.style.backgroundColor = 'blue';
		}
 		
 		checkVocali();
	}


	checkVocali() {
		
		//prendo gli elementi dei display
		var d1 = document.getElementById("display1").value;
			d2 = document.getElementById("display2").value;
			d3 = document.getElementById("display3").value;
			
		this.setState({
			num_giocate : this.state.num_giocate+1,
	     });
			
		//intanto costruisco anche la stringa risultato
		var result = "Giocata n°"+this.state.num_giocate+" "+d1+" "+d2+" "+d3+" ";
			
		if(d1 == d2 || d2 == d3 || d3 == d1) {	
			
			if(d1 == d2 && d2 == d3) {						//check 3 lettere uguali
			
				alert("Che fortuna! 2 lettere uguali!")
				this.setState({
		       		credito: this.state.credito + 15
		     	});
		     	result += "+15 sul tuo credito"
		     		
			} else {										//check 2 lettere uguali
			
				alert("Che fortuna! 3 lettere uguali!")
				this.setState({
		       		credito: this.state.credito + 50
		     	});
		     	result += "+50 sul tuo credito"
		     	
			}
		}
		
		//settaggio risultato di una giocata
		document.getElementById("textAreaRisultati").value += result+ "\n";
		
	};
	
	
	aggiungi() {
		
		var incr = document.getElementById("incremento").value;
		
		if(!isNan(incr)) {   
			
			incr = Number.parseInt(incr);
			
			if(incr > 0) {
				this.setState({
		       		credito: this.state.credito + incr
		     	});
			} else {
				alert("ERRORE incremento, deve essere positivo...");
			return;  
			}   
	     	
   		} else {
			alert("ERRORE incremento, deve essere un numero...");
			return;  
    	}
	}
	
	
	reset() {
		
		//rinascondo il bottone
		document.getElementById("reset").setAttribute("hidden","true");
		
		this.state = {
			credito: 0,
		    lista_giocate: [],  
		    num_giocate: 0,
	    }
	}


  render() {
        return (

            <div className="app">
                <h1>SLOT MACHINE:</h1>
                <Credito aggiungi={this.aggiungi} credito={this.state.credito}/>
                <SlotMachine start={this.start} />
                <Risultati reset={this.reset}/>
            </div>

        );
      }
  }
