'use strict';

class Conteggio extends React.Component {
    
    render() {

	    return (
		    <div className="Conteggio">
		       Punti per lancio : <input type="text" id="lista" value={this.props.listaPunti}/>
		       
		    </div>
	    );
    }
}