'use strict';

class Statitica extends React.Component {
	
    render() {
	
        return (
            <div className="stat"><br/>
            
            	Somma max: <input disable type="text" id="sumMax" name="sumMax" />
		    	Somma min: <input disable type="text" id="sumMin" name="sumMin"  />
		    	Media dei fuori lista: <input disable type="text" id="media" name="media"  />
		    	
		    	VISUALIZZA: <input type="button" id="visualizza" name="visualizza" onClick={this.props.visualizza}/><br/><br/>
		    	
		    	<input type="button" id="reset" value="RESTART_GAME" onClick={this.props.reset} hidden/>

            </div>
        );
    }
}
