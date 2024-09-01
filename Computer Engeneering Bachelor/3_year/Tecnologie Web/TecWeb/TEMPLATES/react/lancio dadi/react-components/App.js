'use strict';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            dadi: [0, 0, 0],
            sequenza: [],
            fuori: [],
            somma1: 0,
            somma2: 0,
            media: 0,
            somme_lista: [],
            somme_fuori: []
        }

        this.play = this.play.bind(this);
        this.reset = this.reset.bind(this);
        this.calcola = this.calcola.bind(this);
    }

    play(){
        let dadi = this.state.dadi;
        let sequenza = this.state.sequenza;
        let fuori = this.state.fuori;
        let somma =0;
        let somme = this.state.somme_lista;
        let somme_fuori = this.state.somme_fuori;

        console.log("nuovo lancio");

        for(let i=0; i<3; i++){
            let rand = Math.floor(Math.random() * 6) + 1;

            dadi[i]=rand;
            somma+=rand;
        }
        
        let result = {Dado1: dadi[0], Dado2: dadi[1], Dado3: dadi[2]}

        if(somma>=6 && somma <=15){
            sequenza.push(result);
            somme.push(somma);
        }else{
            fuori.push(result);
            somme_fuori.push(somma);
        }

        this.setState({
            dadi: dadi,
            sequenza: sequenza,
            fuori: fuori, 
            somme_lista: somme
        })
    }

    reset(){
        this.setState({
            dadi: [0, 0, 0],
            sequenza: [],
            fuori: [],
            somma1: 0,
            somma2: 0,
            media: 0
        })
    }

    calcola(){
        console.log("nuova richiesta di calcolo");
        let somme_lista = this.state.somme_lista

        let max = 0;
        let min = 0;
        if(somme_lista.length != 0){
            max = somme_lista.reduce((a, b) => {
                return a>=b ? a : b;
              });
            min = somme_lista.reduce((a, b) => {
                return a<=b ? a : b;
              });
        }

        let somme_fuori = this.state.somme_fuori;
        let somma = 0;
        let average = 0;

        if(somme_fuori.length != 0){
            somma = somme_fuori.reduce((a, b) => {
                return a + b;
              });
    
            average = somma/somme_fuori.length;
        }
        

        this.setState({
            somma1: max,
            somma2: min,
            media: average
        })
    }

    render() {
        return (
            <div>
                <Lancio dadi={this.state.dadi} play={this.play}/>
                <Sequenza results={this.state.sequenza}/>
                <Statistica somma1={this.state.somma1} somma2={this.state.somma2} media={this.state.media} reset={this.reset} visualizza={this.calcola}/>
            </div>
        );
    }
}

