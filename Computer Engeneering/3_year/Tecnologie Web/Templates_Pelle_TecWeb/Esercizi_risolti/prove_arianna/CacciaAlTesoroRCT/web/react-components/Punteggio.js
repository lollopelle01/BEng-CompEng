'use strict';

class Punteggio extends React.Component {
    
    render() {

	    return (
		    <div className="Punteggio">
		       Tentativi infruttuosi : <input type="text" id="tent" value={this.props.tent}/>
		    </div>
	    );
    }
}