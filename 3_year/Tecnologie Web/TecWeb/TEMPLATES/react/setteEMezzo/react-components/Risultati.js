'use strict';

class Risultati extends React.Component {
    constructor(props) {
        super(props)
    }

    render() { 
        return(<div id="r">
            
                <table>
                    <tr>
                        <th>PUNTEGGIO</th> 
                        <th>VITTORIA?</th>
                    </tr>
                        {this.props.results.map(r =><tr><td>{r.punteggio}</td><td>{r.risultato}</td></tr>)}

                </table>
            
        </div>);
    }
}