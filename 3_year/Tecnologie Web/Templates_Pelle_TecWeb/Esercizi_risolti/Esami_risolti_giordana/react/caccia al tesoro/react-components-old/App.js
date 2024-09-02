'use strict';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            lunghezza : 0,
            larghezza : 0,
            lanci : 0,
            effettuati : 0,
            matrix : [],
            points : [],
            finish : false
        }

        this.gioca = this.gioca.bind(this);
        this.reveal = this.reveal.bind(this);
    }

    gioca({lunghezza, larghezza, lanci}) {
        let grid = new Array(lunghezza);
        for(let i = 0; i < lunghezza; i++){
            grid[i] = new Array(larghezza);
            for(let j = 0; j < larghezza; j++) {
                grid[i][j] = {
                    row : i,
                    col : j,
                    val : Math.ceil(Math.random()*5),
                    reveal : false
                }
            }
        }

        this.setState({
            lunghezza : lunghezza,
            larghezza : larghezza,
            lanci : lanci,
            matrix : grid
        })
    }

    reveal({row, col}) {
        let grid = this.state.matrix;

        if(this.state.effettuati == this.state.lanci) {
            alert("Gioco terminato!");
            for(let i = 0; i < this.state.lunghezza; i++) {
                for(let j = 0; j < this.state.larghezza; j++) {
                    grid[i][j].reveal = true;
                }
            }
            this.setState({
                matrix : grid,
                finish : true
            })
            return;
        }
        

        let points = 0;

        if(grid[row][col] != null && !grid[row][col].reveal) {
            grid[row][col].reveal = true;
            points += grid[row][col].val;
        }

        if(grid[row][col+1] != null && !grid[row][col+1].reveal) {
            grid[row][col + 1].reveal = true;
            points += grid[row][col + 1].val;
        }

        if(grid[row + 1] != null && grid[row + 1][col] != null && !grid[row + 1][col].reveal) {
            grid[row + 1][col].reveal = true;
            points += grid[row + 1][col].val;
        }

        if(grid[row - 1] != null && grid[row - 1][col] != null && !grid[row - 1][col].reveal) {
            grid[row - 1][col].reveal = true;
            points += grid[row - 1][col].val;
        }

        if(grid[row][col - 1] != null && !grid[row][col - 1].reveal) {
            grid[row][col - 1].reveal = true;
            points += grid[row][col - 1].val;
        }

        console.log(points);
        let punti = this.state.points;
        punti.push(points);
        this.setState({
            matrix : grid,
            effettuati : this.state.effettuati + 1,
            points : punti
        })
    }

    render() {
        return (
            <div>
                <Configurazione play={this.gioca}/>
                <Lago matrix={this.state.matrix} reveal={this.reveal}/>
                <Punteggio points={this.state.points} finish={this.state.finish}/>
            </div>
        );
    }
}

