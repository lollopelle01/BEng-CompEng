'use strict';

class Punteggio extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        // let total = this.props.punti1 + this.props.punti2;
        // let winner = 0;
        // if(total == 100) {
        //     winner = this.props.punti1 > this.props.punti2 ? 1 : 2
        // }
        return(
            <div>
                <table>
                    <tr>
                        <th>INTESTAZIONE 1</th>
                        <th>INTESTAZIONE 2</th>
                    </tr>
                    <tr>
                        <td>{this.props.punti1}</td>
                        <td>{this.props.punti2}</td>
                    </tr>
                </table>
                <div>
                    {/* {winner == 0 ? "" : "Il vincitore è" + (winner == 1 ? "MACCHINA 1" : "MACCHINA 2")}
                    <br></br>
                    
                    {winner == 0 ? false : <button onClick={this.props.reset}>RESET</button>} */}
                </div>
            </div>
        );
    }
}