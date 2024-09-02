const Colore = require('../../entities/colore');
const Giocatore = require('../../entities/giocatore');
const Lobby = require('../../entities/lobby');
const OpzioniPartita = require('../../entities/opzioniPartita');
const ICodeManager = require('../../interfaces/accesso-gioco/i-code-manager');
const IMatchListener = require('../../interfaces/accesso-gioco/i-match-listener');
const IPartitaManager = require('../../interfaces/accesso-gioco/i-partite-manager');
const { DIMENSIONE_ID_PARTITA, ALFABETO_ID_PARTITA } = require('../../entities/costanti');

class CodeManager extends ICodeManager{

    /**
     * @type {Map<OpzioniPartita, Lobby>}
     */
    codeGiocatori;
    partiteManager;
    matchListener;

    /**
     * @param {IPartitaManager} partiteManager 
     * @param {IMatchListener} matchListener 
     */
    constructor(partiteManager, matchListener) {
        super();
        this.partiteManager = partiteManager
        this.codeGiocatori = new Map()
        this.matchListener = matchListener
    }
    
    /**
     * @param {!OpzioniPartita} opzioniPartita - The {@linkcode OpzioniPartita} given by the {@linkcode utente}.
     * @param {!Utente} utente - The {@linkcode Utente} who's going to be added to the {@linkcode Lobby} with the corresponding {@linkcode opzioniPartita}.
     * 
     * @returns {!Lobby} 
     * @override 
     */
    aggiungiUtenteAllaLobby(opzioniPartita, utente) {
        const opzioniPartitaKey = JSON.stringify(opzioniPartita); 

        //  1. Cerco lobby, altrimenti la creo
        let lobby;
        if (this.codeGiocatori.has(opzioniPartitaKey)) {
            lobby = this.codeGiocatori.get(opzioniPartitaKey)
        }
        else {
            lobby = this.partiteManager.creaLobby(opzioniPartita); // non c'è nessun creatore !!!
            this.codeGiocatori.set(opzioniPartitaKey, lobby);
        }

        //  2. Creo il giocatore (in base alla lobby)   
        let colori = [Colore.Blu, Colore.Giallo, Colore.Nero,
                    Colore.Rosso, Colore.Verde, Colore.Viola];

        if (lobby.giocatori.length > 0) { // se la lobby non è stata creata --> filtriamo i colori
            for(const g of lobby.giocatori){ 
                if(colori.includes(g.colore)){
                    colori.splice(colori.indexOf(g.colore), 1);
                }
            }
        }

        let colore = colori[Math.floor(Math.random() * colori.length)];
        const giocatore = new Giocatore(utente.nickname, utente.nickname, colore); // le armate si decidono in inizializza partita

        //  3. Aggiungo il giocatore
        lobby.aggiungiGiocatore(giocatore);

        //  4. Se la lobby è piena la tolgo --> ne verrà creata un'altra su richiesta
        if(lobby.giocatori.length == opzioniPartita.numGiocatoriMassimi){
            this.codeGiocatori.delete(opzioniPartitaKey);
            this.matchListener.onMatch(lobby.opzioniPartita, lobby);
        }

        //  5. Restituisco la lobby
        return lobby;
    }
}

module.exports = CodeManager;