'use strict';

class Config extends React.Component {
    constructor(props) {
        super(props)

        this.generateMatrix = this.generateMatrix.bind(this);
    }

    generateMatrix() {

        let larghezza = $("#largh").val();
        let lunghezza = $("#lung").val();

        if(isNaN(larghezza) || isNaN(lunghezza)){
            $("#largh").val("");
            $("#lung").val("");
            return;
        }

        if(larghezza == "" || lunghezza == "")
            return;

        this.props.setter({larg: larghezza, lung: lunghezza})
    }

    render() {
        return(
        <div>
            <h6>IMPOSTAZIONI PER GIOCARE</h6>
            <table id="config">
                <tr>
                    <td>Larghezza del lago:</td>
                    <td><input type="number" name="info" id="largh" min="5" placeholder="Larghezza" onChange={this.generateMatrix}></input></td>
                </tr>
                <tr>
                    <td>Numero mine:</td>
                    <td><input type="number" name="info" id="lung" min="5" placeholder="Lunghezza" onChange={this.generateMatrix}></input></td>
                </tr>
            </table>
        </div>);
    }


}