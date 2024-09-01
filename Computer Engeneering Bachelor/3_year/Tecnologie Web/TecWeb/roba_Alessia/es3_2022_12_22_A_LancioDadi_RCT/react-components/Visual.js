'use strict';

class Visual extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        var html = "";
        var numLanci = this.props.numLanci;
       
        if(numLanci > 0) {
        var numeri = this.props.okList;
            for(var i = 0; i < numeri.length; i++) {
                html =  '<table>'+
                            '<tr>'+
                                '<td width="150px"><b>Lancio numero'+(i+1)+'</b></td>'+
                                '<td width="100px">Dado1: '+(numeri[i][0])+'</td>'+
                                '<td width="100px">Dado2: '+(numeri[i][1])+'</td>'+
                                '<td width="100px">Dado3: '+(numeri[i][2])+'</td>'+
                            '</tr>'+
                        '</table>';
            }
            document.getElementById("visual").innerHTML += html;
        }
        
        

        return(
            <div id="visual">
                <hr/>
            </div>
        );
    }
}