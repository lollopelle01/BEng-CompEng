const Utente = require('../../entities/utente');
const OpzioniPartita = require('../../entities/opzioniPartita');
const Partita = require('../../entities/partita');
const Lobby = require('../../entities/lobby');
const CreatoreDellaPartita = require('../../entities/creatoreDellaPartita');
const Giocatore = require('../../entities/giocatore');
const PartitaCominciata = require('../../entities/partitaCominciata');

/**
 * @abstract
 */
class IPartiteManager {

    /**
     * This method retrives a {@linkcode Partita} by id. If no {@linkcode Partita}  were found, null is returned. 
     * 
     * @param {!string} id - The {@linkcode string} which acts as an id.
     * 
     * @returns {?Partita}
     * @abstract
     */
    getPartitaById(id) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method retrives a {@linkcode Lobby}  by id. If no {@linkcode Lobby}  were found, null is returned.  
     * 
     * @param {!string} id - The {@linkcode string} which acts as an id.
     * 
     * @returns {?Lobby}
     * @abstract
     */
    getLobbyById(id) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method retrives a {@linkcode PartitaCominciata} by id. If no {@linkcode PartitaCominciata} were found, null is returned.  
     * 
     * @param {!string} id - The {@linkcode string} which acts as an id.
     * 
     * @returns {?PartitaCominciata}
     * @abstract
     */
    getPartitaCominciataById(id) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This creates a {@linkcode Lobby} which satisfies the given {@linkcode opzioniPartita}  and, if given, sets the {@linkcode creatoreDellaPartita}  as the creator of that {@linkcode Lobby} .
     * 
     * @param {!OpzioniPartita} opzioniPartita - The {@linkcode OpzioniPartita} inserted by the system or the given {@linkcode creatoreDellaPartita}.
     * @param {?CreatoreDellaPartita} creatoreDellaPartita - The {@linkcode Utente} who inserted the {@linkcode opzioniPartita}.
     * 
     * @returns {!Lobby}
     * @abstract
     */
    creaLobby(opzioniPartita, creatoreDellaPartita = null) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method starts a Risiko game from the {@linkcode Partita} to which corresponds the given {@linkcode id}.
     * It returns a {@linkcode PartitaCominciata} containing all the {@linkcode Giocatore} in the aforementioned {@linkcode Partita}.
     * 
     * @param {!string} id - The {@linkcode string} identifying a {@linkcode Partita}.
     * 
     * @returns {!PartitaCominciata}
     * @abstract
     */
    cominciaPartita(id) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method removes the {@linkcode Partita} to which corresponds the given {@linkcode id}.
     * 
     * @param {!string} id - The {@linkcode string} identifying a {@linkcode Partita}.
     * 
     * @returns {void}
     * @abstract
     */
    rimuoviPartita(id) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method retrieves the url of the WebSocketServer hosting the {@linkcode Partita} possessing the given {@linkcode id}.
     * 
     * @param {!string} id 
     * 
     * @returns {!string}
     * @abstract
     */
    getUrlWebSocketServerDiPartita(id) {
        throw new Error('Must be implemented by subclass!');
    }
}

module.exports = IPartiteManager
