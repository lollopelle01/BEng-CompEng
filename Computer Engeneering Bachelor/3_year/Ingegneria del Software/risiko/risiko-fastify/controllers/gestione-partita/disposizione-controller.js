const Disposizione = require("../../entities/disposizione");
const Giocatore = require("../../entities/giocatore");
const Territorio = require("../../entities/territorio");
const PartitaCominciata = require("../../entities/partitaCominciata");

class DisposizioneController {

    constructor() {}

    /**
     * This method executes the given {@linkcode disposizione} requested by the given {@linkcode giocatore} in the given {@linkcode partitaCominciata}.
     * 
     * @param {PartitaCominciata} partitaCominciata - The {@linkcode PartitaCominciata} where the given {@linkcode giocatore} has requested the given {@linkcode disposizione}.
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} requesting to execute the given {@linkcode disposizione} in the given {@linkcode partitaCominciata}.
     * @param {Disposizione} disposizione - The {@linkcode Disposizione} requested to be executed by the given {@linkcode giocatore} in the given {@linkcode partitaCominciata}.
     * 
     * @returns {Territorio}
     * */
    effettuaDisposizione(partitaCominciata, giocatore, disposizione) {
        return partitaCominciata.effettuaDisposizione(disposizione);
    }
}

module.exports = DisposizioneController
