'use strict';

class Pista extends React.Component {

    constructor(props){
        super(props);

        this.state = {
            pos1 : 0,
            pos2 : 0,
            current_winner : 0,
            turno : 1
        }

        this.move = this.move.bind(this);
        this.update = this.update.bind(this);
        this.newRace = this.newRace.bind(this);
    }

    move() {
        this.interval = setInterval(() => this.update(), 5000);
    }

    update() {
        let offset1 = Math.ceil(Math.random() * 3);
        let offset2 = Math.ceil(Math.random() * 3);

        let new_pos_1 = this.state.pos1 + offset1;
        let new_pos_2 = this.state.pos2 + offset2;
        let winner = 0;

        if(new_pos_1 >= 10 && new_pos_2 < 10) {
            clearInterval(this.interval);
            winner = 1;
            new_pos_1 = 0;
            new_pos_2 = 0;
        } else if(new_pos_2 >= 10 && new_pos_1 < 10) {
            clearInterval(this.interval);
            winner = 2;
            new_pos_1 = 0;
            new_pos_2 = 0;
        } else if(new_pos_2 >= 10 && new_pos_2 >= 10) {
            clearInterval(this.interval);
            winner = (new_pos_1 > new_pos_2 ? 1 : (new_pos_2 > new_pos_2 ? 2 : 0));
            if(winner == 0) {
                do {
                    new_pos_1 += Math.ceil(Math.random() * 3);
                    new_pos_2 += Math.ceil(Math.random() * 3);
                    winner = (new_pos_1 > new_pos_2 ? 1 : (new_pos_2 > new_pos_1 ? 2 : 0));
                } while(winner == 0);
                new_pos_1 = 0;
                new_pos_2 = 0;
            }
        }

        console.log("Winner: " + winner);
        if(winner != 0) {
            this.setState({
                pos1 : new_pos_1,
                pos2 : new_pos_2,
                winner : winner,
                turno : this.state.turno + 1
            })
            this.newRace(winner)
        } else {
            this.setState({
                pos1 : new_pos_1,
                pos2 : new_pos_2,
                winner : winner
            })
        }    
    }

    newRace(winner) {
        this.props.updateRank({winner : winner})
        if(this.state.turno <= this.props.gare) {
            alert("Inizia il turno " + this.state.turno)
            this.move()
        } else {
            this.setState({
                pos1 : 0,
                pos2 : 0,
                winner : 0,
                turno : 1
            })
        }
    }

    render() {
        return(
            <div>
                <table>
                    <Percorso pos={this.state.pos1} car={1}/>
                    <Percorso pos={this.state.pos2} car={2}/>
                    <button onClick={this.move}>AVVIA</button>
                </table>
                
            </div>
        );
    }
}