const FaseDiCombinazione = require('./faseDiCombinazione');
const FaseDiDisposizione = require('./faseDiDisposizione');
const FaseDiAttacco = require('./faseDiAttacco');
const FaseDiSpostamento = require('./faseDiSpostamento');
const Giocatore = require('./giocatore');

class Turno {

    numeroTurno;
    giocatore;
    static DURATA_COMBINAZIONE = 3;
    static DURATA_DISPOSIZIONE = 3;
    static DURATA_ATTACCO = 3;
    static DURATA_SPOSTAMENTO = 3;

    /**
     * @param {number} numeroTurno 
     * @param {Giocatore} giocatore 
     */
    constructor(numeroTurno, giocatore) {
        this.numeroTurno = numeroTurno;
        this.giocatore = giocatore;
        
        this.faseAttuale = new FaseDiCombinazione(Turno.DURATA_COMBINAZIONE);
    }

    passaAFaseDiDisposizione() {
        this.faseAttuale = new FaseDiDisposizione(Turno.DURATA_DISPOSIZIONE);
    }

    passaAFaseDiAttacco() {
        this.faseAttuale = new FaseDiAttacco(Turno.DURATA_ATTACCO);
    }

    passaAFaseDiSpostamento() {
        this.faseAttuale = new FaseDiSpostamento(Turno.DURATA_SPOSTAMENTO);
    }
}

module.exports = Turno;
