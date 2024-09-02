'use strict';

class Cell extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return(<td className="cell" style={{backgroundColor : "grey"}} id={this.props.tesoro} onClick={this.props.play}></td>)
    }
}