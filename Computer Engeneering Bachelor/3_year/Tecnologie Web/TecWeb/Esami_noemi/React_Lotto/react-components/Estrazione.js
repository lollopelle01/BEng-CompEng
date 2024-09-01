'use strict';

class Estrazione extends React.Component {

	
    render() {
    return (
        <div id="estrazione">
            <input type="text" value={this.props.messaggio} disabled></input>
            <input type="button" id="startEstrazione" hidden="true" value="Start Estrazione" onClick={this.props.startEstrazione}></input>
            <input type="text" id="e0" size="2" disabled></input>
            <input type="text" id="e1" size="2" disabled></input>
            <input type="text" id="e2" size="2" disabled></input>
            <input type="text" id="e3" size="2" disabled></input>
            <input type="text" id="e4" size="2" disabled></input>
        </div>
    );
    }
}