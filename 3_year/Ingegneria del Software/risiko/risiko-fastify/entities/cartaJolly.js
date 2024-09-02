const CartaCombinazione = require("./cartaCombinazione");
const Figura = require("./figura");

class CartaJolly extends CartaCombinazione {
    testo;
    
    /**
     * @param {!number} id 
     * @param {!Figura} figura 
     * @param {!string} testo 
     */
    constructor(id, figura, testo){
        super(id, figura);
        this.testo = testo;
    }
}

module.exports = CartaJolly