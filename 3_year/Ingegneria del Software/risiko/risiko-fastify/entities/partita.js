const Giocatore = require("./giocatore");
const OpzioniPartita = require("./opzioniPartita");

/**
 * @abstract
 */
class Partita {

    /**
     * Proprietà privata
     */
    codice;
    /**
     * Proprietà privata
     */
    opzioniPartita;
    /**
     * @type {Giocatore[]}
     */
    giocatori;

    /**
     * @param {string} codice 
     * @param {OpzioniPartita} opzioniPartita 
     */
    constructor(codice, opzioniPartita) {
        this.codice = codice;
        this.opzioniPartita = opzioniPartita;
        this.giocatori = [];
    }

    /**
     * This method removes the given {@linkcode giocatore} from the {@linkcode Partita}, if present;
     * 
     * @param {Giocatore} giocatore 
     * 
     * @returns {void}
     */
    rimuoviGiocatore(giocatore) {
        this.giocatori = this.giocatori.filter((e) => e !== giocatore);
    }

}

module.exports = Partita;
