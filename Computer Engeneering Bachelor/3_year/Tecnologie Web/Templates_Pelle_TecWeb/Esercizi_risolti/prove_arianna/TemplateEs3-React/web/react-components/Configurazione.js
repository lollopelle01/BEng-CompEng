'use strict';

class Configurazione extends React.Component {
	
    render() {
	
		var creditoString = "Il tuo credito e' di: ".concat(this.props.credito);
	
        return (
            <div className="config"><br/>
               Input 1: <input type="text" id="input1" name="input1" onKeyUp={this.props.onKeyUp} size="3"/><br/><br/>
               Input 2: <input disabled type="text" id="input2" name="input2" value={creditoString} size="3"/><br/><br/>
               
               START: <input type="button" id="start" name="start" onClick={this.props.start}/><br/><br/>
            </div>
        );
    }
}
