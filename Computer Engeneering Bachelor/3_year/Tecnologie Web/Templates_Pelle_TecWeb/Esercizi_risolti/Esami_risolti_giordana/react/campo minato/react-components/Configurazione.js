'use strict';

class Configurazione extends React.Component {
    constructor(props) {
        super(props)

        this.generateMatrix = this.generateMatrix.bind(this);
    }

    generateMatrix() {
        let size = $("#grid-size").val();
        let mines = $("#mines-num").val();

        if(isNaN(size) || parseInt(size) < 0 || parseInt(size) > 10){
            $("#grid-size").val("");
            return;
        }

        if(isNaN(mines) || parseInt(mines) < 0) {
            $("#mines-num").val("");
            return;
        }

        if(size == "" || mines == "")
            return;
        
        if(Math.pow(size, 2) < mines){
            alert("Il numero di mine deve essere minore di d^2!");
            $("#mines-num").val("");
            return;
        }

        this.props.generate({mines : mines, size : size})
    }

    render() {
        return(<div>
            <table>
                <tr>
                    <td><label>Dimensione griglia</label></td>
                    <td><label>Numero di mine</label></td>
                </tr>
                <tr>
                    <td><input type="text" id="grid-size" placeholder="Dimensione d" onChange={this.generateMatrix}></input></td>
                    <td><input type="text" id="mines-num" placeholder="Mine n (n < d^2)" onChange={this.generateMatrix}></input></td>
                </tr>
            </table>
        </div>);
    }
}