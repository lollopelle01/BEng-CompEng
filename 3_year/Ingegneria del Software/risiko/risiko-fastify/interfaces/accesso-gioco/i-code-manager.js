const Utente = require('../../entities/utente');
const OpzioniPartita = require('../../entities/opzioniPartita');
const Lobby = require('../../entities/lobby');

/**
 * @abstract
 */
class ICodeManager {

    /**
     * This method adds the given {@linkcode utente} to the corresponding {@linkcode Lobby} of the given {@linkcode opzioniPartita}.
     * 
     * @param {!OpzioniPartita} opzioniPartita - The {@linkcode OpzioniPartita} given by the {@linkcode utente}.
     * @param {!Utente} utente - The {@linkcode Utente} who's going to be added to the {@linkcode Lobby} with the corresponding {@linkcode opzioniPartita}.
     * 
     * @returns {!Lobby} 
     * @abstract
     * */
    aggiungiUtenteAllaLobby(opzioniPartita, utente) {
        throw new Error('Must be implemented by subclass!');
    }
}

module.exports = ICodeManager   
