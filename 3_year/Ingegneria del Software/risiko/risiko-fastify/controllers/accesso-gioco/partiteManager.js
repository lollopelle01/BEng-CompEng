const Lobby = require('../../entities/lobby');
const Partita = require('../../entities/partita');
const PartitaCominciata = require('../../entities/partitaCominciata');
const IPartiteManager = require('../../interfaces/accesso-gioco/i-partite-manager')
const { DIMENSIONE_ID_PARTITA, ALFABETO_ID_PARTITA } = require('../../entities/costanti');
const ICreaGioco = require('../../interfaces/gestione-partita/i-crea-gioco');
const CreaGiocoController = require('../gestione-partita/crea-gioco-controller');


function generateRandomString(length) {
    let result = '';
    for (let i = 0; i < length; i++) {
      const randomIndex = Math.floor(Math.random() * ALFABETO_ID_PARTITA.length);
      result += ALFABETO_ID_PARTITA.charAt(randomIndex);
    }
    return result;
}

class PartiteManager extends IPartiteManager {
    /**
     * @type {Map<string, Partita>} 
     */
    partite;
    /**
     * @type {Map<string, string>} 
     */
    wsServerDiPartiteCominciate;
    creaGiocoController;

    /**
     * @param {ICreaGioco} creaGiocoController 
     */
    constructor(creaGiocoController) {
        super();
        this.partite = new Map()
        this.wsServerDiPartiteCominciate = new Map()
        this.creaGiocoController = creaGiocoController;
    }

    /**
     * @param {!string} id - The {@linkcode String} which acts as an id.
     * 
     * @returns {?Partita}
     * @override
     */
    getPartitaById(id) {
        return this.partite.get(id);
    }

    /**
     * @param {!string} id - The {@linkcode string} which acts as an id.
     * 
     * @returns {?Lobby}
     * @override
     */
    getLobbyById(id) {
        let lobby = this.getPartitaById(id);

        if (lobby instanceof Lobby) {
            return lobby;
        } else {
            return null;
        }
    }

    /**
     * @param {!string} id - The {@linkcode string} which acts as an id.
     * 
     * @returns {?PartitaCominciata}
     * @override
     */
    getPartitaCominciataById(id) {
        let partitaCominciata = this.getPartitaById(id);

        if (partitaCominciata instanceof PartitaCominciata) {
            return partitaCominciata;
        } else {
            return null;
        }
    }

    /**
     * @param {!OpzioniPartita} opzioniPartita - The {@linkcode OpzioniPartita} inserted by the system or the given {@linkcode creatoreDellaPartita}.
     * @param {?CreatoreDellaPartita} creatoreDellaPartita - The {@linkcode Utente} who inserted the {@linkcode opzioniPartita}.
     * 
     * @returns {!Lobby}
     * @override
     */
    creaLobby(opzioniPartita, creatoreDellaPartita = null) {
        const code = generateRandomString(DIMENSIONE_ID_PARTITA);
        let lobby = new Lobby(code, opzioniPartita, creatoreDellaPartita);
        this.partite.set(code, lobby);
        return lobby;
    }

    /**
     * @param {!string} id - The {@linkcode string} identifying a {@linkcode Partita}.
     * 
     * @returns {void}
     * @override
     */
    cominciaPartita(id) {
        let lobby = this.getLobbyById(id);
        let partitaCominciata = new PartitaCominciata(id, lobby.opzioniPartita, lobby.giocatori);
        this.partite.set(id, partitaCominciata);
        let ulrPromise = this.creaGiocoController.creaWebSocketServer(partitaCominciata);
        ulrPromise.then((url) => {
            this.wsServerDiPartiteCominciate.set(id, url);
        });
        
        return;
    }

    /**
     * @param {!string} id - The {@linkcode string} identifying a {@linkcode Partita}.
     * 
     * @returns {void}
     * @override
     */
    rimuoviPartita(id) {

        this.partite.delete(id);

        if (this.getPartitaCominciataById(id) !== null) {
            this.wsServerDiPartiteCominciate.delete(id);
            //TODO: eliminarlo anche da crea gioco
        }

    }

    /**
     * @param {!string} id 
     * 
     * @returns {!string}
     * @override
     */
    getUrlWebSocketServerDiPartita(id) {
        let result = this.wsServerDiPartiteCominciate.get(id);
        return result === undefined ? "" : result;
    }
}

module.exports = new PartiteManager(new CreaGiocoController())