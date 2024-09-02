const Azione = require('./azione');
const CartaCombinazione = require('./cartaCombinazione');

class Combinazione extends Azione {

    carteCombinazione;

    /** 
     * @param {CartaCombinazione[]} carteCombinazione 
     * @param {string} descrizione 
     */
    constructor(carteCombinazione, descrizione) {
        super(descrizione);
        this.carteCombinazione = carteCombinazione;
    }
}

module.exports = Combinazione;
