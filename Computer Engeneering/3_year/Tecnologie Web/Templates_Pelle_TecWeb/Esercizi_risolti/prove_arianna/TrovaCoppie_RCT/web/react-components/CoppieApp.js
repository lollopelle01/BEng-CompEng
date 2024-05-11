'use strict';

class CoppieApp extends React.Component {
	
   constructor(){
	    super();
	    this.state = {
		    lato: 4,
		    lastletter: "",
		    last_row: -1,
		    last_col: -1,
		    tentativi_win: 0,
		    tentativi_loose: 0,
		    final: "",
		    lettere: [],
		    griglia:""
	    }
	    
	    //settaggio event handlers    
	    this.start = this.start.bind(this);       
	    this.creaGriglia = this.creaGriglia.bind(this);
	    this.onClick = this.onClick.bind(this);
	}
	

	start() {
		 this.setState({
			griglia : this.creaGriglia() 
		 });     
		 document.getElementsByClassName("grigliaDIV")[0].innerHTML = this.state.griglia;
	};
	
  
	creaGriglia() {
		
		var valori = ['A','B','C','D','E','F','G','H'];
    	var numUsciti = [0, 0, 0, 0, 0, 0, 0, 0];
    	var num;
		
		var matrix=[];
		for(var i=0; i<this.state.lato; i++)
		{
			matrix[i]=[]
			for(var j=0; j<this.state.lato; j++)
			{
				do {
					num = Math.floor(Math.random() * 8);
				} while (numUsciti(num) == 2);
				
				matrix[i][j] = valori(num);
				numUsciti(num)++;
			}
		}
		
		this.setState({
            lettere: matrix
        });
        
        
		var g=""		
		for(var x=0; x<m; x++) {
			for(var y=0; y<n; y++) {
				
				if(y!=n-1)  
					g += "<input type='button' id="+x+","+y+" name="+x+","+y+" size='2' style='background-color:gray' onClick={this.props.onClick}>";
				else
					g += "<input type='button' id="+x+","+y+" name="+x+","+y+" size='2' style='background-color:gray' onClick={this.props.onClick}><br>";	
			}
		}
		return g;
	}


	onClick(e){
		
		let target = e.target
		
		if(target.value==undefined || target.value=="" || target.value==null) {
			
			let r = parseInt(target.name.split(',')[0]);
			let c = parseInt(target.name.split(',')[1]);
			
			var newletter = this.state.matrix[r][c];
			document.getElementById(r+','+c).value = this.state.lettere[r][c];
			
			if(this.state.lastletter == "") {
				this.setState({
					lastletter: newletter,
					last_col: c,
					last_row: r
		        });
			} 
			else {
				if(this.state.lastletter == letter) {
					document.getElementById(r+','+c).style.background = "green";
					document.getElementById(this.state.last_row+','+this.state.last_col).style.background = "green";
					alert("coppia individuata");
					
					this.setState({
						lastletter: "",
						tentativi_win: tentativi_win+1,
						last_col: -1,
						last_row: -1
			        });
					
				} else {
					document.getElementById(r+','+c).value = "";
					document.getElementById(this.state.last_row+','+this.state.last_col).value = "";
					alert("coppia non individuata");
					
					this.setState({
						lastletter: "",
						tentativi_loose: tentativi_loose+1,
						last_col: -1,
						last_row: -1
			        });
				}
			}

			if(this.state.tentativi_win == 8) {  //l'utente ha trovato 8 coppie
				this.setState({
					final: "Fine del gioco!"
		        });
			}
		}
		//altrimenti cella gia' scoperta
	};
	

	reset() {
		
		this.state = {
		    lato: 4,
		    lastletter: "",
		    last_row: -1,
		    last_col: -1,
		    tentativi_win: 0,
		    tentativi_loose: 0,
		    final: "",
		    lettere: [],
		    griglia:""
	    }
		
		start();
	};
	

  render() {
        return (

            <div className="app">
                <h1>TROVA LE COPPIE:</h1>
                <Init start={this.start} />
                <Griglia  onClick={this.onClick} />
                <Risultati vinte={this.state.tentativi_win} perse={this.state.tentativi_loose} fine={this.state.final} reset={this.reset}/>
            </div>

        );
      }
  }
