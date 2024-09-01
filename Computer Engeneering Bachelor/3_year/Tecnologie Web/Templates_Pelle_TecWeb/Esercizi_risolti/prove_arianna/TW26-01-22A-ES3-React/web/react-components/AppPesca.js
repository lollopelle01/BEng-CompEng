'use strict';

class AppPesca extends React.Component {
	
   constructor(){
	    super();
	    this.state = {
		    larg:"",
		    lung: "",
		    lanci: "",
		    countLanci: 0,
		    countpunti: 0,
		    listaPunti: "",
		    griglia: "",
		    pesci: ""
	    }
	    
	    //settaggio event handlers
	    this.start = this.start.bind(this);      
	    this.creaGriglia=this.creaGriglia.bind(this);
	    this.lancia = this.lancia.bind(this);   
	    this.onKeyUp = this.onKeyUp.bind(this); 
	    this.reset=this.reset.bind(this);
	}

	start() {

	     if(this.state.larg==undefined || this.state.larg=="" || this.state.larg==null){
	        alert("ERRORE di input in larghezza");
	        return;  
	     }
	     if(this.state.lung==undefined || this.state.lung=="" || this.state.lung==null){
	      	alert("ERRORE di input in lunghezza");
		    return;  
	     }
	     if(this.state.lanci==undefined || this.state.lanci=="" || this.state.lanci==null){
	      	alert("ERRORE di input in lanci");
		    return;  
	     }
	      
	     this.setState({
			listaPunti : [],
	    	griglia : this.creaGriglia() //se tutto ok, creo griglia
	     });     
	     document.getElementsByClassName("pescaDIV")[0].innerHTML = this.state.griglia;
	};

	//check inputs
	onKeyUp(e) {  
	
    	if(e.target.name=="larg"){
	
			if(e.target.value>=8){       //check larg > 8
		    	this.setState({
		       		larg: e.target.value
		     	});
	   		} else {
				alert("ERRORE larg, it must be greater than 8");
				return;  
	    	}
    	}
    	
    	if(e.target.name=="lung"){
	
			if(e.target.value>=8){       //check lung > 8
		    	this.setState({
		       		lung: e.target.value
		     	});
	   		} else {
				alert("ERRORE lung, it must be greater than 8");
				return;  
	    	}
    	}
    	
    	if(e.target.name=="lanci"){
	
			if(e.target.value>=0){       //check lanci > 0
		    	this.setState({
		       		lanci: e.target.value
		     	});
	   		} else {
				alert("ERRORE lanci, it must be greater than 0");
				return;  
	    	}
    	}
 	};
  
 
	getRandomInt(max) {
		return Math.floor(Math.random() * (max+1) );
	}
  
	creaGriglia() {
		m = parseInt(this.state.larg)
		var n = parseInt(this.state.lung)
		var g=""
		var matrix=[]
		
		for(var i=0;i<n;i++){
			matrix[i]=[]
			for(var j=0;j<m;j++){
				matrix[i][j]=getRandomInt(5);
			}
		}
		this.setState({
            pesci: matrix
        });
		
		for(var x=0; x<m; x++) {
			for(var y=0; y<n; y++) {
				if(y!=n-1)   //se non sono all'ultima colonna non vado a capo
					g += "<input type='button' id="+x+","+y+" name="+x+","+y+" size='2' style='background-color:gray' onClick='lancia(this);'>";
				else
					g += "<input type='button' id="+x+","+y+" name="+x+","+y+" size='2' style='background-color:gray' onClick='lancia(this);'><br>";	
			}
		}
		
		return g
	}

	lancia(e){
		let target = e.target.name
		let punti = e.target.value
		
		if(punti==undefined || punti==""||punti==null) {
			let r=parseInt(target.split(',')[0]);
			let c=parseInt(target.split(',')[1]);
			
			let r_min, r_max;
			let c_min, c_max;
			
			var totpesci = 0;
			
			if(r == 0) {
				r_min = 0;
				r_max = r+1;
			}
			else if(r == this.state.lung) {
				r_max = this.state.lung;
				r_min = r-1;
			}
			else {
				r_min = r-1;
				r_max = r+1;
			}
			
			if(c == 0) {
				c_min = 0;
				c_max = c+1;
			}
			else if(c == this.state.larg) {
				c_max = this.state.larg;
				c_min = c-1;
			}
			else {
				c_min = c-1;
				c_max = c+1;
			}
				
			for(var i=r_min; i<=r_max; i++) {
				for(var j=c_min; c<=c_max; j++) {
					document.getElementById(i+','+j).value = this.state.pesci[i][j];
					document.getElementById(i+','+j).style.background = "white"
					totpesci += this.state.pesci[i][j];
				}
			}
			
			this.setState({
		         countLanci: this.state.countLanci+1,
		         listaPunti : this.state.listaPunti.add(totpesci),
		         countPunti : this.state.countpunti +totpesci
		       });
		    alert("hai pescato "+totpesci+" pesci");
		    
		    if(this.state.countLanci==parseInt(this.state.lanci) ) 
		    {
				var finePesca=""
				finePesca+="Counter punti : <input type=\"text\" id=\"puntiTot\" value=\""+this.state.countpunti+"\"/>"
				finePesca+="Punti medi : <input type=\"text\" id=\"puntiMed\" value=\""+(this.state.countpunti/this.state.lanci)+"\"/>"
				finePesca+="<input type=\"button\" id=\"reset\" value=\"RESTART_GAME\" onClick={this.props.reset}/>"
				document.getElementsByClassName("Conteggio")[0].innerHTML += finePesca;
			}
				
		}
		//altrimenti è una cella gia utilizzata
	};


	reset() {
	   
        this.state = {
	        larg:"",
		    lung: "",
		    lanci: "",
		    countLanci: 0,
		    countpunti: 0,
		    listaPunti: "",
		    griglia: "",
		    pesci: ""
        }
	};

  render() {
        return (

            <div className="app">
                <h1>PESCA:</h1>
                <ConfigurazionePesca onKeyUp={this.onKeyUp} start={this.start}/>
                <Pesca checkButton={this.lancia}/>
                <ConteggioPesca lista={this.state.listaPunti}/>
            </div>

        );
      }
  }
