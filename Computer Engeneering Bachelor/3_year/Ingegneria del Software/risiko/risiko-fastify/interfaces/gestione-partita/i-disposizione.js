const Giocatore = require('../../entities/giocatore');
const Disposizione = require('../../entities/disposizione');
const Territorio = require('../../entities/territorio');

/**
 * @interface
 */
class IDisposizione {

    /**
     * This method executes the given {@linkcode disposizione} requested by the given {@linkcode giocatore}.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} requesting to execute the given {@linkcode disposizione}.
     * @param {!Disposizione} disposizione - The {@linkcode Disposizione} requested to be executed by the given {@linkcode giocatore}.
     * 
     * @returns {!Territorio}
     * @abstract
     * */
    effettuaDisposizione(giocatore, disposizione) {
        throw new Error('Must be implemented by subclass!');
    }

}

module.exports = IDisposizione
