'use strict';

class Risultati extends React.Component {
    
    render() {

	    return (
		    <div className="Conteggio">
		       Tentativi vincenti : <input type="text" id="vinte" value={this.props.vinte} readonly/>
		       Tentativi falliti : <input type="text" id="perse" value={this.props.perse} readonly/>
		       
		       <p>{this.props.fine} </p><br></br>
		       <input type="button" id="reset" value="RESTART_GAME" onClick={this.props.reset}/>
		       
		    </div>
	    );
    }
}