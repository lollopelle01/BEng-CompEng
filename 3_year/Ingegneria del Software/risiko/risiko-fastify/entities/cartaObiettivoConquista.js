const CartaObiettivo = require("./cartaObiettivo");
const Giocatore = require("./giocatore");
const PartitaCominciata = require("./partitaCominciata");
const Regione = require("./regione");

class CartaObiettivoConquista extends CartaObiettivo {
    regioni;

    /**
     * @param {!number} id 
     * @param {!string} testo 
     * @param {!Regione[]} regioni 
     */
    constructor(id, testo, regioni){
        super(id, testo);
        this.regioni = regioni;
    }

    /**
     * @param {!Giocatore} giocatore 
     * @param {!PartitaCominciata} partitaCominciata
     * @returns {boolean}
     */
    checkConseguimento(giocatore, partitaCominciata) {

        let i, j;
        let regioniGlobali = partitaCominciata.mappa.regioni();

        for (i = 0; i < regioniGlobali.length; i++) {
            for (j = 0; j < this.regioni.length; j++) {
                if (this.regioni[j] === regioniGlobali[i] && regioniGlobali[i].possessore !== giocatore) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * This method returns the list of {@linkcode Regione} to conquer in order to win.
     * 
     * @returns {Regione[]}
     */
    getRegioniDaConquistare() {
        return this.regioni;
    }
}

module.exports = CartaObiettivoConquista