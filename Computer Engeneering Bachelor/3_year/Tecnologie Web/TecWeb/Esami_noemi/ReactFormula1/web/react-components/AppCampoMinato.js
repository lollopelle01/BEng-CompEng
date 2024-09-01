'use strict';

var mina1;     //le due mine 
var mina2;
var m;       //larghezza, mi serve per le mine
class App extends React.Component {
    constructor(){
            super();
            this.state = {
            posA: 1,
            posB: 1,
            puntR: 0,
            puntB: 0,
            res:""
            }
            this.start = this.start.bind(this);       //inizio partita
            this.reset=this.reset.bind(this);
            
    }


 start() {
	 for(var i=0;i<4;i++){
	 while(this.state.posA<9 && this.state.posB<9){
		 setTimeout(myTimer,5000);
	 }
	 while(this.state.posA==this.statePosB){
		 this.setState({
			 posA:posA+getRandomInt(3)+1,
			 posB:posB+getRandomInt(3)+1
		 });
	 }
	 if(this.state.posA>this.statePosB){
		 this.setState({
			 puntR:puntR+10,
			 res: res+"Vittoria rosso\n"
		 });
	 }else{
		 this.setState({
			 puntB:puntB+10,
			 res: res+"Vittoria blu\n"
		 });
	 }
	 document.getElementById("9r").style.textColor = 'black';
	 document.getElementById("9b").style.textColor = 'black';
	 document.getElementById("9r").value = 'arrivo';
	 document.getElementById("9b").value = 'arrivo';
	 if(this.state.puntA>this.state.puntB){
		 document.getElementsByClassName("testoConteggio")[0].innerHTML = "<br>Ha vinto il rosso<br>";
	 }else{
		 document.getElementsByClassName("testoConteggio")[0].innerHTML = "<br>Ha vinto il blu<br>";
	 }
	
 	}
 };
 
 myTimer(){
	 document.getElementById(posA+"r").style.textColor = 'black';
	 document.getElementById(posB+"b").style.textColor = 'black';
	 if(posA==1){
		 document.getElementById(posA+"r").value = 'partenza';
	 }else{
		 document.getElementById(posA+"r").value = '';
	 }
	 if(posB==1){
		 document.getElementById(posB+"b").value = 'partenza';
	 }else{
		 document.getElementById(posB+"b").value = '';
	 }
	 this.setState({
		 posA:posA+getRandomInt(3)+1,
		 posB:posB+getRandomInt(3)+1
	 });
	 if(this.state.posA<9){
	 document.getElementById(posA+"r").style.textColor = 'red';
	 document.getElementById(posA+"r").value = 'macchina';
	 }else{
		 document.getElementById("9r").style.textColor = 'red';
		 document.getElementById("9r").value = 'macchina'
	 }
	 if(this.state.posA<9){
	 document.getElementById(posB+"b").style.textColor = 'blue';
	 document.getElementById(posB+"b").value = 'macchina';
	 }else{
		 document.getElementById("9b").style.textColor = 'blue';
		 document.getElementById("9b").value = 'macchina'
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
        this.state = {
        posA: 1,
        posB: 1,
        puntR: 0,
        puntB: 0,
        res:""
        }
};

  render() {
        return (

            <div className="app">
                <h1>CAMPO MINATO:</h1>
                <Configurazione start={this.start}/>
                <Gioco />
                <Conteggio onChange={this.onChange}  res={this.state.res}/>
            </div>

        );
      }
  }
