'use strict';

class Tavolo extends React.Component {
    constructor(props) {
        super(props)

    }

    render() {
        return(<div id="t">
            <button id="gioca" onClick={this.props.play}>DISTRIBUISCI</button>
            <div id="dealer">
            <table>
                <tr>{this.props.carteD.map(element => <input type="text" id="cella" style={{backgroundColor: element.nascosta ? "black" : element.seme=='denari' ? "yellow" : element.seme=="spade" ? "blue" : element.seme=="coppe" ? "red" : "green"}} readonly value={element.nascosta ? "" : element.value}></input>)}</tr>
            </table>
            </div>
           
            <div id="giocatore">
            <br></br>
            <table>
                <tr>{this.props.carteG.map(element => <input type="text" id="cella" style={{backgroundColor: element.seme=='denari' ? "yellow" : element.seme=="spade" ? "blue" : element.seme=="coppe" ? "red" : "green"}} readonly value={element.value}></input>)}</tr>
            </table>
            <br></br>
            <label>IL TUO PUNTEGGIO:</label><br></br>
            <input readonly value={this.props.countG}></input>
            <br></br>
            <button onClick={this.props.chiedi}>CHIEDI</button>
            <button onClick={this.props.stai}>STAI</button>
            </div>
            
        </div>)
    }
}