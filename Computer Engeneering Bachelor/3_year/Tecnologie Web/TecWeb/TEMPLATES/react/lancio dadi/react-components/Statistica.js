'use strict';

class Statistica extends React.Component {
    constructor(props) {
        super(props)

    }

    render() {
        return(<div id="st">
            <button id="visualizza" onClick={this.props.visualizza}>CALCOLA</button><br></br>
            
            <label for="somma1">La piu grande somma generata da una tripletta in lista</label>
            <input type="text" id="somma1" readonly="readonly" value={this.props.somma1==0 ? "" : this.props.somma1}></input><br></br>

            <label for="somma2">La piu piccola somma generata da una tripletta in lista</label>
            <input type="text" id="somma2" readonly="readonly" value={this.props.somma2==0 ? "" : this.props.somma2}></input><br></br>

            <label for="media">La media aritmetica delle somme delle triplette fuori lista</label>
            <input type="text" id="media" readonly="readonly" value={this.props.media==0 ? "" : this.props.media}></input><br></br>
            
            <button id="RESET" onClick={this.props.reset}>RESET</button>
        </div>)
    }
}