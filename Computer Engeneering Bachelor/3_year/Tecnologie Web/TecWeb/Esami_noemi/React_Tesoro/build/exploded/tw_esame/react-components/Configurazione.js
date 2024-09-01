'use strict';

class Configurazione extends React.Component {

    render() {
    return (
        <div id="configurazione">
        <input id="larg" onChange={this.props.validate}></input>
        <input id="lung" onChange={this.props.validate}></input>
        </div>
        );
    }
}