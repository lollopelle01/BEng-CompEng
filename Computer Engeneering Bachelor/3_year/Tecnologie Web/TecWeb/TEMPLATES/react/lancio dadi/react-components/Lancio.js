'use strict';

class Lancio extends React.Component {
    constructor(props) {
        super(props)

    }

    render() {
        return(<div id="l">
            <button id="gioca" onClick={this.props.play}>SCOMMETTI</button>
            <table>
                <tr>{this.props.dadi.map(element => <input type="text" id="dado" style={{backgroundColor: element==0 ? "white" : element<=3 ? "red" : "green"}} readonly="readonly" value={element==0 ? "" : element}></input>)}</tr>
            </table>
        </div>)
    }
}