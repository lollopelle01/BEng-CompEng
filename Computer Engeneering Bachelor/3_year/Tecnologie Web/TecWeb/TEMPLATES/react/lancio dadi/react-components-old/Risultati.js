'use strict';

class Risultati extends React.Component {
    constructor(props) {
        super(props)
    }

    render() { 
        return(<div id="r">
            
                <table>
                    <tr>
                        <th>slot 1</th> 
                        <th>slot 2</th>
                        <th>slot 3</th>
                        <th>Vincita</th>
                    </tr>
                        {this.props.results.map(r =><tr><td>{r.uno}</td><td>{r.due}</td><td>{r.tre}</td><td>{r.guadagno != 0 ? r.guadagno : ""}</td></tr>)}

                </table>
            
        </div>);
    }
}