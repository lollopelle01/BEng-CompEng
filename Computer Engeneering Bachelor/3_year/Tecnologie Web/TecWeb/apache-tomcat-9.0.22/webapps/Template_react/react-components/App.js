'use strict';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            num_gare_tot: 0,
            num_gare_eff: 0,
            pos_macchina1: 1,
            pos_macchina2: 1,
        }

        this.generate = this.generate.bind(this);
        this.play = this.play.bind(this);
    }

    render() {
        return (
            <div>
                <Inserimento generatePiste={this.generate}/>
                <Gioco grid={this.state.grid} play={this.play}/>
                <Conteggio vittorie={this.state.vittorie} sconfitte={this.state.sconfitte}/>
            </div>
        );
    }
}

