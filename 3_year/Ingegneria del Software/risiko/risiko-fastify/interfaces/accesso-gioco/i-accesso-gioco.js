const Utente = require('../../entities/utente');
const OpzioniPartita = require('../../entities/opzioniPartita');
const Lobby = require('../../entities/lobby');
const CreatoreDellaPartita = require('../../entities/creatoreDellaPartita');
const Giocatore = require('../../entities/giocatore');

/**
 * @abstract
 */
class IAccessoGioco {

    /**
     * This method lets the given {@linkcode utente} join a {@linkcode lobby} as a {@linkcode Giocatore} with the given {@linkcode opzioniPartita}.
     * It returns the first {@linkcode Lobby} found. If no lobbies exist, a new {@linkcode Lobby} must be created and the given {@linkcode utente} will be added as a {@linkcode Giocatore}.
     * 
     * @param {!OpzioniPartita} opzioniPartita - The {@linkcode OpzioniPartita} inserted by the given {@linkcode utente}.
     * @param {!Utente} utente - The {@linkcode Utente} which has inserted the given {@linkcode opzioniPartita}.
     * 
     * @returns {!Lobby}
     * @abstract
     */
    ricercaPartita(opzioniPartita, utente) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method lets the given {@linkcode utente} create a {@linkcode Lobby} with the given {@linkcode opzioniPartita}.
     * It returns the created {@linkcode Lobby} with the given {@linkcode utente} as a {@linkcode CreatoreDellaPartita}.
     * 
     * @param {!OpzioniPartita} opzioniPartita - The {@linkcode OpzioniPartita} inserted by the given {@linkcode utente}.
     * @param {!Utente} utente - The {@linkcode Utente} which has inserted the given {@linkcode opzioniPartita}.
     * 
     * @returns {!Lobby} 
     * @abstract
     */
    creaPartita(opzioniPartita, utente) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method lets the given {@linkcode utente} join a {@linkcode Lobby} as a {@linkcode Giocatore} using the given {@linkcode codice}.
     * It returns the lobby which owns the given {@linkcode codice}.
     * 
     * @param {!string} codice - The {@linkcode string} identifying a certain {@linkcode Lobby} inserted by the given {@linkcode utente}.
     * @param {!Utente} utente - The {@linkcode Utente} which has inserted the given {@linkcode codice}.
     * 
     * @returns {!Lobby} 
     * @abstract
     */
    unionePartita(codice, utente) {
        throw new Error('Must be implemented by subclass!');
    }
}

module.exports = IAccessoGioco   
