const OpzioniPartita = require('../../entities/opzioniPartita');
const Lobby = require('../../entities/lobby');
const Utente = require('../../entities/utente');

/**
 * @abstract
 */
class IMatchListener {

    /**
     * This method is called whenever one or more {@linkcode Utente} are found to populate the given {@linkcode lobby} ensuring the given {@linkcode opzioniPartita} are satisfied.
     * 
     * @param {!OpzioniPartita} opzioniPartita - The {@linkcode OpzioniPartita} of the given {@linkcode lobby}.
     * @param {!Lobby} lobby - The {@linkcode Lobby} possessing the given {@linkcode opzioniPartita}.
     * 
     * @returns {void} 
     * @abstract
     * */
    onMatch(opzioniPartita, lobby) {
        throw new Error('Must be implemented by subclass!');
    }
}

module.exports = IMatchListener   
