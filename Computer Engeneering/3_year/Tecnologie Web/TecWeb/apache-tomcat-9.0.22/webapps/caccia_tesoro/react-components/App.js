'use strict';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            h : 0,
            l : 0,
            grid : [],
            tentativi : 0
        }

        this.generate = this.generate.bind(this);
        this.play = this.play.bind(this);
    }

    generate({h, l, result}) {
        let tentativi = this.state.tentativi;
        if(result != undefined) {
            tentativi++;
        }

        let matrix = new Array(h);
        for(let i = 0; i < h; i++) {
            matrix[i] = new Array(l);
        }

        let h_estratta = Math.round(Math.random()*h);
        let l_estratta = Math.round(Math.random()*l);
        matrix[h_estratta][l_estratta] = "T";
        for(let i = 0; i < h; i++) {
            for(let j = 0; j < l; j++) {
                if(matrix[i][j] != "T"){matrix[i][j] = 0;}
            }
        }
        
        console.log("Nuova matrice generata");
        console.log(matrix)

        this.setState({
            h : h,
            l : l,
            grid : matrix,
            tentativi : tentativi
        })
    }

    play(e) {
        let valore = e.target.id;

        if(valore == "T") {
            $(e.target).css({
                "background-color" : "blue"
            });
            $(e.target).val("T");

            var punteggio
            if (this.state.tentativi <= 10) {punteggio = 5;}
            else {punteggio = 2;}

            alert("Hai vinto! Per te " + punteggio + " punti");
            this.generate({mines : this.state.mines, size : this.state.size, result : "fine"});
        } else {
            $(e.target).css({
                "background-color" : "yellow"
            });

            this.setState({
                tentativi : this.state.tentativi + 1
            });
        }
    }

    render() {
        return (
            <div>
                <Configurazione generate={this.generate}/>
                <Gioco grid={this.state.grid} play={this.play}/>
                <Conteggio tentativi={this.state.tentativi}/>
            </div>
        );
    }
}

