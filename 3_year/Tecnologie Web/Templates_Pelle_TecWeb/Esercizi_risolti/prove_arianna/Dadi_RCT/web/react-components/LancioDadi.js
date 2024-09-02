'use strict';

class LancioDadi extends React.Component {
	
	render() {
	    return (
		    <div className="dadiDIV">
		    
		    	<input disable type="text" id="dado1" name="dado1" style='background-color:gray' />
		    	<input disable type="text" id="dado2" name="dado2" style='background-color:gray' />
		    	<input disable type="text" id="dado3" name="dado3" style='background-color:gray' />
		    	
		    	START: <input type="button" id="start" name="start" onClick={this.props.start}/><br/><br/>
		    
		    </div>
	    );
    }
}
