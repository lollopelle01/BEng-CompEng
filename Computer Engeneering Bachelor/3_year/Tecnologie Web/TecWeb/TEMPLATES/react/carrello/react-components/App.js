'use strict';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            prodotti : [{
                nome : "Pane",
                categoria : "Alimentare",
                prezzo : 1.10
            },{
                nome : "Acqua",
                categoria : "Alimentare",
                prezzo : 0.70
            },{
                nome : "Maglietta",
                categoria : "Abbigliamento",
                prezzo : 15
            },{
                nome : "iPhone 14 plus",
                categoria : "Tecnologia",
                prezzo : 1500
            },{
                nome : "iPad Pro Max",
                categoria : "Tecnologia",
                prezzo : 954.99
            },{
                nome : "Jeans",
                categoria : "Abbigliamento",
                prezzo : 25
            }],
            cart : []
        }

        this.add = this.add.bind(this);
        this.delete = this.delete.bind(this);
    }

    add({product, quantity}) {
        console.log("Aggiungo prodotto al carrello...")
        let prodotto;
        this.state.prodotti.map(p => {
            if(p.nome == product) {
                prodotto = p;
            }
        })

        prodotto.quantity = quantity;
        console.log(prodotto);

        let cart = this.state.cart;
        cart.push(prodotto);
        this.setState({
            cart : cart
        })
    }

    delete({name}) {
        let newCart = [];
        this.state.cart.map(p => {
            if(p.nome != name) {
                newCart.push(p);
            }
        })

        this.setState({
            cart : newCart
        })
    }

    render() {
        return(
            <div>
                <AggiungiProdotti prodotti={this.state.prodotti} add={this.add}/>
                <Carrello cart={this.state.cart} delete={this.delete}/>
                <Conto cart={this.state.cart} />
            </div>
        );
    }
}

