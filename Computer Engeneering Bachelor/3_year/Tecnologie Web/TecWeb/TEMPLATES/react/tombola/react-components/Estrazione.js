'use strict';

class Estrazione extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
        <div className="visualizza-schedina">
            {this.props.estratti.length == 0 ? <button onClick={this.props.estrai}>ESTRAI</button> : <div></div>}
            <br></br>
            {this.props.estratti.map(element => <input type="text" id="number" value={element} className="numeroSchedina" readonly></input>)}
        </div>
        )
    }
}