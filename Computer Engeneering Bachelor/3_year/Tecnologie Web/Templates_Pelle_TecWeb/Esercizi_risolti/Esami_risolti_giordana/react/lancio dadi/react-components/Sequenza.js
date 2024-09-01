'use strict';

class Sequenza extends React.Component {
    constructor(props) {
        super(props)
    }

    render() { 
        return(<div id="s">
            
                <ul>
                {this.props.results.map(r =><li>Dado 1:{r.Dado1} - Dado 2:{r.Dado2} - Dado 3:{r.Dado3}</li>)}
                </ul>
            
        </div>);
    }
}