'use strict';

class App extends React.Component {
	
   constructor(){
	    super();
	    this.state = {
		    sommaMax: 0,
		    sommaMin: 0,
		    media_fuoriLista: 0,
		    fuoriLista: []     		//registro le somme delle triplette fuori-lista
	    }
	    
	    //settaggio event handlers    
	    this.start = this.start.bind(this);   
	    this.checkNumeri = this.checkNumeri(this);
	    this.visualizza = this.visualizza.bind(this);
	    this.reset = this.reset.bind(this);
	}
	
	
	getRandomInt(max) {
		return Math.floor(Math.random() * (max) );  //max escluso
	}


	start() {
	     
	     //estrazione random dei numeri
	     var numeri = [1, 2, 3, 4, 5, 6];
	     var lancio;
	     
	     for(var i=1; i<=3; i++) {
			lancio = getRandomInt(6+1);
			document.getElementById("dado"+i).value = numeri[lancio];
		}
	    
	    //colorazione backgroudColor sulla base dei numeri estratti
	    var element;
	    
	    for(var i=1; i<=3; i++) {
			element = document.getElementById("dado"+i);
			
			if(Number.parseInt(element.value) <= 3)
				element.style.backgroundColor = 'red';
			else
				element.style.backgroundColor = 'green';
		}
		
		//il gioco è iniziato, blocco il bottone di reset
	     document.getElementById("reset").removeAttribute("hidden");
 		
 		checkNumeri();
	     
	};
  
  
  	checkNumeri() {
	
		//prendo gli elementi dado
		var d1 = Number.parseInt(document.getElementById("dado1").value);
			d2 = Number.parseInt(document.getElementById("dado2").value);
			d3 = Number.parseInt(document.getElementById("dado3").value);
		
		var sum = d1 + d2 + d3;
		
		if(sum >= 6 && sum <= 15) {			//lista
			
			var msg = "Dado1 :"+d1+" Dado 2:"+d2+" Dado 3:"+d3;
			document.getElementById("textAreaRisultati").value += msg+ "\n";
			
			if(sum > this.state.sommaMax)
				this.setState({
			         sommaMax: sum
			    });
			else if(sum < this.state.sommaMin)
				this.setState({
			         sommaMin: sum
			    });
		} 
		else {								//fuori-lista
			
			this.setState({
		         fuoriLista: this.state.fuoriLista.push(sum)
		    });
		    
		    var count = 0;
		    var media = 0;
		    for(var i=0; i<this.state.fuoriLista.length; i++) {
				count++;
				media += this.state.fuoriLista[i]
			}
			
			media = media/count
			
			this.setState({
		         media_fuoriLista: media
		    });
		}
	}
	
	
	visualizza() {
		
		document.getElementById("sumMax").value = this.state.sommaMax;
		document.getElementById("sumMin").value = this.state.sommaMin
		document.getElementById("media").value = this.state.media_fuoriLista;
	}

	reset() {
		
		document.getElementById("reset").setAttribute("hidden","true");
		
	   	this.state = {
		    sommaMax: 0,
		    sommaMin: 0,
		    media_fuoriLista: 0,
		    fuoriLista: []     		
	    }
	};


  render() {
        return (

            <div className="app">
                <h1>APP:</h1>
                <LancioDadi start={this.start}/>
                <SequenzaLanci />
                <Statistica visualizza={this.visualizza} />
            </div>

        );
      }
  }
