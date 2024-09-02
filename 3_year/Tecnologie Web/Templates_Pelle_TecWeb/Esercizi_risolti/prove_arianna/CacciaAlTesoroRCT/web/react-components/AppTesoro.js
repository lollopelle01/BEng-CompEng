'use strict';

var x_tesoro;
var y_tesoro;

class AppTesoro extends React.Component {
	
   constructor(){
	    super();
	    this.state = {
		    larg:"",
		    lung: "",
		    tent: 0,
		    punti: 0,
		    griglia:""
	    }
	    
	    //settaggio event handlers
	    this.onKeyUp = this.onKeyUp.bind(this);      
	    this.start = this.start.bind(this);       
	    this.creaGriglia=this.creaGriglia.bind(this);
	    this.checkButton = this.checkButton.bind(this);
	    this.reset=this.reset.bind(this);
	}

	start() {

	     if(this.state.larg==undefined || this.state.larg=="" || this.state.larg==null){
	        alert("ERRORE di input");
	        return;  
	     }
	     if(this.state.lung==undefined || this.state.lung=="" || this.state.lung==null){
	      	alert("ERRORE di input");
		    return;  
	     }
	      
	     this.setState({
	    	griglia : this.creaGriglia() 
	     });     
	     document.getElementsByClassName("mappaDIV")[0].innerHTML = this.state.griglia;
	};

	//check inputs
	onKeyUp(e) {  
	
    	if(e.target.name=="larg"){
	
			if(e.target.value>=5){       //check larg > 5
		    	this.setState({
		       		larg: e.target.value
		     	});
	   		} else {
				alert("ERRORE larg, it must be greater than 5");
				return;  
	    	}
    	}
    	
    	if(e.target.name=="lung"){
	
			if(e.target.value>=5){       //check lung > 5
		    	this.setState({
		       		lung: e.target.value
		     	});
	   		} else {
				alert("ERRORE lung, it must be greater than 5");
				return;  
	    	}
    	}
 	};
  
 
	getRandomInt(max) {
		return Math.floor(Math.random() * (max+1) );
	}
  
	creaGriglia() {
		
		var m = parseInt(this.state.larg)
		var n = parseInt(this.state.lung)
		var g=""
		
		x_tesoro = this.getRandomInt(n);
		y_tesoro = this.getRandomInt(m);
		
		for(var x=0; x<m; x++) {
			for(var y=0; y<n; y++) {
				
				if(y!=n-1)  
					g += "<input type='button' id="+x+","+y+" name="+x+","+y+" size='2' style='background-color:gray' onClick='checkButton(this);'>";
				else
					g += "<input type='button' id="+x+","+y+" name="+x+","+y+" size='2' style='background-color:gray' onClick='checkButton(this);'><br>";	
			}
		}
		return g
	}


	checkButton(e){
		
		let target = e.target.name
		let valore = e.target.value
		
		if(valore==undefined || valore=="" || valore==null) {
			
			let r = parseInt(target.split(',')[0]);
			let c = parseInt(target.split(',')[1]);
			
			this.setState({
				tent : this.state.tent+1
			});
			
			if(r == x_tesoro && c == y_tesoro) {
				alert("Hai trovato il tesoro, congraturazioni!");
				document.getElementById((r)+","+(c)).style.backgroundColor = 'yellow';

				this.setState({
					punti : (this.state.tent <= 10) ? 5 : 2
				});
				
				var final = ""
				final += "Punteggio : <input type=\"text\" id=\"punti\" value={this.props.punti}/>"
		       	final += "<input type=\"button\" id=\"reset\" value=\"RESTART_GAME\" onClick={this.props.reset}/>"
		       	
		       	document.getElementsByClassName("Punteggio")[0].innerHTML = final
		       	
			} else
				document.getElementById((r)+","+(c)).value = 'T';
		}
		//altrimenti cella già settata, non utilizzabile
	};

	reset() {
		
	   this.state = {
		    larg:"",
		    lung: "",
		    tent: 0,
		    punti: 0,
		    griglia:""
	    }
	    
	    x_tesoro = -1;
	    y_tesoro = -1;
	};

  render() {
        return (

            <div className="app">
                <h1>CACCIA AL TESORO</h1>
                <Configurazione onKeyUp={this.onKeyUp} start={this.start}/>
                <MappadelTesoro checkButton={this.checkButton}/>
                <Punteggio tent={this.state.tent}/>
            </div>

        );
      }
  }
