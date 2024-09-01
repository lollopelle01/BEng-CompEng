'use strict';

class Carrello extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name : ""
        }

        this.delete = this.delete.bind(this);
        this.inputHandler = this.inputHandler.bind(this);
    }

    inputHandler(e) {
        this.setState({
            name : e.target.value
        })
    }

    delete() {
        this.props.delete({name : this.state.name})
    }

    render() {
        return(
            <div>
                <ul>
                    {this.props.cart.map(p => 
                    <li>{"<Nome: " + p.nome + "> <Categoria: " + p.categoria + 
                    "> <Prezzo Unitario: " + p.prezzo + "> <Quantità: " + p.quantity + ">"}</li>
                    )}
                </ul>
                <label for="toDelete">Scrivi il nome di un articolo da eliminare</label> <br></br>
                <input type="text" id="toDelete" onChange={this.inputHandler}></input>
                <button onClick={this.delete}>Elimina articolo</button>
            </div>
        );
        
    }
}