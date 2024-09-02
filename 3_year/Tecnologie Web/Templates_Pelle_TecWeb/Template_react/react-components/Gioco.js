'use strict';

class Gioco extends React.Component {
    constructor(props) {
        super(props)

        // Può necessitare di stato se gioco molto complesso
        // this.state = {
        //     h : 0,
        //     l : 0,
        //     grid : [],
        //     vittorie : 0,
        //     sconfitte : 0
        // }
    }

    // Implementa la griglia inserendo una Cella in ogni cella della matrice
    // Alla Cella vengono passati il contenuto della cella logica e la logica di gioco 
    render() {
        return(<div>
            <table className="grid">
                {this.props.grid.map(row => <tr> {row.map(cell => <Cella value={cell} play={this.props.play}/>)} </tr>)}
            </table>
        </div>);
    }
}