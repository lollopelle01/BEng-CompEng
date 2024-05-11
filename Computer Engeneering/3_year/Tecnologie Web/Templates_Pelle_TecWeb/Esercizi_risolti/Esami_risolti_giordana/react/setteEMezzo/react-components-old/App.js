'use strict';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            credito: 0,
            grid: ['', '', ''],
            risultati: []
        }

        this.genera = this.genera.bind(this);
        this.play = this.play.bind(this);
    }

    genera({incremento}) {
        let cred = this.state.credito*1 + incremento*1;

        this.setState({
            credito: cred
        })

        console.log("incremento effettuato: attuale credito="+ cred);
    }

    play(){
        let vocali = "aeiou";
        let cred = this.state.credito;
        let slot = this.state.grid;
        let results = this.state.risultati;

        console.log("nuova slot");

        if(cred>=5){
            for(let i=0; i<3; i++){
                let rand = vocali.charAt(Math.floor(Math.random() * vocali.length));
    
                slot[i]=rand;
            }
            
            let vincita=0;
            if(slot[0] == slot[1] && slot[0]==slot[2]){
                cred += 45;
                vincita=50;
            }
            else{
                if(slot[0]==slot[1] || slot[0]==slot[2] || slot[1]==slot[2]){
                    cred += 10;
                    vincita=15;
                }
                else{
                    cred -= 5;
                    vincita = 0;
                }
            }

            let result = {uno: slot[0], due: slot[1], tre: slot[2], guadagno: vincita}
            results.push(result);

            this.setState({
                credito: cred,
                grid: slot,
                risultati: results
            })
        }
        else{
            alert("RICARICA IL CONTO PEZZENTE");
        }
    }

    render() {
        return (
            <div>
                <Credito genera={this.genera} cred={this.state.credito}/>
                <Slot play={this.play} slot={this.state.grid}/>
                <Risultati results={this.state.risultati}/>
            </div>
        );
    }
}

