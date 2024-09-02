'use strict';

class Configurazione extends React.Component {
    constructor(props) {
        super(props)

        this.generateMatrix = this.generateMatrix.bind(this);
    }

    generateMatrix() {
        let larghezza = $("#larghezza").val();
        let lunghezza = $("#lunghezza").val();

        if(isNaN(larghezza) || parseInt(larghezza) < 5){
            $("#larghezza").val("");
            return;
        }

        if(isNaN(lunghezza) || parseInt(lunghezza) < 5){
            $("#lunghezza").val("");
            return;
        }

        if(larghezza == "" || lunghezza == "")
            return;

        this.props.generate({h : larghezza, l : lunghezza})
    }

    render() {
        return(<div>
            <table>
                <tr>
                    <td><label>Larghezza griglia</label></td>
                    <td><label>Lunghezza griglia</label></td>
                </tr>
                <tr>
                    <td><input type="text" id="larghezza" placeholder="larghezza > 4" onBlur={this.generateMatrix}></input></td>
                    <td><input type="text" id="lunghezza" placeholder="lunghezza > 4" onBlur={this.generateMatrix}></input></td>
                </tr>
            </table>
        </div>);
    }
}