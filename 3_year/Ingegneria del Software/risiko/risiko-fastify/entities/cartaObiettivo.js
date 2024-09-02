const Carta = require("./carta");
const Giocatore = require("./giocatore");
const PartitaCominciata = require("./partitaCominciata");


/**
 * @abstract
 */
class CartaObiettivo extends Carta {
    testo

    /**
     * @param {!number} id 
     * @param {!string} testo 
     */
    constructor(id, testo) {
        super(id);
        this.testo = testo;
    }
    
    /**
     * This method checks if the objective has been completed.
     * 
     * @param {!Giocatore} giocatore 
     * @param {!PartitaCominciata} partiteCominciata 
     * @returns {boolean}
     * @abstract
     */
    checkConseguimento(giocatore, partiteCominciata) {
        throw new Error('Must be implemented by subclass!');
    }
}

module.exports = CartaObiettivo