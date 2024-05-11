'use strict';

class Configurazione extends React.Component {
    constructor(props) {
        super(props)

        this.generateMatrix = this.generateMatrix.bind(this);
    }

    generateMatrix() {
        let altezza = $("#altezza").val(); console.log(altezza);
        let larghezza = $("#larghezza").val(); console.log(larghezza);

        if(isNaN(altezza) || parseInt(altezza) < 5){
            $("#altezza").val("");
            return;
        }

        if(isNaN(larghezza) || parseInt(larghezza) < 5) {
            $("#larghezza").val("");
            return;
        }

        if(altezza == "" || larghezza == "")
            return;

        this.props.generate({h : altezza, l : larghezza})
    }

    render() {
        return(<div>
            <table>
                <tr>
                    <td><label>Altezza griglia</label></td>
                    <td><label>Larghezza griglia</label></td>
                </tr>
                <tr>
                    <td><input type="text" id="altezza" placeholder="Altezza (a > 5)" onBlur={this.generateMatrix}></input></td>
                    <td><input type="text" id="larghezza" placeholder="Larghezza (l > 5)" onBlur={this.generateMatrix}></input></td>
                </tr>
            </table>
        </div>);
    }
}