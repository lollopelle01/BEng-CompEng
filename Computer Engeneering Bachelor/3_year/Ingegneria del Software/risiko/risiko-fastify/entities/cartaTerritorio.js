const CartaCombinazione = require("./cartaCombinazione");
const Figura = require("./figura");

class CartaTerritorio extends CartaCombinazione {
    nomeTerritorio;

    /**
     * @param {!number} id 
     * @param {!Figura} figura 
     * @param {!string} nomeTerritorio 
     */
    constructor(id, figura, nomeTerritorio){
        super(id, figura);
        this.nomeTerritorio = nomeTerritorio;
    }
}

module.exports = CartaTerritorio