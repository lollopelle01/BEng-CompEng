'use strict';

class App extends React.Component {
    constructor() {
        super();
        let gare = prompt("Inserire il numero di gare: ");
        this.state = {
            gare : gare,
            punti_1 : 0,
            punti_2 : 0
        }

        this.updateRank = this.updateRank.bind(this);
        this.reset = this.reset.bind(this);
    }

    updateRank({winner}) {
        if(winner == 1) {
            this.setState({
                punti_1 : this.state.punti_1 + 10
            })
        } else if(winner == 2) {
            this.setState({
                punti_2 : this.state.punti_2 + 10
            })
        }
    }

    reset() {
        let gare = prompt("Inserire il numero di gare: ");
        this.setState({
            gare : gare,
            punti_1 : 0,
            punti_2 : 0
        })
    }

    render() {
        return(
            <div>
                <Pista gare={this.state.gare} updateRank={this.updateRank}/>
                <Classifica punti1={this.state.punti_1} punti2={this.state.punti_2} reset={this.reset}/>
            </div>
        );
    }
}

