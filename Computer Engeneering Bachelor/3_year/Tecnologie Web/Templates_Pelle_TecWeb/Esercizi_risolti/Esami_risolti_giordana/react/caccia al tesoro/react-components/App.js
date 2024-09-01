'use strict';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            lunghezza : 0,
            larghezza : 0,
            effettuati : 0,
            matrix : [],
            points : 0,
            finish : false
        }

        this.gioca = this.gioca.bind(this);
        this.reveal = this.reveal.bind(this);
    }

    gioca({lunghezza, larghezza}) {
        let grid = new Array(lunghezza);
        for(let i = 0; i < lunghezza; i++){
            grid[i] = new Array(larghezza);
            for(let j = 0; j < larghezza; j++) {
                grid[i][j] = {
                    row : i,
                    col : j,
                    teasure: false,
                    reveal : false
                }
            }
        }
        let row_teasure = Math.floor(Math.random() * lunghezza);
        let col_teasure = Math.floor(Math.random() * larghezza);
        grid[row_teasure][col_teasure] = {
            row : row_teasure,
            col : col_teasure,
            teasure: true,
            reveal : false
        }

        this.setState({
            lunghezza : lunghezza,
            larghezza : larghezza,
            matrix : grid
        })
    }

    reveal({row, col}) {
        let grid = this.state.matrix;

        if(!grid[row][col].reveal && !this.state.finish){
            grid[row][col].reveal=true;

            if(grid[row][col].teasure==true) {
                alert("Gioco terminato!");
                
                //rivelo tutte le caselle
                for(let i = 0; i < lunghezza; i++){
                    for(let j = 0; j < larghezza; j++) {
                        grid[i][j].reveal = true;
                    }
                }

                let punti = 0;
    
                if(this.state.effettuati <= 10){
                    punti = 5;
                }
                else{
                    punti = 2;
                }
    
                this.setState({
                    matrix : grid,
                    finish : true,
                    points: punti
                })
                return;
            }

            this.setState({
                matrix : grid,
                effettuati : this.state.effettuati + 1
            })
        }
    }

    render() {
        return (
            <div>
                <Configurazione play={this.gioca}/>
                <Lago matrix={this.state.matrix} reveal={this.reveal}/>
                <Punteggio points={this.state.points} finish={this.state.finish} effettuati={this.state.effettuati}/>
            </div>
        );
    }
}

