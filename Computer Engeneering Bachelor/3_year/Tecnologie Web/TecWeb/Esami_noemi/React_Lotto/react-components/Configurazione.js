'use strict';

class Configurazione extends React.Component {

	
    render() {
    return (
        <div id="configurazione">
            <input type="button" id="start" value="Inizializza Scheda" onClick={this.props.inizializza}></input>
            <input type="text" id="c0" placeholder="num" size="2"></input>
            <input type="text" id="c1" placeholder="num" size="2"></input>
            <input type="text" id="c2" placeholder="num" size="2"></input>
            <input type="text" id="c3" placeholder="num" size="2"></input>
            <input type="text" id="c4" placeholder="num" size="2"></input>
            <input type="button" id="valida" value="Valida Scheda" onClick={this.props.valida}></input>
        </div>
    );
    }
}