const OpzioniPartita = require("./opzioniPartita");
const Partita = require("./partita");
const CreatoreDellaPartita = require("./creatoreDellaPartita");
const Giocatore = require("./giocatore");

class Lobby extends Partita {

    creatoreDellaPartita;

    /**
     * @param {string} codice 
     * @param {OpzioniPartita} opzioniPartita 
     * @param {CreatoreDellaPartita} creatoreDellaPartita 
     */
    constructor(codice, opzioniPartita, creatoreDellaPartita) {
        super(codice, opzioniPartita);
        this.creatoreDellaPartita = creatoreDellaPartita;
        if (creatoreDellaPartita !== null) {
            this.aggiungiGiocatore(creatoreDellaPartita);
        }
    }

    /**
     * This method adds the given {@linkcode giocatore} to the {@linkcode Lobby}, if not present;
     * 
     * @param {Giocatore} giocatore 
     */
    aggiungiGiocatore(giocatore) {
        if (!this.giocatori.includes(giocatore)) {
            this.giocatori.push(giocatore);
        }
    }
}

module.exports = Lobby;
