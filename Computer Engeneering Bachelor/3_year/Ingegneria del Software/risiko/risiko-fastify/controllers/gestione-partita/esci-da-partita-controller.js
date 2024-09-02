const Giocatore = require("../../entities/giocatore");
const PartitaCominciata = require("../../entities/partitaCominciata");

class EsciDaPartitaController {

    constructor() {}

    /**
     * This method lets the {@linkcode giocatore} quit the given {@linkcode partitaCominciata}.
     * 
     * @param {PartitaCominciata} partitaCominciata - The {@linkcode PartitaCominciata} where the given {@linkcode giocatore} has requested to quit.
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} wanting to quit the given {@linkcode partitaCominciata}.
     * 
     * @returns {void}
     * @abstract
     * */
    esci(partitaCominciata, giocatore) {
        // NOT-TODO
    }
}

module.exports = EsciDaPartitaController
