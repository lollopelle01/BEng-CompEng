const Giocatore = require('../../entities/giocatore');
const Spostamento = require('../../entities/spostamento');
const TuplaTerritori = require('../../entities/tupla-territori');

/**
 * @interface
 */
class ISpostamento {

    /**
     * This method executes the given {@linkcode spostamento} requested by the given {@linkcode giocatore}.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} requesting to execute the given {@linkcode spostamento}.
     * @param {!Spostamento} spostamento - The {@linkcode Spostamento} requested to be executed by the given {@linkcode giocatore}.
     * 
     * @returns {!TuplaTerritori}
     * @abstract
     * */
    effettuaSpostamento(giocatore, spostamento) {
        throw new Error('Must be implemented by subclass!');
    }

}

module.exports = ISpostamento
