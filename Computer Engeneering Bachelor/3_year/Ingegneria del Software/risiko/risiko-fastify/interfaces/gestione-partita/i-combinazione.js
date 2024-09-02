const Giocatore = require('../../entities/giocatore');
const Combinazione = require('../../entities/combinazione');

/**
 * @interface
 */
class ICombinazione {

    /**
     * This method returns all the {@linkcode Combinazione} available to the given {@linkcode giocatore}.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} to whom check all {@linkcode Combinazione}.
     * 
     * @returns {!Combinazione[]}
     * @abstract
     * */
    elencaCombinazioniValide(giocatore) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method uses the given {@linkcode combinazione} of the given {@linkcode giocatore}, returning the corresponding number of armies.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} who plays the {@linkcode combinazione}.
     * @param {!Combinazione} combinazione - The {@linkcode Combinazione} played by the {@linkcode giocatore}
     * 
     * @returns {!number}
     * @abstract
     * */
    utilizzaCombinazione(giocatore, combinazione) {
        throw new Error('Must be implemented by subclass!');
    }

}

module.exports = ICombinazione
