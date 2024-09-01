'use strict';

class Cell extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return(<td className="cell"><button class="bottone" name="bottone" id={this.props.id} onClick={this.props.play}></button></td>)
    }
}