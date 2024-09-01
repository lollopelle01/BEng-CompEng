'use strict';

function Casella(props) {
    let colore = props.index == "CAR" ? (props.car == 1 ? "blue" : "green") : "black"
    return (<td style={{color : colore}}>{props.index == 0 ? "Partenza" : props.index == 10 ? "Traguardo" : props.index}</td>);
}