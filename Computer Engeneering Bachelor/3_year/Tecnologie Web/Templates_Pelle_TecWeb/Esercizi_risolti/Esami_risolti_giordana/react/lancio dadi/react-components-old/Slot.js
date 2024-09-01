'use strict';

class Slot extends React.Component {
    constructor(props) {
        super(props)

    }

    render() {
        return(<div id="s">
            <button id="gioca" onClick={this.props.play}>SCOMMETTI</button>
            <table>
                <tr>{this.props.slot.map(element => <input type="text" id="cella" style={{backgroundColor: element=='' ? "white" : element=='a' ? "green" : element=='e' ? "green" : "blue"}} readonly value={element}></input>)}</tr>
            </table>
        </div>)
    }
}