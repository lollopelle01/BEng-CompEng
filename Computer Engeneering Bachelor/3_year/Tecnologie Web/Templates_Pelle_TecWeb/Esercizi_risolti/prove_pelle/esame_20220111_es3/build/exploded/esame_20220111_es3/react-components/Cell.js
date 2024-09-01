'use strict';

class Cell extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return(<td className="cell" style={{backgroundColor : "grey", height:"30px", width:"30px" }} id={this.props.flower} onClick={this.props.play}></td>)
    }
}