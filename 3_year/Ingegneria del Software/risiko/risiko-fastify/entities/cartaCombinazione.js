const Carta = require("./carta");
const Figura = require("./figura");

/**
 * @abstract
 */
class CartaCombinazione extends Carta {

    figura;

    /**
     * @param {!number} id 
     * @param {!Figura} figura 
     */
    constructor(id, figura) {
        super(id);
        this.figura = figura;
    }
}

module.exports = CartaCombinazione;
