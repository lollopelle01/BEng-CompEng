'use strict';

class App extends React.Component {
    constructor() {
        super();

        // Come verrà visto lo stato all'inizio
        this.state = {
            h : 0,
            l : 0,
            grid : [],
            vittorie : 0,
            sconfitte : 0
        }

        this.generate = this.generate.bind(this);
        this.play = this.play.bind(this);
    }

    // GENERA MATRICE h*l --> da implementare logica di creazione !
    generate({h, l, result}) {
        // Salvare punteggi per aggiornarli con result
        let win = this.state.vittorie;
        let lost = this.state.sconfitte;

        // Gestione aggiornamento punteggi in base a result
        if(result != undefined) { 
            if(result == "win") {
                win++;
            } else {
                lost++;
            }
        }

        // Creazione matrice
        let matrix = new Array(h);
        for(let i = 0; i < h; i++) {
            matrix[i] = new Array(l);
        }

        // Inizializzazione matrice
        for(let i = 0; i < h; i++) {
            for(let j = 0; j < l; j++) {
                // Logica molto personale
                // ...
            }
        }
        
        // Debug
        console.log("Nuova matrice generata");
        console.log(matrix)

        // Aggiorniamo stato
        this.setState({
            h : h,
            l : l,
            grid : matrix,
            vittorie : win,
            sconfitte : lost
        })
    }

    // GESTISCE UN EVENTO (pressione cella usually)
    play(e) {
        let flower = e.target.id;

        if(flower == 1) {
            $(e.target).css({
                "background-color" : "red"
            });
            alert("Game Over!");
            this.generate({mines : this.state.mines, size : this.state.size, result : "lose"});
        } else {
            $(e.target).css({
                "background-color" : "green"
            });

            if(this.state.tentativi == 4) {
                alert("You win!");
                this.generate({mines : this.state.mines, size : this.state.size, result : "win"});
            } else {
                this.setState({
                    tentativi : this.state.tentativi + 1
                });
            }
        }
    }

    // PRESENTAZIONE APP + PASSAGGIO FUNZIONI/PARAMETRI
    // 1) Dò a <Configurazione> la funzione per generare la matrice
    // 2) Dò al <Gioco> la matrice appena generata e la funzione per gestire la logica di gioco
    // 3) Dò a <Conteggio> i parametri da presentare o che necessitano di una elaborazione statica (senza controllare stato)
    render() {
        return (
            <div>
                <Configurazione generate={this.generate}/>
                <Gioco grid={this.state.grid} play={this.play}/>
                <Conteggio vittorie={this.state.vittorie} sconfitte={this.state.sconfitte}/>
            </div>
        );
    }
}

