'use strict';

class Conto extends React.Component {

    constructor(props) {
        super(props);
    }


    render() {
        let total = 0;
        this.props.cart.map(p => {
            total += p.quantity * p.prezzo
        })
        return(<div>
            <label for="conto"></label>
            <input type="text" id="conto" style={{textAlign: "right"}} readOnly={true} value={total}/>
        </div>)
    }
}