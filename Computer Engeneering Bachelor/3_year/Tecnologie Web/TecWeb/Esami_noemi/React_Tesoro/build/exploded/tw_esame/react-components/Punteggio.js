'use strict';

class Punteggio extends React.Component {

    render() {
    return (
        <div id="punteggio">
        Tentativi: {this.props.tentativi}
        Punteggio: {this.props.punteggio}
        </div>
        );
    }
}