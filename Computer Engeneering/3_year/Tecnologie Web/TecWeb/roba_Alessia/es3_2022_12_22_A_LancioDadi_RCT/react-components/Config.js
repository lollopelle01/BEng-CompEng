'use strict';

class Config extends React.Component {
    constructor(props) {
        super(props)

        this.rollDice = this.rollDice.bind(this);
    }

    rollDice() {
        
        var bottoni = document.getElementsByClassName("dadi");
        for(var i = 0; i < bottoni.length; i++) {
            bottoni[i].value = Math.floor(Math.random()*6) + 1;
        }
        
        bottoni[0].innerHTML = bottoni[0].value
        bottoni[1].innerHTML = bottoni[1].value
        bottoni[2].innerHTML = bottoni[2].value

        for(var i = 0; i < 3; i++) {
            if(bottoni[i].value <= 3) {
                $("#d"+(i+1)).css({
                    "background-color" : "red"
                });
            } else {
                $("#d"+(i+1)).css({
                    "background-color" : "green"
                });
            }
        }
        
        this.props.setter({da1: bottoni[0], da2: bottoni[1], da3: bottoni[2]})
    }

    render() {
        return(
        <div>
            <h4>SEZIONE LANCIO DEI DADI</h4>
            <table id="config">
                <tr>
                    <td><button id="d1" class="dadi" readonly></button></td>
                    <td><button id="d2" class="dadi" readonly></button></td>
                    <td><button id="d3" class="dadi" readonly></button></td>
                    <td><button onClick={this.rollDice}>Lancia i dadi</button></td>
                </tr>
            </table>
        </div>);
    }


}