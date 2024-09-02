const CreatoreDellaPartita = require('../../entities/creatoreDellaPartita');
const Giocatore = require('../../entities/giocatore');
const Utente = require('../../entities/utente');
const PartitaCominciata = require('../../entities/partitaCominciata');
const Lobby = require('../../entities/lobby');

/**
 * @abstract
 */
class IGestioneLobby {

    /**
     * @param {!string} id - The {@linkcode string} identifying a {@linkcode Partita}.
     * 
     * @returns {void}
     * @override
     */
    avviaPartita(codicePartita) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method removes the given {@linkcode giocatore} from the {@linkcode Lobby} identified by the given {@linkcode codicePartita}.
     * The removal of the {@linkcode giocatore} happens if and only if {@linkcode creatoreDellaPartita} is the user who created the {@linkcode Lobby}.
     * 
     * @param {!string} codicePartita - The {@linkcode string} identifying a {@linkcode Lobby}.
     * @param {!CreatoreDellaPartita} creatoreDellaPartita - The {@linkcode CreatoreDellaPartita} who created the {@linkcode Lobby}.
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} to remove from the {@linkcode Lobby}.
     * 
     * @returns {void}
     * @abstract
     */
    rimuoviGiocatore(codicePartita, creatoreDellaPartita, giocatore) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method lets the given {@linkcode giocatore} leave the {@linkcode Lobby} identified by the given {@linkcode codicePartita}.
     * 
     * @param {!string} codicePartita - The {@linkcode string} identifying a {@linkcode Lobby}.
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} who wants to exit the {@linkcode Lobby}.
     * 
     * @returns {void}
     * @abstract
     */
    abbandonaLobby(codicePartita, giocatore) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method kicks every {@linkcode Giocatore} out of the {@linkcode Lobby} identified by the given {@linkcode codicePartita}. The {@linkcode Lobby} is then disposed of.
     * The removal of every {@linkcode Giocatore} happens if and only if the given {@linkcode creatoreDellaPartita} is the {@linkcode Utente} who created the {@linkcode Lobby}. 
     * 
     * @param {!string} codicePartita - The {@linkcode string} identifying a {@linkcode Lobby}.
     * @param {!CreatoreDellaPartita} creatoreDellaPartita - The {@linkcode CreatoreDellaPartita} who created the {@linkcode Lobby}.
     * 
     * @returns {void}
     * @abstract
     */
    sciogliLobby(codicePartita, creatoreDellaPartita) {
        throw new Error('Must be implemented by subclass!');
    }

    /**
     * This method retrieves the URL of the WebSocketServer which manages the corresponding {@linkcode Partita} of the given {@linkcode codicePartita}.
     * 
     * @param {!string} codicePartita - The {@linkcode string} identifying a {@linkcode Partita}.
     * 
     * @returns {!string}
     * @abstract
     */
    getWebSocketServerUrl(codicePartita) {
        throw new Error('Must be implemented by subclass!');
    }
}

module.exports = IGestioneLobby
