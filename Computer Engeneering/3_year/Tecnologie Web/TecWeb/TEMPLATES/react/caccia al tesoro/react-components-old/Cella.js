'use strict';

class Cella extends React.Component {
    constructor(props) {
        super(props)

        this.show = this.show.bind(this);
    }

    show() {
        console.log("Row: " + this.props.info.row + " Col: " + this.props.info.col);
        this.props.reveal({row : this.props.info.row, col : this.props.info.col})
    }

    render() {
        return(<td style={{backgroundColor : "grey", textAlign : "center"}} onClick={this.show}> {this.props.info.reveal ? this.props.info.val : ""} </td>);
    }
}