'use strict';

class Risultati extends React.Component {
    
    render() {

	    return (
		    <div className="ris">
		       Lista delle giocate : <textArea id="textAreaRisultati" disabled ></textArea>
		       
		       <input type="button" id="reset" value="reset" onClick={this.props.reset} hidden ></input>
		    </div>
	    );
    }
}