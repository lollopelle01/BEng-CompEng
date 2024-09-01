'use strict';

class Grid extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        
        let matrix = []
        
        let row;
        for(var i = 0; i < this.props.columns; i++){
            row = [];
            for(var j=0 ; j < this.props.rows; j++){
                row.push(`${i}${j}`)
            }
            matrix.push(row);
        }

        return (
            <div id="gioco">
                <table id="griglia">
                    {matrix.map((row => ( <tr id={'r'+row[0]}> {row.map(cellId => <Cell id={cellId} play={this.props.play}></Cell>)} </tr>)))}
                </table>
                <br/>
                <button id="Resetta" onClick={this.props.reset}>Reset</button> 
            </div>            
        );
    }
}