'use strict';

class Credito extends React.Component {

    render() {
	
		var creditoString = "Il tuo credito e' di: ".concat(this.props.credito);
	
        return (
            <div className="init"><br/>
               T-incremeto: <input type="text" id="incremento" name="incremento" size="3" required/><br/><br/>
               
               <input type="button" id="aggiungi" name="aggiungi" value="AGGIUNGI" onClick={this.props.aggiungi}/><br/><br/>
          
               T-credito: <input type="text" id="credito" name="credito" value={creditoString} size="3" readonly/><br/><br/>
            </div>
        );
    }
}
