'use strict';

class Configurazione extends React.Component {
    constructor(props) {
        super(props)

        this.generateMatrix = this.generateMatrix.bind(this);
    }

    generateMatrix() {
        let h = $("#altezza").val();
        let l = $("#larghezza").val();

        if(isNaN(h) || parseInt(h) < 0 || !Number.isInteger(h)){
            $("#altezza").val("");
            return;
        }

        if(isNaN(l) || parseInt(l) < 0 || !Number.isInteger(l)){
            $("#larghezza").val("");
            return;
        }

        if(h == "" || l == "")
            return;

        this.props.generate({h : h, l : l})
    }

    render() {
        return(<div>
            <table>
                <tr>
                    <td><label>Dimensione griglia</label></td>
                    <td><label>Numero di mine</label></td>
                </tr>
                <tr>
                    <td><input type="text" id="altezza" placeholder="altezza" onBlur={this.generateMatrix}></input></td>
                    <td><input type="text" id="lunghezza" placeholder="lunghezza" onBlur={this.generateMatrix}></input></td>
                </tr>
            </table>
        </div>);
    }
}