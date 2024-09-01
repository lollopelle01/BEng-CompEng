'use strict';

// La cella 
function Cella(props) {
    // Elaboriamo colore in base a stato di quando viene creata
    let colore = //props.index == "CAR" ? (props.car == 1 ? "blue" : "green") : "black"

    return (<td className="cell" style={{backgroundColor : colore}} id={this.props.value} onClick={this.props.play}></td>);
}