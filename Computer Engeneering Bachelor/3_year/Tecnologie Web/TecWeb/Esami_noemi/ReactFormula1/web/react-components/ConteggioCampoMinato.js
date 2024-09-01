'use strict';

class Conteggio extends React.Component {
    render() {
    let vinte = this.props.vinte;
    let perse = this.props.perse;
    return (
    <div className="Conteggio">
       <div className="testoConteggio">
    	Riepilogo <input type="text" id="result" value={this.props.res}/>
    	</div>
    	<input type="button" id="reset" value="RESTART_GAME" onClick={this.props.reset}/>
    </div>
        );
    }
}