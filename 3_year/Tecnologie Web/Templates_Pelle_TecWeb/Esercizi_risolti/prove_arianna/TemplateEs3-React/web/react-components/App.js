'use strict';

var varglob;

class App extends React.Component {
	
   constructor(){
	    super();
	    this.state = {
		    input1:"",
		    input2: "",
		    count: 0,
		    lista: "",
		    numeri: "",
		    griglia:""
	    }
	    
	    //settaggio event handlers
	    this.onKeyUp = this.onKeyUp.bind(this);      
	    this.start = this.start.bind(this);       
	    this.creaGriglia = this.creaGriglia.bind(this);
	    this.checkButton = this.checkButton.bind(this);
	    this.reset = this.reset.bind(this);
	}

	start() {

	     if(this.state.input1==undefined || this.state.input1=="" || this.state.input1==null){
	        alert("ERRORE di input");
	        return;  
	     }
	     if(this.state.input2==undefined || this.state.input2=="" || this.state.input2==null){
	      	alert("ERRORE di input");
		    return;  
	     }
	     
	     if(this.state.credito == 0 || this.state.credito < 5){
	        alert("Non hai abbastanza credito per giocare, minimo 5 monete");
	        return;  
	     }
	      
	     this.setState({
			lista : [],    //lista di qualcosa
	    	griglia : this.creaGriglia() 
	     });     
	     
	     document.getElementsByClassName("giocoDIV")[0].innerHTML = this.state.griglia;
	     
	     //il gioco è iniziato, blocco il bottone di reset
	     document.getElementById("reset").removeAttribute("hidden");
	     
	     // se devo attendere prima di fare altro setto un timeout che poi richiama la funzione specificata
	     setTimeout(this.generaVocali, 2000);
	};
	

	//CHECK INPUTS
	onKeyUp(e) {  
		
		var element = e.target;
	
    	if(element.name=="input1") {
	
			if(element.value>5){       //check alt > 5
		    	this.setState({
		       		alt: e.target.value
		     	});
	   		} else {
				alert("ERRORE alt, it must be greater than 5");
				return;  
	    	}
    	}
    	
    	// oppure
    	
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
 	};
  
 
	getRandomInt(max) {
		return Math.floor(Math.random() * (max) );  //max escluso
	}
  
	creaGriglia() {
		
		m = parseInt(this.state.larg)
		var n = parseInt(this.state.alt)
		var g=""
		
		//in caso dovessi settare anche una matrice
		var matrix=[]
		
		for(var i=0;i<n;i++){
			matrix[i]=[]
			for(var j=0;j<m;j++){
				matrix[i][j]=getRandomInt(5);
			}
		}
		this.setState({
            numeri: matrix
        });
		
		for(var x=0; x<m; x++) {
			for(var y=0; y<n; y++) {
				
				//potrei dover mettere la virgola tra x e y
				if(y!=n-1)  
					g += "<input type='button' id="+x+","+y+" name="+x+","+y+" size='2' style='background-color:gray' onClick='checkButton(this);'>";
				else
					g += "<input type='button' id="+x+y+" name=a"+x+y+" size='2' style='background-color:gray' onClick='checkButton(this);'><br>";	
			}
		}
		return g
	}
	
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


	checkButton(e){
		
		let target = e.target.name
		let valore = e.target.value
		
		if(valore==undefined || valore=="" || valore==null) {
			
			//prendere gli indici della riga e della colonna
			let r = parseInt(target.split(',')[0]);
			let c = parseInt(target.split(',')[1]);
			
			//per prendere una cella adiacente
			document.getElementById((r+1)+","+(c+1)).style.backgroundColor = 'white';
			
			
			//dopo alcuni calcoli
			//1) si risettano nuovi valori dello stato
			//2) e poi si verifica se l'utente ha vinto, ha perso o è finito il gioco
			
			
			var final = ""
			final += "Punteggio : <input type=\"text\" id=\"punti\" value={this.props.punti}/>"
	       	final += "<input type=\"button\" id=\"reset\" value=\"RESTART_GAME\" onClick={this.props.reset}/>"
	       	
	       	document.getElementsByClassName("Punteggio")[0].innerHTML = final
			//oppure setto una variabile punteggio e modifico in basso la render per mostrare quella
		}
		//altrimenti cella non utilizzabile se già settata
		
		
		//oppure posso evitare if ed else con questa impostazione
		document.getElementById(e.target.id).setAttribute("disabled","disabled");
		
		
		//settaggio risultato di una giocata nella text area
		document.getElementById("textAreaRisultati").value += result+ "\n";
	};


	reset() {
		
		//rinascondo il bottone
		document.getElementById("reset").setAttribute("hidden","true");
		
	   var resetW = this.state.countW;
	   var resetL = this.state.countL;
	   
        this.state = {
	        alt:this.state.alt,
	        larg: this.state.larg,
	        countW: resetW,
	        countL: resetL,
	        countM: 0,
	        countP: 0,
	        res:""
        }
	};


	perso(){
		let c = this.state.count+1
		this.setState({
	         count: c 
	    });
		alert("Partita terminata: hai perso!");
		reset();                                 //la partita finisce ma subito ne setto una nuova
	};

  render() {
        return (

            <div className="app">
                <h1>APP:</h1>
                <Configurazione onKeyUp={this.onKeyUp} start={this.start}/>
                <Gioco checkButton={this.checkButton}/>
                <Conteggio onChange={this.onChange}  vinte={this.state.countW} perse={this.state.countL}/>
            </div>

        );
      }
  }
