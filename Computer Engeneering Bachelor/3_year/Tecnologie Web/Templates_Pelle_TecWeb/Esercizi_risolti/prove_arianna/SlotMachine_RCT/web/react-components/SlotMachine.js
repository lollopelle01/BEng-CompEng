'use strict';

class SlotMachine extends React.Component {
	
	render() {
	    return (
		    <div className="slotmachineDIV">
		    GIOCA: <input type="button" name="gioca" value="GIOCA" onClick={this.props.start}/><br/><br/>
		    
		    <input disable type="text" id="display1" name="display1" style='background-color:gray' />
		    <input disable type="text" id="display2" name="display2" style='background-color:gray' />
		    <input disable type="text" id="display3" name="display3" style='background-color:gray' />
		    
		    </div>
	    );
    }
}
