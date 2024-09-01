'use strict';

class Gioco extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return(<div>
            <table className="grid">
                {this.props.grid.map(row => <tr> {row.map(cell => <Cell tesoro={cell} play={this.props.play}/>)} </tr>)}
            </table>
        </div>);
    }
}