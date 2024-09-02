'use strict';

class VisualizzaSchedina extends React.Component {

    constructor(props) {
        super(props)
    }

    render() {
        console.log("Vincenti: " + this.props.vincenti)
        let result = this.props.result;
        let color = "white";

        switch (result) {
            case 'Ambo':
                color="yellow"
              break;
            case 'Terno':
                color="green"
              break;
            case 'Quaterna':
                color="blue"
                break;
            case 'Cinquina':
                color="red"
                break;
            default:
              console.log("Colore incalcolabile");
        }
        return(
            <div className="visualizza-schedina">
                <div className="result-div">{this.props.result}</div>
                {this.props.schedina.map(element => <input type="text" style={this.props.vincenti.includes(element) ? {backgroundColor: color} : {backgroundColor: "white"}} id="number" value={element} className="numeroSchedina" readonly></input>)}
            </div>
        );
    }
}