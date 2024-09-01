'use strict';

class Gioco extends React.Component {
	
	//posso avere gia dei campi che vado a modificare in value e in color per esempio
	
	render() {
	    return (
		    <div className="giocoDIV">
		    
		    	<input disable type="text" id="display1" name="display1" style='background-color:gray' />
		    	<input disable type="text" id="display2" name="display2" style='background-color:gray' />
		    	<input disable type="text" id="display3" name="display3" style='background-color:gray' />
		    
		    </div>
	    );
    }
}
