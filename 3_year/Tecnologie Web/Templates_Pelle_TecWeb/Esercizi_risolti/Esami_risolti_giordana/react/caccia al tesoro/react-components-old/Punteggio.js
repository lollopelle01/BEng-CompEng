'use strict';

class Punteggio extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        let tot = this.props.finish ? this.props.points.reduce((sum, num) => sum + num, 0) : 0;
        let average = this.props.finish ? tot*1.0/this.props.points.length : 0; 
        return(<div>
            <table>
                {this.props.points.map((punteggio, index) => <tr><td>Lancio {index + 1}</td><td>{punteggio}</td></tr>)}
            </table>
            {
                this.props.finish ? 
                <table>
                    <tr>
                        <th>Punteggio totale</th> 
                        <th>Punteggio medio</th>
                    </tr>
                    <tr>
                        <td>{tot}</td>
                        <td>{average}</td>
                    </tr>
                </table> : ""
            }
        </div>);
    }
}