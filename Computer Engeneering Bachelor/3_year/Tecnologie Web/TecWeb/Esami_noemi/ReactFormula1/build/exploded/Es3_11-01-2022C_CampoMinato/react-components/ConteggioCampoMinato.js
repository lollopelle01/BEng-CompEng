'use strict';

class Conteggio extends React.Component {
    render() {
    let vinte = this.props.vinte;
    let perse = this.props.perse;
    return (
    <div className="Conteggio">
       Counter vinte : <input type="text" id="vinte" value={this.props.vinte}/>
       Counter perse : <input type="text" id="perse" value={this.props.perse}/>
       <input type="button" id="reset" value="RESTART_GAME" onClick={this.props.reset}/>
    </div>
        );
    }
}