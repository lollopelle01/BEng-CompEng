'use strict';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            schedina : [],
            estratti : [],
            result : "",
            vincenti : []
        }

        this.generate = this.generate.bind(this);
        this.estrai = this.estrai.bind(this);
    }

    generate({numbers}) {
        let newSchedina = []
        numbers.map(number => {
            if(number == "" || isNaN(number) || parseInt(number) < 1 || parseInt(number) > 10) {
                alert("Numeri inseriti non corretti!");
                return;
            }
            console.log(number);
            newSchedina.push(number);
        })

        console.log(newSchedina);

        this.setState({
            schedina: newSchedina,
        })
    }

    estrai() {
        let numeri = []
        for(let i = 0; i < 5; i++) {
            let n;
            do {
                n = Math.ceil(Math.random() * 10);
                console.log(n);
            } while(numeri.includes(n));

            numeri.push(n);
        }

        this.setState({
            estratti: numeri
        })

        //Controllo vincita
        let counter = 0;
        let numvincenti = [];
        for(let numero of this.state.schedina) {
            if(numeri.includes(parseInt(numero))) {
                counter++;
                numvincenti.push(numero);
            }
        }

        let results = ["Perdente", "Estratto", "Ambo", "Terno", "Quaterna", "Cinquina"];
        console.log("Numeri vincenti: " + numvincenti);
        this.setState({
            result : results[counter],
            vincenti : numvincenti
        })
    }

    render() {
        return (
            <div>
                <GeneraSchedina generate={this.generate}/>  
                <VisualizzaSchedina schedina={this.state.schedina} result={this.state.result} vincenti={this.state.vincenti}/>
                <Estrazione estratti={this.state.estratti} estrai={this.estrai} />
            </div>
        );
    }
}

