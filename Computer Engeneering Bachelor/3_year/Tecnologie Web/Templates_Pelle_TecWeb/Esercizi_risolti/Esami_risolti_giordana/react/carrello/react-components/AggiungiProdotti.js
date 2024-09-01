'use strict';

class AggiungiProdotti extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            product : "",
            quantity : 0
        }

        this.selectHandler = this.selectHandler.bind(this);
        this.inputHandler = this.inputHandler.bind(this);
        this.click = this.click.bind(this);

    }

    selectHandler(e) {

        this.setState({
            product : e.target.value
        })
    }

    inputHandler(e) {
        let quantity = e.target.value;
        console.log(quantity);
        if(isNaN(quantity) || parseInt(quantity) < 1) {
            e.target.value = "";
            return;
        }

        this.setState({
            quantity : quantity
        })
    }

    click(e) {
        console.log(this.state.product);
        console.log(this.state.quantity);
        if(this.state.product == "" || this.state.quantity == 0){
            return;
        }
        this.props.add({product : this.state.product, quantity : this.state.quantity})
    }


    render() {
        return(
            <div>
                <label for="products">Prodotti</label> <br></br>
                <select id="products" onChange={this.selectHandler}>
                    <option value="">Selezionare un prodotto</option>
                    {this.props.prodotti.map(p => <option value={p.nome}>{p.nome}</option>)}
                </select> <br></br>
                <label for="quantity">Quantità</label> <br></br>
                <input type="text" name="quantity" id="quantity" onChange={this.inputHandler}></input> <br></br>
                <button onClick={this.click}>Aggiungi</button>
            </div>
        );
    }
}