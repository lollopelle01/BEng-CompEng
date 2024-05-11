'use strict';

class Configurazione extends React.Component {
    render() {
        return (
            <div className="conf"><br/>
                START: <input type="button" name="start" onClick={this.props.start}/><br/><br/>
            </div>
        );
    }
}
