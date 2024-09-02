'use strict';

class Cell extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return(<td className="cell" style={{backgroundColor : "yellow"}} id={this.props.flower} onClick={this.props.play}></td>)
    }
}