const Lobby = require('../../entities/lobby')
const OpzioniPartita = require('../../entities/opzioniPartita')
const Utente = require('../../entities/utente')
const IAccessoGioco = require('../../interfaces/accesso-gioco/i-accesso-gioco')
const IMatchListener = require('../../interfaces/accesso-gioco/i-match-listener')
const ICodeManager = require('../../interfaces/accesso-gioco/i-code-manager');
const IPartiteManager = require('../../interfaces/accesso-gioco/i-partite-manager');
const Giocatore = require('../../entities/giocatore')
const CreatoreDellaPartita = require('../../entities/creatoreDellaPartita');
const Colore = require('../../entities/colore')

const CodeManager = require('./codeManager')

class AccessoGiocoController extends IAccessoGioco {

    /**
     * @type {ICodeManager}
     */
    codeManager;
    /**
     * @type {IPartiteManager}
     */
    partiteManager;

    /**
     * 
     * @param {IPartiteManager} partiteManager 
     */
    constructor(partiteManager) {
        super();
        this.partiteManager = partiteManager
        this.codeManager = new CodeManager(partiteManager, this);
    }

    /**
     * @param {OpzioniPartita} opzioniPartita - The {@linkcode OpzioniPartita} inserted by the given {@linkcode utente}.
     * @param {Utente} utente - The {@linkcode Utente} which has inserted the given {@linkcode opzioniPartita}.
     * 
     * @returns {!Lobby}
     * @override
     */
    ricercaPartita(opzioniPartita, utente){
        // VALIDA INPUT ?????

        //  1. Aggiungiamo il utente alla lobby (creerà in automatico il giocatore)
        let lobby = this.codeManager.aggiungiUtenteAllaLobby(opzioniPartita, utente);

        //  2. Restituiamo la lobby
        return lobby;
    }

    /**
     * @param {!OpzioniPartita} opzioniPartita - The {@linkcode OpzioniPartita} inserted by the given {@linkcode utente}.
     * @param {!Utente} utente - The {@linkcode Utente} which has inserted the given {@linkcode opzioniPartita}.
     * 
     * @returns {!Lobby} 
     * @override
     */
    creaPartita(opzioniPartita, utente) {
        // VALIDA INPUT ?????


        //  1. Crea giocatore
        const creatore = new CreatoreDellaPartita(utente.nickname, utente.nickname, Colore.Blu, 0);

        //  2. Creiamo la lobby
        let lobby = this.partiteManager.creaLobby(opzioniPartita, creatore);

        //  3. Restituiamo la lobby
        return lobby;
    }

    /**
     * @param {!string} codice - The {@linkcode String} identifying a certain {@linkcode Lobby} inserted by the given {@linkcode utente}.
     * @param {!Utente} utente - The {@linkcode Utente} which has inserted the given {@linkcode codice}.
     * 
     * @returns {Lobby | null} 
     * @override
     */
    unionePartita(codice, utente) {
        // VALIDA INPUT ?????

        //  1. Crea giocatore
        const lobby = this.partiteManager.getLobbyById(codice);

        if (lobby === null) {
            return null;
        }

        const colori = [Colore.Blu, Colore.Giallo, Colore.Nero,
                        Colore.Rosso, Colore.Verde, Colore.Viola];

        for (const g of lobby.giocatori) {
            if (colori.includes(g.colore)) {
                colori.splice(colori.indexOf(g.colore), 1);
            }
        }
        const index = Math.floor(Math.random() * colori.length);
        
        const colore = colori[index];
        const giocatore = new Giocatore(utente.nickname, utente.nickname, colore);

        //  2. Distrugge utente
        // non faccio nulla

        //  3. Aggiunge giocatore alla lobby
        lobby.aggiungiGiocatore(giocatore);

        //  4. Restituisce la lobby
        return lobby;
    }

    /**
     * This method retrieves the url of the WebSocketServer hosting the {@linkcode Partita} possessing the given {@linkcode id}.
     * 
     * @param {!string} id 
     * 
     * @returns {!string}
     * @abstract
     */
    getUrlWebSocketServerDiPartita(id){
        return this.partiteManager.getUrlWebSocketServerDiPartita(id);
    }

    /**
     * This method is called whenever one or more {@linkcode Utente} are found to populate the given {@linkcode lobby} ensuring the given {@linkcode opzioniPartita} are satisfied.
     * 
     * @param {!OpzioniPartita} opzioniPartita - The {@linkcode OpzioniPartita} of the given {@linkcode lobby}.
     * @param {!Lobby} lobby - The {@linkcode Lobby} possessing the given {@linkcode opzioniPartita}.
     * 
     * @returns {void} 
     * @override
     */
    onMatch(opzioniPartita, lobby) {
        this.partiteManager.cominciaPartita(lobby.codice);
        console.log('onMatch', lobby);
        /**
         * Dalle sequenze:
         * 1) viene chiamato onMatch
         * 2) Operazioni aggiuntive di on match?
         * 2) viene chiamata CominciaPartita
         * 3) viene creato il websocketserver che si occupa della PartitaCominciata da CreaGiocoController
         * 4) L'URL di tale server viene restituito a PartiteManager -> come ottengo URL conoscendo solo AddressInfo? -> forse `https://${AddressInfo.address}:${AddressInfo.port}` o qualcosa di simile
         * 5) Prima di restituire l'url, va salvato in PartiteManager.wsServerDiPartiteCominciate
         * 6) Si ritorna a AccessoGiocoController, finendo la chiamata di onMatch
         */
    }

}

module.exports = new AccessoGiocoController(require('./partiteManager'))