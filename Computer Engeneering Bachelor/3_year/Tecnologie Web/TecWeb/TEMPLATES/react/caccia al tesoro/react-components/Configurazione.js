'use strict';

class Configurazione extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            larghezza : 0, 
            lunghezza : 0
        }

        this.handleInput = this.handleInput.bind(this);
        this.gioca = this.gioca.bind(this);
    }

    handleInput(e) {
        let num = e.target.value;
        let target = e.target.id;
        if(isNaN(num) || !Number.isInteger(Number(num)) || (target == "lunghezza" && num < 5) || (target == "lunghezza" && num < 5)) {
            e.target.value = "";
            alert("Valore non ammissibile")
            return;
        }

        if(target == "lunghezza") {
            this.setState({
                lunghezza : num
            })
        } else if(target == "larghezza") {
            this.setState({
                larghezza : num
            })
        } 

    }

    gioca() {
        console.log("Gioca")
        if(this.state.lunghezza != 0 && this.state.larghezza != 0) {
            this.props.play({lunghezza : this.state.lunghezza, larghezza : this.state.larghezza})
        }
    }

    render() {
        return(<div>
            <h2>CONFIGURAZIONE</h2>
            <label for="lunghezza">Lunghezza</label> <br></br>
            <input type="text" id="lunghezza" onChange={this.handleInput} placeholder="lunghezza"></input> <br></br>
            <label for="larghezza">Larghezza</label> <br></br>
            <input type="text" id="larghezza" onChange={this.handleInput} placeholder="larghezza"></input> <br></br>
            <button onClick={this.gioca}>GIOCA</button>
        </div>);
    }
}