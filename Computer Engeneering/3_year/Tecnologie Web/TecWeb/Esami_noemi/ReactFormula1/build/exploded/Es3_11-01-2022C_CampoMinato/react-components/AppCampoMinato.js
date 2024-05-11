'use strict';

var mina1;     //le due mine 
var mina2;
var m;       //larghezza, mi serve per le mine
class App extends React.Component {
    constructor(){
            super();
            this.state = {
            alt:"",
            larg: "",
            countW: 0,
            countL: 0,
            countM: 0,
            countP: 0,
            res:""
            }
            this.start = this.start.bind(this);       //inizio partita
            this.checkButton = this.checkButton.bind(this);    //bottone che premo
            this.onKeyUp = this.onKeyUp.bind(this);        //alt e larg
            this.reset=this.reset.bind(this);
            this.creaGriglia=this.creaGriglia.bind(this);
    }


 start(e) {
              //valuto che i campi non siano vuoti
              if(this.state.alt==undefined||this.state.alt==""||this.state.alt==null){
                    //controllo email ben formattata
                     alert("ERRORE");
	                 return;  
              }
              if(this.state.larg==undefined||this.state.larg==""||this.state.larg==null){
                   alert("ERRORE");
	               return;  
              }
          
         this.setState({
        	 res : this.creaGriglia() //se tutto ok, creo griglia
         });     
         document.getElementsByClassName("giocoDIV")[0].innerHTML = this.state.res;
    };


 onKeyUp(e) {
    if(e.target.name=="alt"){
	  if(e.target.value>5){       //check alt > 5
       this.setState({
       alt: e.target.value
        });
    } else {
	  alert("ERRORE alt");
	  return;  
    }
    }
    if(e.target.name=="larg"){
	   if(e.target.value>5){
            this.setState({
      larg: e.target.value
         });
    } else {
	  alert("ERRORE larg");
	  return;  
    }
    }
  };
  
 
 getRandomInt(max) {
  return Math.floor(Math.random() * max);
}
  
  
creaGriglia() {
	m = parseInt(this.state.larg)
	var resA=""
	var n = parseInt(this.state.alt)
	for(var x=0; x<m; x++)    //ricostruisco la griglia
		{
			for(var y=0; y<n; y++) {
				if(y!=n-1){    //se non sono all'ultima colonna non vado a capo
				resA += "<input type='button' id=a"+x+y+" name=a"+x+y+" size='2' style='background-color:gray' onClick='checkButton(this);'>";
				} else {
				resA += "<input type='button' id=a"+x+y+" name=a"+x+y+" size='2' style='background-color:gray' onClick='checkButton(this);'><br>";	
				}
			}
		}
	return resA
}


checkButton(e){
	let val = e.target.name
	let c = this.state.countM
	if(parseInt(e.target.id)==parseInt(mina1, 10) || parseInt(e.target.id)==mina2){    //hai preso una mina
	   document.getElementById(val).style.backgroundColor = 'red';      //se prendo la mina il bottone diventa rosso
	   c++;
	   this.setState({
         countM: c
       });
	   alert("hai preso una mina");
	   if(this.state.countM==3){
	      perso();
	    }
	} else {
	   document.getElementById(val).style.backgroundColor = 'blue';    //se NON prendo la mina il bottone diventa blu
	   c = this.state.countP
	   this.setState({
         countP: c
       });
       if(this.state.countP==5){
	     vinto();
      }
	}
};


reset() {
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
	let c = this.state.countL+1
	this.setState({
         countL: c 
    });
	alert("Partita terminata: hai perso!");
	reset();                                 //la partita finisce ma subito ne setto una nuova
};


vinto() {
	let c = this.state.countW+1
	this.setState({
         countW: c 
       });
	alert("Partita terminate: hai vinto! ");
	reset();                               //la partita finisce ma subito ne setto una nuova
};


  render() {
        return (

            <div className="app">
                <h1>CAMPO MINATO:</h1>
                <Configurazione onClick={this.onClick} onKeyUp={this.onKeyUp} start={this.start}/>
                <Gioco checkButton={this.checkButton}/>
                <Conteggio onChange={this.onChange}  vinte={this.state.countW} perse={this.state.countL}/>
            </div>

        );
      }
  }
