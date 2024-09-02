'use strict';

class Configurazione extends React.Component {
	
	// ai campi di input associo un event handler che controlla l'input
	// al campo bottone associo un event handler che setterà tutta la parte di gioco
	
    render() {
        return (
            <div className="conf"><br/>
               Altezza: <input type="text" name="alt" onKeyUp={this.props.onKeyUp} size="3"/><br/><br/>
               Larghezza: <input type="text" name="larg" onKeyUp={this.props.onKeyUp} size="3"/><br/><br/>
               
               START: <input type="button" name="start" onClick={this.props.start}/><br/><br/>
            </div>
        );
    }
}
