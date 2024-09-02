const Attacco = require("../../entities/attacco");
const Giocatore = require("../../entities/giocatore");
const PartitaCominciata = require("../../entities/partitaCominciata");
const TuplaTerritori = require('../../entities/tupla-territori');

class AttaccoController {

    constructor() {}

    /**
     * This method executes the given {@linkcode attacco} requested by the given {@linkcode giocatore} in the given {@linkcode partitaCominciata}.
     * 
     * @param {PartitaCominciata} partitaCominciata - The {@linkcode PartitaCominciata} where the given {@linkcode giocatore} has requested the given {@linkcode attacco}.
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} launching the {@linkcode attacco} in the given {@linkcode partitaCominciata}.
     * @param {!Attacco} attacco - The {@linkcode Attacco} launched by the {@linkcode giocatore} in the given {@linkcode partitaCominciata}.
     * 
     * @returns {TuplaTerritori}
     */
    effettuaAttacco(partitaCominciata, giocatore, attacco) {
        return partitaCominciata.effettuaAttacco(attacco);
    }
}

module.exports = AttaccoController
