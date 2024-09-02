'use strict';

class Configurazione extends React.Component {
	
    render() {
        return (
            <div className="config"><br/>
               Larghezza: <input type="text" name="larg" onKeyUp={this.props.onKeyUp} size="3"/><br/><br/>
               Lunghezza: <input type="text" name="lung" onKeyUp={this.props.onKeyUp} size="3"/><br/><br/>
               
               START: <input type="button" name="start" onClick={this.props.start}/><br/><br/>
            </div>
        );
    }
}
