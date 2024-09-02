const Giocatore = require('../../entities/giocatore');
const Attacco = require('../../entities/attacco');
const TuplaTerritori = require('../../entities/tupla-territori');

/**
 * @interface
 */
class IAttacco {

    /**
     * This method executes the given {@linkcode attacco} requested by the given {@linkcode giocatore}.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} launching the {@linkcode attacco}.
     * @param {!Attacco} attacco - The {@linkcode Attacco} launched by the {@linkcode giocatore}
     * 
     * @returns {!TuplaTerritori}
     * @abstract
     * */
    effettuaAttacco(giocatore, attacco) {
        throw new Error('Must be implemented by subclass!');
    }

}

module.exports = IAttacco
