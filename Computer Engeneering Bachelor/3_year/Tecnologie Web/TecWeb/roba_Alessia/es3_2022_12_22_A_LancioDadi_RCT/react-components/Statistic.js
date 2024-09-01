'use strict';

class Statistic extends React.Component {
    constructor(props) {
        super(props)

        this.stat = this.stat.bind(this);
        this.res = this.res.bind(this);
    }

    stat() {
        
        var top = $("#top");
        var flop = $("#flop");
        var average = $("#average");
        
        var okList = this.props.okList;
        var flopList = this.props.flopList;
    
        var topSomma = 0;
        var flopSomma = parseInt(okList[0][0]) + parseInt(okList[0][1]) + parseInt(okList[0][2]);;
        var somma = flopSomma;

        for(var i = 0; i < okList.length; i++) {
            if(somma > topSomma) {
                topSomma = somma;
            }
            if(flopSomma > somma) {
                flopSomma = somma;
            }
            somma = parseInt(okList[i][0]) + parseInt(okList[i][1]) + parseInt(okList[i][2]);
        }

        top.val(topSomma);
        flop.val(flopSomma);
        
        var media = 0;
        somma = 0;

        for(var i = 0; i < flopList.length; i++) {
            somma += parseInt(flopList[i][0]) + parseInt(flopList[i][1]) + parseInt(flopList[i][2]);
        }
        if(somma >= 0) {
            somma = (somma/flopList.length).toFixed(2);
            average.val(somma);
        } else {
            average.val("Non ci sono lanci nella fuori lista");
        }
    }

    res() {
        this.props.reset({});
    }

    render() {
        return(
        <div>
            <h4>SEZIONE STATISTICA</h4>
            <table id="stat">
                <tr>
                    <td width="150px">Top somma della lista Triplette </td>
                    <td width="150px">Flop somma della lista Triplette</td>
                    <td width="150px">Media della Fuori Lista</td>
                </tr>
                <tr>
                    <td><input id="top" class="stat" readonly></input></td>
                    <td><input id="flop" class="stat" readonly></input></td>
                    <td><input id="average" class="stat" readonly></input></td>
                    <td><button onClick={this.stat}>Ottieni statistiche</button></td>
                    <td><button onClick={this.res}>RESETTA</button></td>
                </tr>
            </table>
        </div>);
    }


}