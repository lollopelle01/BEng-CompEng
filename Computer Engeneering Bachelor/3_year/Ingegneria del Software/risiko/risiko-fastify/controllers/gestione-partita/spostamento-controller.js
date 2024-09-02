const PartitaCominciata = require('../../entities/partitaCominciata');
const TuplaTerritori = require('../../entities/tupla-territori');
const Giocatore = require('../../entities/giocatore');
const Spostamento = require('../../entities/spostamento');

class SpostamentoController {

    constructor() {}
    
    /**
     * This method executes the given {@linkcode spostamento} requested by the given {@linkcode giocatore}.
     * 
     * @param {PartitaCominciata} partitaCominciata - The {@linkcode PartitaCominciata} where the given {@linkcode giocatore} has requested the given {@linkcode Spostamento}.
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} requesting to execute the given {@linkcode spostamento} in the given {@linkcode partitaCominciata}.
     * @param {Spostamento} spostamento - The {@linkcode Spostamento} requested to be executed by the given {@linkcode giocatore} in the given {@linkcode partitaCominciata}.
     * 
     * @returns {!TuplaTerritori}
     * */
    effettuaSpostamento(partitaCominciata, giocatore, spostamento) {
        partitaCominciata.effettuaSpostamento(spostamento);
    }
}  

module.exports = SpostamentoController
