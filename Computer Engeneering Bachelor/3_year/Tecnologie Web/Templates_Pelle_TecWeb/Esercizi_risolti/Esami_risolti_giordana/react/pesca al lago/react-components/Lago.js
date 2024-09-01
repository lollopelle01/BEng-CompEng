'use strict';

class Lago extends React.Component {
    constructor(props) {
        super(props)

    }

    render() {
        return(<div>
            <table>
                {this.props.matrix.map(row => <tr>{row.map(cell =><Cella reveal={this.props.reveal} info={cell}/>)}</tr>)}
            </table>
        </div>)
    }
}