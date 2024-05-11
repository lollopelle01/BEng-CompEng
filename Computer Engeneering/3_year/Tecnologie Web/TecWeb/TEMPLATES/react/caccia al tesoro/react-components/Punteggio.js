'use strict';

class Punteggio extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return(<div>
                <label>Tentativi effettuati: {this.props.effettuati}</label> <br></br>
            {
                this.props.finish ? <label>Punteggio ottenuto: {this.props.points}</label> : ""
            }
        </div>);
    }
}