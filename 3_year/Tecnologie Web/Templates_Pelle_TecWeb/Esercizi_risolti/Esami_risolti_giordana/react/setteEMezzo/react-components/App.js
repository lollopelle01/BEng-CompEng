'use strict';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            credito: 0,
            carte: [{value:1, seme:"mazze", uscita:false, nascosta:false}, {value:2, seme:"mazze", uscita:false, nascosta:false}, {value:3, seme:"mazze", uscita:false, nascosta:false}, {value:4, seme:"mazze", uscita:false, nascosta:false}, {value:5, seme:"mazze", uscita:false, nascosta:false}, {value:6, seme:"mazze", uscita:false, nascosta:false}, {value:7, seme:"mazze", uscita:false, nascosta:false}, {value:8, seme:"mazze", uscita:false, nascosta:false}, {value:9, seme:"mazze", uscita:false, nascosta:false}, {value:10, seme:"mazze", uscita:false, nascosta:false},
                     {value:1, seme:"coppe", uscita:false, nascosta:false}, {value:2, seme:"coppe", uscita:false, nascosta:false}, {value:3, seme:"coppe", uscita:false, nascosta:false}, {value:4, seme:"coppe", uscita:false, nascosta:false}, {value:5, seme:"coppe", uscita:false, nascosta:false}, {value:6, seme:"coppe", uscita:false, nascosta:false}, {value:7, seme:"coppe", uscita:false, nascosta:false}, {value:8, seme:"coppe", uscita:false, nascosta:false}, {value:9, seme:"coppe", uscita:false, nascosta:false}, {value:10, seme:"coppe", uscita:false, nascosta:false},
                     {value:1, seme:"spade", uscita:false, nascosta:false}, {value:2, seme:"spade", uscita:false, nascosta:false}, {value:3, seme:"spade", uscita:false, nascosta:false}, {value:4, seme:"spade", uscita:false, nascosta:false}, {value:5, seme:"spade", uscita:false, nascosta:false}, {value:6, seme:"spade", uscita:false, nascosta:false}, {value:7, seme:"spade", uscita:false, nascosta:false}, {value:8, seme:"spade", uscita:false, nascosta:false}, {value:9, seme:"spade", uscita:false, nascosta:false}, {value:10, seme:"spade", uscita:false, nascosta:false},
                     {value:1, seme:"denari", uscita:false, nascosta:false}, {value:2, seme:"denari", uscita:false, nascosta:false}, {value:3, seme:"denari", uscita:false, nascosta:false}, {value:4, seme:"denari", uscita:false, nascosta:false}, {value:5, seme:"denari", uscita:false, nascosta:false}, {value:6, seme:"denari", uscita:false, nascosta:false}, {value:7, seme:"denari", uscita:false, nascosta:false}, {value:8, seme:"denari", uscita:false, nascosta:false}, {value:9, seme:"denari", uscita:false, nascosta:false}, {value:10, seme:"denari", uscita:false, nascosta:false}],
            carteU: [{value:1, seme:"mazze", uscita:false, nascosta:false}, {value:2, seme:"mazze", uscita:false, nascosta:false}, {value:3, seme:"mazze", uscita:false, nascosta:false}, {value:4, seme:"mazze", uscita:false, nascosta:false}, {value:5, seme:"mazze", uscita:false, nascosta:false}, {value:6, seme:"mazze", uscita:false, nascosta:false}, {value:7, seme:"mazze", uscita:false, nascosta:false}, {value:8, seme:"mazze", uscita:false, nascosta:false}, {value:9, seme:"mazze", uscita:false, nascosta:false}, {value:10, seme:"mazze", uscita:false, nascosta:false},
                     {value:1, seme:"coppe", uscita:false, nascosta:false}, {value:2, seme:"coppe", uscita:false, nascosta:false}, {value:3, seme:"coppe", uscita:false, nascosta:false}, {value:4, seme:"coppe", uscita:false, nascosta:false}, {value:5, seme:"coppe", uscita:false, nascosta:false}, {value:6, seme:"coppe", uscita:false, nascosta:false}, {value:7, seme:"coppe", uscita:false, nascosta:false}, {value:8, seme:"coppe", uscita:false, nascosta:false}, {value:9, seme:"coppe", uscita:false, nascosta:false}, {value:10, seme:"coppe", uscita:false, nascosta:false},
                     {value:1, seme:"spade", uscita:false, nascosta:false}, {value:2, seme:"spade", uscita:false, nascosta:false}, {value:3, seme:"spade", uscita:false, nascosta:false}, {value:4, seme:"spade", uscita:false, nascosta:false}, {value:5, seme:"spade", uscita:false, nascosta:false}, {value:6, seme:"spade", uscita:false, nascosta:false}, {value:7, seme:"spade", uscita:false, nascosta:false}, {value:8, seme:"spade", uscita:false, nascosta:false}, {value:9, seme:"spade", uscita:false, nascosta:false}, {value:10, seme:"spade", uscita:false, nascosta:false},
                     {value:1, seme:"denari", uscita:false, nascosta:false}, {value:2, seme:"denari", uscita:false, nascosta:false}, {value:3, seme:"denari", uscita:false, nascosta:false}, {value:4, seme:"denari", uscita:false, nascosta:false}, {value:5, seme:"denari", uscita:false, nascosta:false}, {value:6, seme:"denari", uscita:false, nascosta:false}, {value:7, seme:"denari", uscita:false, nascosta:false}, {value:8, seme:"denari", uscita:false, nascosta:false}, {value:9, seme:"denari", uscita:false, nascosta:false}, {value:10, seme:"denari", uscita:false, nascosta:false}],
            carteD: [],
            carteG: [],
            countG: 0,
            risultati: [],
            finish: true
        }

        this.genera = this.genera.bind(this);
        this.play = this.play.bind(this);
        this.stai = this.stai.bind(this);
        this.chiedi = this.chiedi.bind(this);
    }

    genera({incremento}) {
        let cred = this.state.credito*1 + incremento*1;

        this.setState({
            credito: cred
        })

        console.log("incremento effettuato: attuale credito="+ cred);
    }

    play(){
        //resetta le variabili
        let cred = this.state.credito;
        let carte = this.state.carte;
        let carted = [];
        let carteg = [];


        console.log("nuova partita");

        if(cred>=5 && this.state.finish){
            //bisogna assegnare una carta al dealer e una carta al player
                let carta = Math.floor(Math.random() * carte.length);
                
                while(carte[carta].uscita){
                    carta = Math.floor(Math.random() * carte.length);
                }


                carte[carta].nascosta= true;
                carte[carta].uscita = true;

                carted.push(carte[carta]);
            

            //e contemporaneamente calcoliamo la somma delle carte uscite al giocatore
            let somma = 0;

                let carta2 = Math.floor(Math.random() * carte.length);
                
                while(carte[carta2].uscita){
                    carta2 = Math.floor(Math.random() * carte.length);
                }

                carte[carta2].uscita = true;

                carteg.push(carte[carta2]);

                if(carteg[0].value >7 ){
                    somma += 0.5;
                }else{
                    somma += carteg[0].value;
                }
            
                cred -= 5;

            this.setState({
                credito: cred,
                carteD: carted,
                carteG: carteg,
                carteU: carte,
                countG: somma,
                finish: false
            })
        }
        else{
            if(cred<5){
                alert("RICARICA IL CONTO PEZZENTE");
            }else{
                alert("FINISCI LA PARTITA IN CORSO");
            }
            
        }
    }

    chiedi(){
        //Bisogna aggiungere una carta al player
        let somma = this.state.countG;
        let carte = this.state.carteU;
        let carteg = this.state.carteG;
        let finish = this.state.finish;
        let carted = this.state.carteD;
        let results = this.state.risultati;

        if(finish){
            alert("partita finita, iniziane un altra trmon");
        }else{
            let carta2 = Math.floor(Math.random() * carte.length);
                
            while(carte[carta2].uscita){
                carta2 = Math.floor(Math.random() * carte.length);
            }

            carte[carta2].uscita = true;

            carteg.push(carte[carta2]);

            if(carte[carta2].value >7 ){
                somma += 0.5;
            }else{
                somma += carte[carta2].value;
            }

            let result;

            if(somma > 7.5){
                finish = true;
                alert("HAI SBALLATO HAHAHAHA");

                for(let i=0; i< carted.length; i++){
                    carted[i].nascosta=false;
                }

                result = {punteggio: somma, risultato: "sconfitta"};
                results.push(result);
            }
            
            this.setState({
                carteG: carteg,
                carteU: carte,
                countG: somma,
                finish: finish,
                carteD: carted,
                risultati: results
            })
        }

    }

    stai(){

        //quando si chiede di stare bisogna controllare che la partita non sia finita
        let somma = this.state.countG;
        let carte = this.state.carteU;
        let carted = this.state.carteD;
        let finish = this.state.finish;
        let cred = this.state.credito;

        if(finish){
            alert("partita finita, iniziane un altra trmon");
        }else{
            //se non è finita allora il banco scopre la sua carta e chiede carte fino a quando non vince o sballa
            carted[0].nascosta=false;

            let sommad = carted[0].value > 7 ? 0.5 : carted[0].value;

            //il dealer pesca carte finche la sua somma è minore di quella del player senza sballare
            while((sommad<somma && sommad<=7.5)){
                let carta = Math.floor(Math.random() * carte.length);
                
                while(carte[carta].uscita){
                    carta = Math.floor(Math.random() * carte.length);
                }

                carte[carta].uscita = true;

                carted.push(carte[carta]);

                sommad += carte[carta].value > 7 ? 0.5 : carte[carta].value;
            }

            let result;
            //usciti dal while controlliamo il caso di uscita
            if(sommad>7.5){
                alert("IL BANCO HA SBALLATO, COMPLIMENTI PER LA VITTORIA");
                cred +=10;
                result = {punteggio: somma, risultato: "vittoria"};

            }else{
                if(sommad==7.5 && sommad==somma){
                    alert("HAI PAREGGIATO COL BANCO, SUCA");
                    cred +=5;
                    result = {punteggio: somma, risultato: "pareggio"};
                }else{
                    alert("IL BANCO HA VINTO CON " + sommad);
                    result = {punteggio: somma, risultato: "sconfitta"};
                }
            }

             let results = this.state.risultati;
             results.push(result);

            this.setState({
                credito: cred,
                carteU: carte,
                carteD: carted,
                finish: true,
                risultati: results
            })
        }
    }

    render() {
        return (
            <div>
                <Credito genera={this.genera} cred={this.state.credito}/>
                <Tavolo play={this.play} chiedi={this.chiedi} stai={this.stai} carteD={this.state.carteD} carteG={this.state.carteG} credito={this.state.credito} countG={this.state.countG}/>
                <Risultati results={this.state.risultati}/>
            </div>
        );
    }
}

