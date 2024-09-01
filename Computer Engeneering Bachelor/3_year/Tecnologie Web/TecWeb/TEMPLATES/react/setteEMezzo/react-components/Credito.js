'use strict';

class Credito extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            incremento: 0
        }

        this.handleInput = this.handleInput.bind(this);
        this.gioca = this.gioca.bind(this);
    }

    handleInput(e) {
        let num = e.target.value;
        if(isNaN(num) || !Number.isInteger(Number(num)) || num<=0) {
            alert("Valore non ammissibile")
            return;
        }

        this.setState({
            incremento: num
        })

        console.log("scritto valore: " + num);
    }

    gioca() {
        console.log("Gioca")
        if(this.state.incremento > 0 ) {
            this.props.genera({incremento : this.state.incremento})
        }
    }

    render() {
        return(<div id="c">
            <h2>Credito</h2>
            <label for="incremento">Incremento</label> <br></br>
            <input type="text" id="incremento" onChange={this.handleInput} placeholder="incremento"></input> <br></br>
            <label for="credito">Credito</label> <br></br>
            <input type="text" id="credito" readonly="readonly" value={this.props.cred}></input> <br></br>
            <button onClick={this.gioca}>AGGIORNA CREDITO</button>
        </div>);
    }
}