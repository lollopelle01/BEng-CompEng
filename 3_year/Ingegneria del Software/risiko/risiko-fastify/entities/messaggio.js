const Giocatore = require('./giocatore');

class Messaggio {

    testo;
    dateTime;
    giocatore;

    /**
     * @param {Giocatore} giocatore 
     * @param {string} testo 
     * @param {Date} dateTime 
     */
    constructor(giocatore, testo, dateTime) {
        this.giocatore = giocatore;
        this.testo = testo;
        this.dateTime = dateTime;
    }
}

module.exports = Messaggio;
