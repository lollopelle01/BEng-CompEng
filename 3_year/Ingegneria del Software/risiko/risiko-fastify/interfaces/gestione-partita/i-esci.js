const Giocatore = require('../../entities/giocatore');

/**
 * @interface
 */
class IEsci {

    /**
     * This method lets the {@linkcode giocatore} quit the game of Risk.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} wanting to quit the game of Risk.
     * 
     * @returns {void}
     * @abstract
     * */
    esci(giocatore) {
        throw new Error('Must be implemented by subclass!');
    }

}

module.exports = IEsci
